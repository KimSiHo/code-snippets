package com.example.jpaentity.entities.real_2.api;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpaentity.entities.real_2.dao.OrderRepository;
import com.example.jpaentity.entities.real_2.dao.query.OrderFlatDto;
import com.example.jpaentity.entities.real_2.dao.query.OrderItemQueryDto;
import com.example.jpaentity.entities.real_2.dao.query.OrderQueryDto;
import com.example.jpaentity.entities.real_2.dao.query.OrderQueryRepository;
import com.example.jpaentity.entities.real_2.domain.OrderStatus;
import com.example.jpaentity.entities.real_2.domain.Real2Address;
import com.example.jpaentity.entities.real_2.domain.Real2Order;
import com.example.jpaentity.entities.real_2.domain.Real2OrderItem;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * V1. 엔티티 직접 노출 - 엔티티가 변하면 API 스펙이 변한다. - 트랜잭션 안에서 지연 로딩 필요 - 양방향 연관관계 문제
 * <p>
 * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X) - 트랜잭션 안에서 지연 로딩 필요 V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O) - 페이징 시에는 N 부분을 포기해야함(대신에 batch fetch size? 옵션 주면 N
 * -> 1 쿼리로 변경 가능)
 * <p>
 * V4. JPA에서 DTO로 바로 조회, 컬렉션 N 조회 (1 + N Query) - 페이징 가능 V5. JPA에서 DTO로 바로 조회, 컬렉션 1 조회 최적화 버전 (1 + 1 Query) - 페이징 가능 V6. JPA에서 DTO로 바로 조회, 플랫
 * 데이터(1Query) (1 Query) - 페이징 불가능...
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * V1. 엔티티 직접 노출 - Hibernate5Module 모듈 등록, LAZY=null 처리 - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    // 일대 다 컬렉션을 가져오고, 또 해당 컬렉션에 있는 ToOne (item)을 강제 초기화한다
    // 엔티티를 직접 노출하기에 양방향 연관관계를 @JsonIgnore 처리 해줘야 한다!
    @GetMapping("/api/v1/orders")
    public List<Real2Order> ordersV1() {
        List<Real2Order> all = orderRepository.findAll();
        for (Real2Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
            List<Real2OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName()); //Lazy 강제 초기화
        }
        return all;
    }

    // 쿼리가 많이 나간다, 먼저 order 리스트를 가져오고, 멤버와 delivery를 각각 지연 로딩으로 가져온다
    // 그리고 order 단건에 대한 orderItems 리스트를 가져온다 (2개)
    // 그리고 orderItem 리스트에 대한 toOne lazy 로딩인 item을 가져오는 쿼리가 2번 나간다
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Real2Order> orders = orderRepository.findAll();
        List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(toList());

        return result;
    }

    // v2와 비교해서 조회하는 jpql문만 fetch 조인문을 사용하게 변경됬고 값을 반환하는 코드는 바뀐 것이 없다!
    // 직접 sql을 다루면 데이터를 가져와서 각 테이블 별로 dto를 만들고 해야할 작업이 많았을 것이다
    // 일대 다 페치 조인 단점은 페이징이 불가능하다는 것! 메모리에서 페이징 작업을 한다
    // 컬렉션 페치 조인은 1개만 사용 가능, 2개 이상에 사용하면 데이터가 부정합하게 조회될 수 있다, 뻥튀기 갯수도 많아지지만
    // 어떤 것을 기준으로 가져와야 되는지 jpa 가 판단을 못할 수 있다
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Real2Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(toList());

        return result;
    }

    /**
     * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려 - ToOne 관계만 우선 모두 페치 조인으로 최적화 - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     */
    // 일대다에서 일을 기준으로 페이징을 하는 것이 목적이다. 그런데 데이터는 다를 기준으로 row 가 생성된다
    // 일대다 연관관계가 있는 것을 jpql 로 가져오고 페이징 처리하면 메모리에서 페이징 처리한다
    // toOne 관계는 fetch 조인을 통해 한번에 가져오고 toMany와 toMany와 연관되어 있는 toOne은 지연 로딩을 한다
    // 지연 로딩을 할 때 @BatchSize를 통해 최적화를 한다
    // 그러면 member와 delivery를 fetch join 하는 쿼리가 한 번 나가고, 지연로딩으로 orderItem 컬렉션을 가져오는데 (추가 1개의 쿼리) 2개의 행이 반환되고.
    // 그 2개의 행 각각에 item이 있어서 2개의 쿼리가 추가적으로 또 나간다
    // batchsize 적용을 하면 일대 다뿐만 아니라 일대 다에 연관된 toOne도 in 쿼리가 나간다, 결과적으로 아래의 예제에서 1+N+N 쿼리가 1+1+1이 되는 것이다
    // batchsize 적용을 하면 모든 연관관계를 in 쿼리를 통해서 가져온다
    // 이 정도 하면 대부분의 성능 문제 해결, 더 이상의 성능은 레디스를 쓰거나 다른 방법을 써야 된다
    // 또한 이 방법의 장점은 데이터 전송량이 최적화 된다는 것이다, 기존의 fetch join 을 사용해서 가져오는 방법은 중복된 데이터들도 다 갖고 와서
    // 데이터 전송량이 증가했지만 batch size를 통한 최적화는 중복된 데이터가 없고 딱 필요한 데이터만 정규화해서 가져와서 효율적이다
    // 상황과 데이터 양에 따라서 달르지만 fetch join을 통해서 한 번에 가져오는 것보다 정규화된 데이터를 여러 번 가져오는 것이 빠를 수도 있다!
    // default batch size 설정을 사용하고 필요시 각 필드마다 커스텀한 사이즈 설정을 한다, 컬렉션은 컬렉션 필드 위에다가 설정을 하고 toOne 관계는
    // 엔티티 위에다가 설정을 하면 된다
    // batch size는 1000을 max로 생각하자, db에 따라서 1000이 넘어가면 in 쿼리에서 오류를 리턴한다
    // 1000이 가장 좋긴 하지만, 1000으로 잡으면 한번에 1000개를 db에서 가져오므로 db에서 순간 부하가 증가할 수 있다 따라서 db가 순간 부하를
    // 어디까지 견딜 수 있는지로 결정하면 된다
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<Real2Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(toList());

        return result;
    }

    // toMany 조회가 있을 때 dto 로 변환하는 방법
    // 1+N, 단건 조회일 경우 사용
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    // 여러 건 조회일 경우 사용, 1+1
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    // 여러 건 조회일 경우 사용, 1
    // db에서 한 방에 조인 문으로 가져온다음 어플리케이션 상에서 조작
    // 단점은 쿼리는 한번이지만 조인으로 인해 DB에서 전달하는 중복 데이터로 v5보다 더 느릴 수도 있다
    // 애플리케이션에서 추가 작업이 크고 페이징 불가능 (order 기준으로 페이징 안됨!)
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        // id를 기준으로 묶게 @EqualsAndHashCode를 구현해야 한다
        return flats.stream()
            .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
            )).entrySet().stream()
            .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                e.getKey().getAddress(), e.getValue()))
            .collect(toList());
    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Real2Address address;
        private List<OrderItemDto> orderItems;

        // 엔티티를 dto로 변환해서 반환할 때는 단순히 껍데기만 dto로 감싸서 반환하는 것이 아니라 내부에 있는 엔티티도 dto로 변환해서
        // 반환해야 한다
        // value 오브젝트는 상관없다
        public OrderDto(Real2Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count;      //주문 수량

        public OrderItemDto(Real2OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
