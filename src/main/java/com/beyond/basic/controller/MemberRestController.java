package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest")
@Api(tags = "회원관리 서비스")
//csr - 클라이언트 사이드 랜더링 -> 화면제공 필요없음.(vue에서 제공)/ 리턴은 json
//RestContoller : 모든 메서드 상단에 @Responseoy한 것과 같음
public class MemberRestController {

    private final MemberService  memberService;
    @Autowired
    MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member/text")
    public String memberText(){
        return "ok";
    }

    @GetMapping("/member/list")
    public ResponseEntity<CommonResDto> memberList(){
        List<MemberResDto> memberList = memberService.memberList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member list successfully found", memberList);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id){
        try{
            MemberDetResDto memberDetResDto = memberService.memberDetail(id);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is found",memberDetResDto.toEntity());
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.NOT_FOUND, e.getMessage());
            return new ResponseEntity<>(commonErrorDto, HttpStatus.NOT_FOUND);

        }
    }

//SQLIntegrityConstraintViolationException - email 중복 에러

    //postman을 통해 json을 받음 -> RequestBody
    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto memberdto){
        try{
            MemberDetResDto memberDetResDto = memberService.memberCreate(memberdto);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", memberDetResDto.toEntity());
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
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
