package hello.hellospring.repository;

import hello.hellospring.domain.Member;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
//    MemberRepository repository = new MemoryMemberRepository();
MemoryMemberRepository repository = new MemoryMemberRepository();
// Test Class를 먼저 만들고 MemoryMemberRepository를 작성할 수도 있다(지금은 반대로 했지만)
// -> 테스트주도개발(TDD), 테스트를 먼저 만들고 구현 클래스를 만들어서 돌려보는 것

    @AfterEach // 메서드마다 끝나고 DB 삭제 실행 ❗❗
    // 각 Test간 의존관계가 없이 실행되어야함. 하나의 test가 끝날때마다 저장소나 공용데이터를 초기화
    public void afterEach() {
        repository.clearStore();
        // test가 실행되고 끝날때마다 한번씩 repository를 지운다.
    }

        @Test // @Test로 실행할 수 있음
        public void save(){
            Member member = new Member();
            member.setName("spring"); // ctrl+shift+enter

            repository.save(member);

            Member result = repository.fidnById(member.getId()).get();
            // 확인, Optional에서 값을 꺼낼때는 get()으로 꺼낼 수 있음,
            // get()으로 바로 꺼내는게 좋은 방법은 아니지만 test코드라서,,
//            System.out.println("result =  "+ (result == member)); result =  true
            // 글자로 볼 수없으니 Assertions라는 기능을 제공

//            Assertions.assertEquals(member, result); // 성공시 아래콘솔창에 파란불뜸, 요즘에는 org.assertj를 많이 사용
            assertThat(member).isEqualTo(result); // alt + enter하고 static import하면 Assertions 생략 가능
        }
        @Test
        public void findByName() {
            Member member1 = new Member();
            member1.setName("spring1");
            repository.save(member1);

            Member member2 = new Member(); // shift + F6: 동일한 이름 찾아서 바꿔줌
            member2.setName("spring2");
            repository.save(member2);

            Member result = repository.findByName("spring1").get(); // get()하면 Optional한번 까서 꺼낼수 있음.
            assertThat(result).isEqualTo(member1);
        }
        @Test
        public void findAll(){
            Member member1 = new Member();
            member1.setName("spring1");
            repository.save(member1);

            Member member2 = new Member(); // shift + F6: 동일한 이름 찾아서 바꿔줌
            member2.setName("spring2");
            repository.save(member2);

            List<Member> result = repository.findAll();

            assertThat(result.size()).isEqualTo(2);
        }


}
