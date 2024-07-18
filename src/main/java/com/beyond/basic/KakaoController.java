package com.beyond.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoController {

    @GetMapping("/kakao/login")
    public String loginView(){
        return "kakaoLoginTest";
    }
}
