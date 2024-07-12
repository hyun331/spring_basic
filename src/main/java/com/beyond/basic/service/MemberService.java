package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//input값의 검증 및 실질적 비지니스 로직은 서비스 계층에서 수행

@Service    //서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성. 스프링이 알아서 service 객체 1개를 생성함.
//Transcational 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고(각 메서드마다 하나의 트랜잭션으로 묶는다.)
//만약 예외가 발생하면 롤백처리 자동화
//각 메서드마다 @Transactional을 붙일 수 있음. 근데 필요없는 곳에는 붙이지 말기
//Service단에 붙이면 아래 모든 메서드에 @Transactional 한것과 동일
@Transactional
public class MemberService {
    //최초에 생성하면 재할당 안되게
    private final MyMemberRepository memberRepository;
    //싱글톤 객체를 주입(DI) 받는다는 의미
    @Autowired
    public MemberService(MyMemberRepository memoryRepository) {
        this.memberRepository = memoryRepository;
    }


    //다형성 설계인 경우
//    private final MemberRepository memberRepository;
//    @Autowired
//    public MemberService(MemberSpringDataJpaRepository memoryRepository) {
//        this.memberRepository = memoryRepository;
//    }


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

    //Member와 MemberDetResDto가 같아도 옮겨 담자
    public MemberDetResDto memberDetail(Long id){
//        Member member = memberRepository.findById(id);
//        MemberDetResDto memberDetResDto = new MemberDetResDto();
//        memberDetResDto.setId(member.getId());
//        memberDetResDto.setEmail(member.getEmail());
//        memberDetResDto.setName(member.getName());
//        memberDetResDto.setPassword(member.getPassword());
//
//        return memberDetResDto;

        //springdatajpa로 처리
        //client한테 적절한 예외 메세지와 상태 코드 주는 것이 주요 목적
        //또한 예외를 강제 발생시킴으로서 적절한 롤백처리 하는 것도 주요 목적 -> @Transactional 이 붙어있어야 가능!
        Optional<Member> optMember = memberRepository.findById(id);
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        MemberDetResDto memberDetResDto = new MemberDetResDto();
        memberDetResDto.setId(member.getId());
        memberDetResDto.setEmail(member.getEmail());
        memberDetResDto.setName(member.getName());
        memberDetResDto.setPassword(member.getPassword());

        return memberDetResDto;
    }


    //repository에서 받은 List<Member> -> List<MemberResDto>로 변경
    public List<MemberResDto> memberList(){
        List<Member> memberList= memberRepository.findAll();
        List<MemberResDto> memberResDtoList = new ArrayList<>();
        for(Member m : memberList){
            MemberResDto memberResDto = new MemberResDto();
            memberResDto.setId(m.getId());
            memberResDto.setName(m.getName());
            memberResDto.setEmail(m.getEmail());
            memberResDtoList.add(memberResDto);
        }

        return memberResDtoList;

    }
}
