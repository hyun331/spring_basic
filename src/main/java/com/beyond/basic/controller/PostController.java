package com.beyond.basic.controller;

import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import com.beyond.basic.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
@Api(tags = "게시글 관리 서비스")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository){
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping("/post/list")
    public List<PostResDto> postListView(){
        return postService.postList();
    }


    //lazy지연로딩, eager즉시로딩 테스트
    @GetMapping("/post/member/all")
    public void postMemberAll(){
        System.out.println(postRepository.findAll());
    }
}
