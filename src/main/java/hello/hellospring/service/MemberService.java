package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MemberService {
    // 회원 객체 저장하는 레포지토리 인터페이스인 MemberRepository 타입에
    // MemberRepository를 상속받는 MemoryMemberRepository구현체 인스턴스를 생성
    // MemberRepository형으로 선언한 변수를 사용해 구현체의 메서드를 사용

    /*private final MemberRepository memberRepository = new MemoryMemberRepository();*/
    private final MemberRepository memberRepository;

    @Autowired
    // MemService는 memberRepository가 필요, @Autowired하면 MemberService Spring이 생성할 떄
    // @Service -> Container에 등록 -> 생성자 호출 -> @Autowired -> MemberRepository memberRepository
    // -> Container에 있는 Repository를 끼워준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
//        MemberService입장에서 직접 new하지 않음 외부에서 memberRepository를 넣어준다.
//        이런 것을 Dependency Injection이라고 한다.❗❗❗ DI라고한다.
    }

    /*
    * 회원 가입
    */
    public Long join(Member member){
        // 같은 이름이 있는지 중복 회원X


//        Optional<Member> result = memberRepository.findByName(member.getName());
//        // ifPresent: null이 아니라 값이 있으면 동작(Optional이기 떄문에 가능)
//        // 기존에는 ifnull이아니면 으로 했을 것, Optional로 감싸면 여러메서드 사용가능
//        // Member member1 = result.get(); 꺼내고 싶으면get()메서드 사용해서❗ 근데 직접 바로 꺼내는것을 권장하지 않음❌
//        // result.orElseGet(): 값이 있으면 꺼내고 값이 없으면 지정한 메서드를 실행🟠, 많이사용됨❗
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        }); Optional을 바로 꺼내면 안좋으므로 아래처럼 사용 ❗(validateDuplicateMember)
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    /*
    * 전체 회원 조회
    * 서비스 개발 부분은 비즈니스에 의존적으로 설계하고
    * 레파지토리(저장소) 부분은 개발스러운 데이터 입출력에 관한 네이밍 사용
    */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.fidnById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //findByName을 한결과sms Optinal<Member>이므로
                        .ifPresent(m -> { // Optional이므로 .ifPresent 사용
                            throw new IllegalStateException("이미 존재하는 회원");
                        });
        // 함수를 만들고 alt+Enter, Extract method하면 함수를 생성❗❗
    }
}
