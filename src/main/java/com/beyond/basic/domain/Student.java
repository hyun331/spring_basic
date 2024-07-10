package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Student {
    private String name;
    private String email;
    private List<Grade> grades;
}
