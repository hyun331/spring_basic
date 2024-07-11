package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//해당 클래스가 Repository 계층임을 표현함과 동시에 싱글톤 객체로 생성
//여러 클래스에서 이 클래스를 사용할 경우 싱글톤으로 만들어서 사용하면 됨. -> new 키워드 사용 안해도 됨. 스프링에서 알아서 객체 하나 만들어둠
@Repository
public class MemberMemoryRepository implements MemberRepository{
    private final List<Member> memberList;

    MemberMemoryRepository(){
        memberList = new ArrayList<>();
    }
    @Override
    public void save(Member member) {
        memberList.add(member);
    }

    @Override
    public List<Member> findAll() {
        return memberList;
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
