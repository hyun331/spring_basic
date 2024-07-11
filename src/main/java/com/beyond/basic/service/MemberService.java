package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberJdbcRepository;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//input값의 검증 및 실질적 비지니스 로직은 서비스 계층에서 수행

@Service    //서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성. 스프링이 알아서 service 객체 1개를 생성함.
public class MemberService {
    //최초에 생성하면 재할당 안되게
    private final MemberRepository memberRepository;
    //싱글톤 객체를 주입(DI) 받는다는 의미
    @Autowired
    public MemberService(MemberJdbcRepository memoryRepository){
        memberRepository = memoryRepository;
    }



    public void memberCreate(MemberReqDto memberdto) {
        if(memberdto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        //받아온 reqDto를 Service에서 객체로 만들어주기
        //근데 이 방법은 컬럼 많아지면 어려움
        Member member = new Member();
        member.setName(memberdto.getName());
        member.setEmail(memberdto.getEmail());
        member.setPassword(memberdto.getPassword());
        memberRepository.save(member);
    }

    public void memberDetail(Long id){
        memberRepository.findById(id);
    }


    //repository에서 받은 List<Member> -> List<MemberResDto>로 변경
    public List<MemberResDto> memberList(){
        List<Member> memberList= memberRepository.findAll();
        List<MemberResDto> memberResDtoList = new ArrayList<>();
        for(Member m : memberList){
            MemberResDto memberResDto = new MemberResDto();
//            memberResDto.setId(m.getId());
            memberResDto.setName(m.getName());
            memberResDto.setEmail(m.getEmail());
            memberResDtoList.add(memberResDto);
        }

        return memberResDtoList;

    }
}
