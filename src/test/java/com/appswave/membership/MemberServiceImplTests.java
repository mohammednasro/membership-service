package com.appswave.membership;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.entity.Gender;
import com.appswave.membership.member.entity.Member;
import com.appswave.membership.member.entity.MembershipType;
import com.appswave.membership.member.entity.Persona;
import com.appswave.membership.member.repository.MemberRepository;
import com.appswave.membership.member.service.MemberService;

@SpringBootTest
class MemberServiceImplTests {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();

        Member member = Member.builder()
                .firstName("Mohammed")
                .lastName("Nasro")
                .email("mohammed@test.com")
                .mobileNumber("0799999999")
                .gender(Gender.MALE)
                .membershipType(MembershipType.EXTERNAL_MEMBER)
                .persona(Persona.BUSINESS)
                .build();

        memberRepository.save(member);
    }

    @Test
    void search_shouldReturnResults_whenNoFilterApplied() {

        MemberFilter filter = new MemberFilter();
        filter.setPage(0);
        filter.setSize(10);

        Page<Member> result = memberService.search(filter);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getEmail())
                .isEqualTo("mohammed@test.com");
    }

    @Test
    void search_shouldFilterByFirstName() {

        MemberFilter filter = new MemberFilter();
        filter.setFirstName("moh");
        filter.setPage(0);
        filter.setSize(10);

        Page<Member> result = memberService.search(filter);

        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void search_shouldReturnEmpty_whenNoMatch() {

        MemberFilter filter = new MemberFilter();
        filter.setFirstName("NotExisting");
        filter.setPage(0);
        filter.setSize(10);

        Page<Member> result = memberService.search(filter);

        assertThat(result.getTotalElements()).isZero();
    }
}
