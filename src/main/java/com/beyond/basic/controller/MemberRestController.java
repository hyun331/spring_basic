package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.domain.MemberUpdateDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
//csr - 클라이언트 사이드 랜더링 -> 화면제공 필요없음.(vue에서 제공)/ 리턴은 json
//RestContoller : 모든 메서드 상단에 @Responseoy한 것과 같음
public class MemberRestController {

    private final MemberService  memberService;
    @Autowired
    MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }


    @GetMapping("/member/list")
    public List<MemberResDto> memberList(){
        List<MemberResDto> memberList = memberService.memberList();
        return memberList;
    }

    @GetMapping("/member/detail/{id}")
    public MemberDetResDto memberDetail(@PathVariable Long id){
        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
        return memberDetResDto;
    }


    //postman을 통해 json을 받음 -> RequestBody
    @PostMapping("/member/create")
    public String memberCreatePost(@RequestBody MemberReqDto memberdto){
        try{
            memberService.memberCreate(memberdto);
            return "ok";
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return "error!!";
        }
    }


    //수정의 2가지 요청방식 : put, patch
    //PUT - 덮어쓰기
    //PATCH : 부분수정
    @PatchMapping("/member/pw/update")
    public String mamberList(@RequestBody MemberUpdateDto dto){
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return "ok";
    }



}
