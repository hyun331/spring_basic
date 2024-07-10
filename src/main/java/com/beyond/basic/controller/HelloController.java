package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

//해당 클래스가 컨트롤러(사용자의 요청을 처리하고 응답하는 편의기능)임을 명시
@Controller()
//클래스 차원에 url 매핑시 requestMapping사용
@RequestMapping("/hello")
//@RestController //Controller + 각 메서드마다 @ResponseBody


public class HelloController {

    // case1.화면 요청
// case2. @ResponseBody 붙은 경우 단순 String데이터 return
    //GetMapping을 통해 get요청을 처리하고 url 패턴 명시
    @GetMapping("/")
    //resonseBody를 사용하면 화면이 아닌 데이터를 리턴함
    //만약 여기서 resonseBody가 없고 returndl 스트링이면 스프링은 resources/templates폴더 밑에 helloword.html화면을 찾아 리턴
//    @ResponseBody
    public String helloWorld(){
        return "helloworld";
    }


    //case 3. 사용자에게 json 리턴
    //데이터 리턴하되, json 형태로 리턴
    @GetMapping("/json")
//    @RequestMapping(value = "/json", method = RequestMethod.GET) //메서드차원에서도 requestMapping 사용 가능
    @ResponseBody
    //responsebodyㄹ르 사요하며서 객체를 return시 자동으로 직렬화
    public Hello helloJson () throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("안녕하세요");
        hello.setEmail("hello@naver.com");

