package com.beyond.basic.controller;

import com.beyond.basic.domain.CommonResDto;
import com.beyond.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {
    //Response할 때 단순 데이터 뿐 만 아니라 Status Code, Status message도 추가

    // case 1. @ResponseSatus 어노테이션 방식

    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.CREATED)
    public String anotation1(){
        return "ok";
    }

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member anotation2(){
        // 객체 생성 후 db 저장 성공
        Member member = new Member("hong", "hong@dkddk", "1234");
        return member;
    }

    // case2. Method 체이닝 방식 : ResponseEntity의 클래스메서드 사용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1(){
        Member member = new Member("hong", "hong@dkddk", "1234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2(){
        Member member = new Member("hong", "hong@dkddk", "1234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> chainin3(){
        return ResponseEntity.notFound().build();
    }


    //case3. ResponseEntity객체를 직접 custom하여 생성하는 방식
    //이 방식을 쓸 예정
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("hong", "hong@dkddk", "1234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }


    //body에도 status_code, status_message 넣기
    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2(){
        Member member = new Member("hong", "hong@dkddk", "1234");
        //body에 들어가도록
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", member);
        //header에 들어가는 내용
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);

    }


}
