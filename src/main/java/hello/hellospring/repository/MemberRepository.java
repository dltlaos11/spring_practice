package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

// 회원 객체 저장하는 레포지토리 인터페이스
public interface MemberRepository {
    Member save(Member member);// 회원 저장시 저장된 회원 반환, 회원저장
    Optional<Member> fidnById(Long id); //id로 회원 찾기
    Optional<Member> findByName(String name); // Optional: id, name없으면 null 반환, 반환시 Optional로 감싸서 반환
    List<Member> findAll(); // 지금까지 저장된 회원 리스트로 반환
}
