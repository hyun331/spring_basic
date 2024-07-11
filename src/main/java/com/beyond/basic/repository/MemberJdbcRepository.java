package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;

public class MemberJdbcRepository implements MemberRepository{
    @Override
    public void save(Member member) {

    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
