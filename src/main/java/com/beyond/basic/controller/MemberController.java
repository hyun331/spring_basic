package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//싱글톤
@Controller
//@RequiredArgsConstructor - 의존성 주입 3번째 방식
public class MemberController {

    //의존성 주입(DI) 3가지
    //1. 생성자 주입 방식 (가장 많이 사용하는 방식)
    //장점 : final을 통해 상수로 사용 가능. 다형성 구현 가능. 순환 참조 방지
    //순환참조 : controller에서 service Autowired + service에서 controller autowired
        //이런 경우 생성자 방식에서는 컴파일 시점에서 오류
        //필드주입 방식에선 런타임 시점에서 오류
    private final MemberService  memberService;
    @Autowired
    MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //2. 필드 주입 방식(Autowired만 사용)
    //final 못붙임.(final 쓰려면 초기화해야함). -> 재할당 가능해짐
    //다형성 구현 불가
//    @Autowired
//    private MemberService memberService;

    //3. 어노테이션(RequiredArgsConstructor)을 이용하는 방식
    //RequiredArgsConstructor : @NonNull어노테이션, final키워드가 붙어 있는 대상으로 생성자 생성
    //@RequiredArgsConstructor은 class 위에 작성
    //1번과 동일한 방식이지만 다형성에 문제 발생
    //final 키워드 사용
//    private final MemberService memberService;
//    //nonnull 어노테이션 사용
//    @NonNull
//    private MemberService memberService1;


    //회원 목록 조회
    //화면 하나 만들어서 memberList메서드에서 해당 화면 리턴
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

    //회원 상세조회 : memberDetail
    //url : member/1, member/2
    //화면명 : member-detail
    @GetMapping("/member/detail/{id}")
    //@ResponseBody
    //int 또는 Long으로 받을 경우 스프링에서 자동으로 형변환(String->Long)
    public String memberDetail(@PathVariable Long id, Model model){
        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDetResDto);
        return "member/member-detail";
    }


    //회원가입 화면 제공
    @GetMapping("/member/create")
    public String memberCreateView(){
        return "member/member-create";
    }

    //회원가입 데이터 받기-> name, email, password
    @PostMapping("/member/create")
    public String memberCreatePost(MemberReqDto memberdto, Model model){
        try{
            memberService.memberCreate(memberdto);
            //화면 리턴이 아닌 url 재호출
            return "redirect:/member/list";
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }

    }

    @GetMapping("/")
    public String home(){
        return "member/home";
    }

}
