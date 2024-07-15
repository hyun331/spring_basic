package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResDto {
    private Long id;
    private String name;
    private String email;

    public MemberResDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
