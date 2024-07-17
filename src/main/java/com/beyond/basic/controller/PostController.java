package com.beyond.basic.controller;

import com.beyond.basic.domain.PostResDto;
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

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/post/list")
    public List<PostResDto> postListView(){
        return postService.postList();
    }
}
