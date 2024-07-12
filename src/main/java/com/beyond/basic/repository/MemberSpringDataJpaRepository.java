package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//springDataJpa : JpaRepository를 상속받고 상속시 Entity명과 entity의 PK타입을 명시
//MemberRepository, JpaRepository 둘다 findById 있는데 리턴타입이 다름 -> MemberRepository의 리턴타입 Optional<Member>로 변경
//MemberSpringDataJpaRepository는 JpaRepository를 상속함으로서 JpaRepository의 주요 기능을 상속
//JpaRepository가 인터페이스임에도 해당  기능을 상속해서 사용할 수 있는 이유는ㄴ hibernate구현체가 미리 구현되어 있기 때문
//런타임 시점에 사용자가 인터페이스에 저장한 메서드를 프록시(대리인) 객체가 리플렉션 기술을 통해 메서드를 구현
public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndName(String email, String name);
}
