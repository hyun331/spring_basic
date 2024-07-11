package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//input값의 검증 및 실질적 비지니스 로직은 서비스 계층에서 수행

@Service    //서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성. 스프링이 알아서 service 객체 1개를 생성함.
public class MemberService {
    //최초에 생성하면 재할당 안되게
    private final MemberRepository memberRepository;
    //싱글톤 객체를 주입(DI) 받는다는 의미
    @Autowired
    public MemberService(MemberMemoryRepository memoryRepository){
        memberRepository = memoryRepository;
    }



    public void memberCreate(Member member) {
        if(member.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        memberRepository.save(member);
    }

    public void memberDetail(Long id){
        memberRepository.findById(id);
    }
    public List<Member> memberList(){
        return memberRepository.findAll();

    }
}
