package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@SpringBootApplication의 역할 : ComponentScanner를 가지고 있음
//ComponentScanning을 수행
@SpringBootApplication
//주로 web서블릿 기반의 구성요소를 스캔ㄴ하고 자동ㅇ으로 등록하는 기능
//예를 ㄷ르어 @WebServlet, @WebFileer, @WebListener
@ServletComponentScan
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
