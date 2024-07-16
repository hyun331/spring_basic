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

    private final MemberService  memberService;
    @Autowired
    MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 목록 조회
    //화면 하나 만들어서 memberList메서드에서 해당 화면 리턴
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

    //회원 상세조회
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
