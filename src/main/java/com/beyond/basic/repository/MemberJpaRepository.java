package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository{
    //EntityManager는 JPA의 핵심 클래스(객체)
    //Entity의 생명주기를 관리, 데이터베이스와의 모든 인터페이싱을 책임
    //즉, 엔티티를 대상으로 crud하는 기능을 제공
    @Autowired
    private EntityManager entityManager;

    @Override
    public Member save(Member member) {
        //persist : 전달된 엔티티가 EntityManager의 관리 상태가 되도록 만들어주고 트랙잭션이 커밋될 때 데이터베이스에 저장 (insert)

        entityManager.persist(member);
        return null;
    }

    @Override
    public List<Member> findAll() {
        //jpql : jpa의 raw쿼리 문법(객체지향)
        //jpa에서는 spql 문법 컴파일에러가 나오지 않으나, springdatajpa에선 컴파일에러 발생
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();

        return memberList;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //entitymanager를 통해 find(리턴타입 클래스 지정 및 매개변수로 pk 필요)
        //찾아서 자동으로 객체를 만들어주기 때문에 어떤 클래스로 만들지 명시해주기
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }


    //pk 이외의 컬럼으로 조회할때 jpql문법으로 raw쿼리 비슷하게 직접 쿼리 작성
    public Member findByEmail(String email){
        //: email    jdbc의 ?과 같은 역할
        //.setParameter() email값 넣어주기
        //getSingleResult() : 하나만 가져올 때
        Member member = entityManager.createQuery("select m form Member m where m.email= :email", Member.class).setParameter("email", email).getSingleResult();
        return member;
    }
}
