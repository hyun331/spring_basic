package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDetResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;

    public MemberDetResDto(Long id, String name, String email, String password, String createdTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }
}
