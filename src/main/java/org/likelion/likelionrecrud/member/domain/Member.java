package org.likelion.likelionrecrud.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.likelionrecrud.member.api.dto.request.MemberUpdateReqDto;
import org.likelion.likelionrecrud.order.domain.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Part part;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

    @Builder Member(String name,int age, Part part) {
        this.name = name;
        this.age = age;
        this.part = part;
    }

    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        this.name = memberUpdateReqDto.name();
        this.age = memberUpdateReqDto.age();
        this.part = memberUpdateReqDto.part();
    }

}