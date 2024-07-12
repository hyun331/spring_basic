package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;
import java.util.Optional;

//1개 조회, 전체 조회, 등록, 삭제
public interface MemberRepository {
    //보통 DB에 저장할 때 넣은걸 return 해줌
    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findById(Long id);

}
