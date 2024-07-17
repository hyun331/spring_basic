package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.*;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//input값의 검증 및 실질적 비지니스 로직은 서비스 계층에서 수행

@Service    //서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성. 스프링이 알아서 service 객체 1개를 생성함.
//Transcational 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고(각 메서드마다 하나의 트랜잭션으로 묶는다.)
//만약 예외가 발생하면 롤백처리 자동화
//각 메서드마다 @Transactional을 붙일 수 있음. 근데 필요없는 곳에는 붙이지 말기
//Service단에 붙이면 아래 모든 메서드에 @Transactional 한것과 동일
@Transactional(readOnly = true)
public class MemberService {
    //최초에 생성하면 재할당 안되게
    private final MyMemberRepository memberRepository;
    //싱글톤 객체를 주입(DI) 받는다는 의미
    @Autowired
    public MemberService(MyMemberRepository memoryRepository) {
        this.memberRepository = memoryRepository;
    }



    @Transactional
    public MemberDetResDto memberCreate(MemberReqDto memberdto) {
        if(memberdto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        if(memberRepository.findByEmail(memberdto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }
        //받아온 reqDto를 Service에서 객체로 만들어주기
        Member member = memberdto.toEntity();
        Member newMember = memberRepository.save(member);

        //Transactional rollback test
        //여기서 예외터지면 save가 취소됨
        if(member.getName().equals("kim"))
            throw new IllegalArgumentException("예외");
        return newMember.detFromEntity();
    }

    //Member와 MemberDetResDto가 같아도 옮겨 담자
    public MemberDetResDto memberDetail(Long id){
        //springdatajpa로 처리
        //client한테 적절한 예외 메세지와 상태 코드 주는 것이 주요 목적
        //또한 예외를 강제 발생시킴으로서 적절한 롤백처리 하는 것도 주요 목적 -> @Transactional 이 붙어있어야 가능!
        Optional<Member> optMember = memberRepository.findById(id);
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
//        MemberDetResDto memberDetResDto = new MemberDetResDto();
//        memberDetResDto.setId(member.getId());
//        memberDetResDto.setEmail(member.getEmail());
//        memberDetResDto.setName(member.getName());
//        memberDetResDto.setPassword(member.getPassword());
//        LocalDateTime createdTime = member.getCreatedTime();
//        String value = createdTime.getYear()+"년"+createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
//        memberDetResDto.setCreatedTime(value);
        MemberDetResDto memberDetResDto = member.detFromEntity();
        System.out.println("글쓴이의 게시글 개수 : "+member.getPosts().size());
        for(Post p : member.getPosts()){
            System.out.println("글 제목 : "+p.getTitle());
        }
        return memberDetResDto;
    }


    //repository에서 받은 List<Member> -> List<MemberResDto>로 변경
    public List<MemberResDto> memberList(){
        List<Member> memberList= memberRepository.findAll();
        List<MemberResDto> memberResDtoList = new ArrayList<>();
        for(Member m : memberList){
            MemberResDto memberResDto = new MemberResDto(m.getId(), m.getName(), m.getEmail());
            memberResDtoList.add(memberResDto);
        }

        return memberResDtoList;

    }

    public void pwUpdate(MemberUpdateDto dto){
        Member member = memberRepository.findById(dto.getId()).orElseThrow(()->new EntityNotFoundException("member is not found"));
        if(member!=null){
            member.updatePW(dto.getPassword());

            //기존 객체를 조회 후 수정한 다음에 save()시에는 jpa가 update를 실행
            //save는 추가, 수정 둘의 기능이 있음
            memberRepository.save(member);
        }
    }


    public void deleteMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("member is not found"));
        memberRepository.delete(member);
    }


}

