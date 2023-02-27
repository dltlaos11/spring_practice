package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // @Configuration 해당 어노테이션을 읽고
public class SpringConfigure {
    @Bean // @Bean Spring 빈을 스프링 컨테이너에 등록
    public MemberService memberService() {
        return new MemberService(memberRepository());
        // 스프링 빈에 등록되어 있는 memberRepository()를 MemberService에 넣어준다.
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

//
//    memberController -> memberService -> memberRepository

}
