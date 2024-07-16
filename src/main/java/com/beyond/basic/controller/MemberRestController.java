package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<CommonResDto> memberDetail(@PathVariable Long id){
        MemberDetResDto memberDetResDto = null;
        CommonResDto commonResDto = null;
        try{
            memberDetResDto = memberService.memberDetail(id);
            commonResDto = new CommonResDto(HttpStatus.OK, "member detail success",memberDetResDto.toEntity());
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);


        }catch (EntityNotFoundException e){
            e.printStackTrace();
            commonResDto = new CommonResDto(HttpStatus.NOT_FOUND, "member detail failed", memberDetResDto);

            return new ResponseEntity<>(commonResDto, HttpStatus.NOT_FOUND);

        }
    }


    //postman을 통해 json을 받음 -> RequestBody
    @PostMapping("/member/create")
    public ResponseEntity<CommonResDto> memberCreatePost(@RequestBody MemberReqDto memberdto){
        MemberDetResDto memberDetResDto = null;
        CommonResDto commonResDto;
        try{
            memberDetResDto = memberService.memberCreate(memberdto);
            commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", memberDetResDto.toEntity());
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            commonResDto = new CommonResDto(HttpStatus.BAD_REQUEST, "member isn't created", memberDetResDto);
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
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
