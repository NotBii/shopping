package com.zerobase.shopping;

import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShoppingApplicationTests {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버")
    void test1() {
        MemberEntity member = memberRepository.findByUsername("test1").orElseThrow(MemberNotFound::new);
        System.out.println(member.getMemberId());

    }
    @Test
    @DisplayName("객체내용")
    void test2() {
        MemberEntity member = memberRepository.findByUsername("test2").orElseThrow(MemberNotFound::new);
        System.out.println(member.getCart());

    }
}
