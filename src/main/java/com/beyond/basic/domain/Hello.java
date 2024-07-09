package com.beyond.basic.domain;

import lombok.*;

//@Getter
//@Setter
@Data //getter, setter, toString 등을 포함
@NoArgsConstructor  //기본생성자 넣는 어노테이션
@AllArgsConstructor // 모든 변수를 사용한 생성자를 만드는 어노테이션
 public class Hello{
    private String name;
    private String email;
    private String password;

//    @Override
//    public String toString(){
//        return "name : "+this.name+", email : "+this.email+", password : "+ this.password;
//    }

 }