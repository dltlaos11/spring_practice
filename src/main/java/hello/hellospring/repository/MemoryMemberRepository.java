package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

// 구현체
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    // Map으로 저장<id, name>,
    // 동시성문제, 공유되는 변수에는 concurrentHasdhMap적용해야..
    private static long sequence = 0L;// 0,1,2.. key값 생성, 마찬가지 동시성문제로 Atomic적용해야..

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> fidnById(Long id) {
        return Optional.ofNullable(store.get(id));
        // null일 가능성, client에서 처리
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // param으로 넘어온 name과 같은경우에만 필터링
                .findAny(); // 하나라도 찾는것,
        // 람다
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    // 구현이 끝났으니 테스트케이스를 통해서 검증해야함.
}
