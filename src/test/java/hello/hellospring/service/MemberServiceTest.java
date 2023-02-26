package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 서비스 로직에서 ctrl+shift+T누르면 해당 서비스 로직에서 바로 TDD가능

    /*MemberService memberService = new MemberService();*/
    MemberService memberService;
    /*MemoryMemberRepository memberRepository = new MemoryMemberRepository();*/
    MemoryMemberRepository memberRepository;
    // 문제
    // static은 class 레벨에 붙음(같이 load)
    // MemoryMemberRepository에서 store에 static 제거시, 다른 db가 되면서 문제가 생김
    // MemoryMemberRepositoryTest와 MemberServiceTest에서 사용하는 Repository는 같아야하는데
    // 다른 Repository를 사용(각자 인스턴스 생성해서 사용중)
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    // 각 테스트 수행하기 전에(@BeforeEach), MemoryMemberRepository를 만들고
    // MemberService에 넣어준다. 그러면 같은 MemoryMemberRepository가 사용됨.
    @AfterEach // 메서드마다 끝나고 DB 삭제 실행 ❗❗
    // 각 Test간 의존관계가 없이 실행되어야함. 하나의 test가 끝날때마다 저장소나 공용데이터를 초기화
    public void afterEach() {
        memberRepository.clearStore();
        // test가 실행되고 끝날때마다 한번씩 repository를 지운다.
    }
    @Test
    void 회원가입() {
        // given, when, then pattern
        // given 어떤 데이터를 기반으로 하는지
        Member member = new Member();
        member.setName("hello");
        // when 어떤걸 검증하는지
        Long saveId = memberService.join(member);
        // then 어떤게 검증되는지
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외(){
        // 회원가입에서 예외가 터지는 테스트 또한 중요
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        // try-catch로도 가능 하지만 더 나은 방법이 존재❗
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
        /*try{
            memberService.join(member2);
            fail();
        } catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
        }*/

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}