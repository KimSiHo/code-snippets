package com.example.jpaentity.entities.real_2.api;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpaentity.entities.real_2.dao.OrderRepository;
import com.example.jpaentity.entities.real_2.dao.OrderSearch;
import com.example.jpaentity.entities.real_2.dao.OrderSimpleQueryDto;
import com.example.jpaentity.entities.real_2.dao.OrderSimpleQueryRepository;
import com.example.jpaentity.entities.real_2.domain.OrderStatus;
import com.example.jpaentity.entities.real_2.domain.Real2Address;
import com.example.jpaentity.entities.real_2.domain.Real2Order;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * xToOne(ManyToOne, OneToOne) 관계 최적화 Order Order -> Member Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository; //의존관계 주입

    /**
     * V1. 엔티티 직접 노출 - Hibernate5Module 모듈 등록, LAZY=null 처리 - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    // to one 시리즈를 조회 후 반환
    // order와 member에 양방향 연관관계 끊어주지 않으면 무한루프 에러
    @GetMapping("/api/v1/simple-orders")
    public List<Real2Order> ordersV1() {
        List<Real2Order> all = orderRepository.findAllByString();
        /*for (Real2Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }*/
        for (Real2Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }
        return all;
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    // eager로 해도 해결이 안된다, 일단 JPQL을 사용하면서 order를 가져온 다음에, 그 다음에 eager로 세팅되어 있는 것을 보고
    // 어떻게 가져오려고 시도하는 거여서 성능은 성능 대로 안나오고 예측하기 힘든 쿼리가 나간다
    // 그래서 LAZY로 하고 필요할 때 페치 조인을 사용해야 한다
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Real2Order> orders = orderRepository.findAll();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());

        return result;
    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Real2Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }

    // 네트워크 전송량이 줄어들어서 성능은 증가하기 하지만 v3은 다양한 로직에서 사용할 수 있는 반면 v4는 원하는 필드를 특정하게 선택해서
    // 직접 sql을 짜는 것과 같이 동작해 재사용성이 떨어진다는 단점이 있다
    // 네트워크 용량을 최적화하긴 하지만 생각보다 성능 차이가 미비하다, 리포지토리 재사용성 떨어짐, API 스펙에 맞춘 코드가 리포지토리에 들어가는 단점
    // 물리적으로는 로직과 db 접근 기술이 분리되어 있지만 논리적으로는 결합되어 있다, api 스펙이 리포지토리 쿼리문에 들어가므로
    // 대부분의 성능 문제는 from절과 where절 인덱스 여부에 따라 성능 차이가 나므로 select 절 최적화는 크게 영향이 없다
    // select 절에서 가져오는 데이터 용량이 큰 경우를 제외하고는 select 절 최적화를 하지말고 그냥 가져오는 것이 유지보수성과 확장성에 좋다!
    // select절 최적화 하는 경우 해당 코드는 별도의 respository를 만들어서 사용하는 것이 유지보수성에 좋다!
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Real2Address address;

        public SimpleOrderDto(Real2Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

}
