package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    //회원 목록 조회
    //화면 하나 만들어서 memberList메서드에서 해당 화면 리턴
    @GetMapping("/member/list")
    public String memberList(){
        return "member/member-list";
    }

    //회원 상세조회 : memberDetail
    //url : member/1, member/2
    //화면명 : member-detail
    @GetMapping("/member/{id}")
    //@ResponseBody
    //int 또는 Long으로 받을 경우 스프링에서 자동으로 형변환(String->Long)
    public String memberDetail(@PathVariable Long id, Model model){
        //model.addAttribute();
        return "member-detail";
    }


    //회원가입 화면 제공
    @GetMapping("/member/create")
    public String memberCreateView(){
        return "member/member-create";
    }

    //회원가입 데이터 받기-> name, email, password
    @PostMapping("/member/create")
    @ResponseBody
    public String memberCreateHandle(@RequestBody Member member){
        System.out.println(member);
        return "ok";
    }

}