        //objectMapper를 사용해서 수동으로 String 만들기.
        //이렇게 하지 않아도 됨.
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello);
//        return value;
        
        //리턴타입이 Hello면 -> 알아서 직렬화됨
        return hello;
    }

    //case4. 사용자가 json데이터 요청하되, parameter형식으로 특정 객체 요청
    //get 요청중에 특정 데이터를 요청
    @GetMapping("/param1")
    @ResponseBody
    //파라미터 형식 : ?name=kim -> name이 kim인 사람을 찾아오기
    //@RequestParam : name의 값을 가져와서 inputName에 넣기
    public Hello param1(@RequestParam(value = "name")String inputName){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputName+"@naver.com");
        System.out.println(hello.toString());
        return hello;
    }

    //url 패턴 model-param2. 메서드 modelParam2
    //parameter 2개  name, email -> hello객체 생성 후 리턴
    //요청방식 : ?name:xxx&email=xxx@naver.com
    //접속 : http://localhost:8080/hello/model-param2?name=park&email=park@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello param2(@RequestParam(value = "name")String inputName, @RequestParam(value = "email")String inputEmail){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }

    //case 5 : parameter형식으로 요청하되 ,서버에서 데이터바인딩 하는 형식
    @GetMapping("/param3")
    @ResponseBody
    //parameter가 많을 경우 객체로 대체가 가능 .객체에 각 변수에 맞게알아서 바인딩함(데이터바인딩)
    //요청방식 : http://localhost:8080/hello/param3?name=park&email=park@naver.com11&password=123456
    //객체의 Setter를 이용해서 만들어주기 때문에 변수명이 정확해야함
    //데이터바인딩(묶다. 매핑) 조건 : 기본 생성자 + Setter(controller계층에서는 setter을 사용할 수 있다. DTO)
    public Hello param3(Hello hello){
        return hello;
    }


    //case 6 : 서버에서 화면에 데이터를 넣어 사용자에게 return(model객체 사용)
    //ssr 형식 : 서버 사이드 랜더링
    //RestController 사용하면 데이터 리턴이 되기 때문에 사용 안됨!
    //Model model을 통해 .html(화면)에 inputName(데이터)를 넣어서 랜더링
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name")String inputName, Model model){
        //model 객체에 name이라는 키와 inputNum value 세팅 -> 화면으로 전달됨
        //helloworld.html에서 name 변수 사용 가능
        model.addAttribute("name", inputName);  
        return "helloworld";
    }



    //case 7 : pathvariable 방식을 통해 사용자로부터 값을 받아 화면 리턴
    //localhost:8080/hello/model-path/1 == 1번에 대한 정보 요청 or /hello/model-path?id=1(파라미터 방식)
    //pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀 더 restful한 형식
    //restful : http통신의 원칙 중 하나.
    //parameter 방식보다 이 방식을 지향하자
    @GetMapping("/model-path/{name}")   //{name}이 아래의 @PathVariable String name으로 들어감
    public String modelPath(@PathVariable String name, Model model){
        model.addAttribute("name", name);
        return "helloworld";

    }


    /////////////////////POST요청/////////////////////////
    // post: 사용자 입장에서 서버에 데이터를 주는 상황
    // 요청방식  : 1)html에서 form태그 사용(url인코딩-txt만, multipart/form-data), 2)js 사용(json, formdata객체))

    //case 1 : url 인코딩 방식(text만 전송)
    //name, email, password 입력할 수 있는 화면을 주는 메서드 정의
    //메서드 정의 : formView, 화면명 : form-view
    @GetMapping("/form-view")
    public String formView(){
        return "form-view";
    }
    //post요청 (사용자가 서버에 데이터 전송)
    //형식 : key1=value1&key2=value2&key3=value3 형식으로 데이터가 옴
    @PostMapping("/form-post1") //getmapping과 같으 url 패턴도 가능함
    @ResponseBody
    public String formPost1(@RequestParam(value = "name")String inputName,
                            @RequestParam(value = "email")String inputEmail,
                            @RequestParam(value = "password")String inputPassword){
        System.out.println(inputName+", "+inputEmail+", "+inputPassword);
        return "ok";

    }
    /////formPost1은 너무 길다! Hello객체로 받아보자!////
    @PostMapping("/form-post2") //getmapping과 같으 url 패턴도 가능함
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello){   //ModelAttribute 생략 가능
        System.out.println(hello);
        return "ok";

    }



    //case 2: multipart/form-data 방식(text과 파일 전송)
    //url명 : form-file-post, 메서드명 formFilePost, 화면명 form-file-view
    //form태그 name, email, password, file
    @GetMapping("/form-file-post")
    public String formFilePost(){
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    //html에서 encodingtype이 multipart/form-data였음  -> java에서 이를 위해 MultipartFile 제공
    public String formFileHandle(Hello hello, @RequestParam(value = "photo")MultipartFile photo){
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());    //콘솔에서 이미지 출력 어려움 -> 파일명만 출력해보기
        return "ok";
    }




    //case3 : js를 활용한 form 데이터 전송(text)
    @GetMapping("/axios-form-view")
    public String axiosFormView(){
        //name, email, password를 전송
        return "axios-form-view";
    }

    //case 2와 다르게 ok화면이 뜨지않음.아마 axios response.data에 return값 들어감
    //ascios를 통해 넘어오는 형식이 key=value&key=value형식임
    @PostMapping("/axios-form-view")
    @ResponseBody
    public String axiosFormPost(Hello hello){
        System.out.println(hello);
        return "ok";
    }


    //case4 : js를 활용한 formData전송(+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView(){
        return "axios-form-file-view";
    }

    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello, @RequestParam(value = "file")MultipartFile file){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";

    }

    ////////////////////0710

    //case5 : js를 활용한 json 데이터 전송
    //url패턴 : axios-json-view, 화면명 : axios-json-view, get요청 메서드 동일.
    //post요청 메서드 : axiosJsonPost
    @GetMapping("/axios-json-view")
    public String axiosJsonView(){
        return "axios-json-view";
    }

    @PostMapping("/axios-json-view")
    @ResponseBody
    //JSON으로 전송한 데이터를 받을 때는 @RequestBody 어노테이션 사용
    public String axiosJsonPost(@RequestBody Hello hello){
        System.out.println(hello);
        return "ok";
    }




    //case6 : js를 활용한 json데이터 전송(file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView(){
        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-file-view1")
    @ResponseBody
    //파일은 json에 들어갈 수 없음 -> @RequstBody 사용 불가
    //RequestPart : JSON + 파일을 처리할 때 주로 사용하는 어노테이션
    public String axiosJsonFilePost1(@RequestParam("hello") String hello, @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        //axios-json-file-view.html에서 formData.append('hello', helloJson); 이렇게 줄 때
        //String으로 받은 뒤 수동으로 처리하기
        ObjectMapper objectMapper = new ObjectMapper();
        Hello h1 = objectMapper.readValue(hello, Hello.class);
        System.out.println(h1);

        System.out.println(file.getOriginalFilename());
        return "ok";
    }
    @PostMapping("/axios-json-file-view2")
    @ResponseBody
    //form 데이터를 통해 josn, filter를 처리할 땐 RequestPart 어노테이션 많이 사용
    public String axiosJsonFilePost2(@RequestPart("hello") String hello, @RequestPart("file") MultipartFile file){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }


    //case7 : js를 활용한 json데이터 전송(멀티 file)
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView(){
        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-multi-file-view2")
    @ResponseBody
    public String axiosJsonMultiFilePost(@RequestPart("hello") String hello, @RequestPart("file") MultipartFile file) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

}



