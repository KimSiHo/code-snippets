package com.example.jpaentity.entities.value.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.example.jpaentity.entities.mapping.mappedSuperClass.BaseRegisterDateTimeEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "value_member")
//@Entity
public class ValueMember {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column
    private String name;

    @Embedded
    private Period period;

    // 임베디드 타입을 중복해서 사용할 경우 이렇게 이름을 오버라이드 한다
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="city", column = @Column(name = "WORK_CITY")),
        @AttributeOverride(name="strret", column = @Column(name = "WORK_STREET")),
        @AttributeOverride(name="zipcode", column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address address;

    // rdb는 컬렉션을 내부적으로 테이블 안에 저장할 수 있는 구조가 없으므로, 일대 다 형식으로 테이블로 저장된다
    // 이때, 값 타입을 테이블에 저장할 때 식별자를 따로 생성해서 PK로 하지 말고 값 타입들을 조합해서 PK로 설정
    // PK를 따로 설정하면 그것은 값 타입이 아니라 엔티티가 된다
    // join column으로 매핑할 외래키를 지정
    // 값 타입 같은 경우 @Column 매핑을 하지 않아도, 해당 값 타입의 필드로 테이블 컬럼이 생성
    // Set<String>과 같이 칼럼이 딱 하나인 경우는 예외적으로 @Column 매핑으로 매핑할 칼럼명을 적어줄 수 있다
    // 값 타입 컬렉션은 영속성 전이 + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다
    // 값 타입은 본인 스스로의 라이프 사이클이 없다, 생명 주기가 엔티티에 소속된다
    // 엔티티에 속하는 멤버 변수는 값만 바뀌면 자동으로 업데이트 쿼리문이 나가듯이, 값 타입 컬렉션도 값을 바꾸면 별도로 없데이트 할 필요가 없다
    // 값 타입 컬렉션에서 값을 변경할 때는 삭제하고 다시 집어넣는 식으로 해야 한다
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "member_no"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    // 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 함 : null 입력 X, 중복 저장 X
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "member_no"))
    private List<Address> addressHistory = new ArrayList<>();

    public static ValueMember createMember() {
        Period period = Period.builder()
            .startDate(LocalDateTime.now().minusHours(1L))
            .endDate(LocalDateTime.now())
            .build();

        Address address = Address.builder()
            .city("seoul")
            .street("nowon")
            .zipcode("111")
            .build();

        return ValueMember.builder()
                .name("test")
                .period(period)
                .address(address)
                .build();
    }
}
