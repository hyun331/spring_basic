package com.beyond.basic.domain;

import lombok.*;

//@Getter
//@Setter
@Data //getter, setter, toString 등을 포함
@Builder
@AllArgsConstructor // 모든 변수를 사용한 생성자를 만드는 어노테이션
@NoArgsConstructor  //기본생성자 넣는 어노테이션
 public class Hello{
    private String name;
    private String email;
    private String password;

//    @Override
//    public String toString(){
//        return "name : "+this.name+", email : "+this.email+", password : "+ this.password;
//    }


    //빌더패턴 직접구현
    //빌더 적용 대상 생성자가 필요
    public Hello(HelloBuilder helloBuilder){
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

    public static HelloBuilder builder(){
        return new HelloBuilder();
    }

    //static 내부 클래스
    public static class HelloBuilder{
        private String name;
        private String email;
        private String password;

        public HelloBuilder name(String name){
            this.name = name;
            return this;
        }
        public HelloBuilder email(String email){
            this.email = email;
            return this;
        }
        public HelloBuilder password(String password){
            this.password = password;
            return this;
        }
        public Hello build(){
            return new Hello(this);
        }
    }
 }