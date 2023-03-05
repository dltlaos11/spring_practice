package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration // @Configuration 해당 어노테이션을 읽고
public class SpringConfigure {

    @Autowired
    DataSource dataSource;
//    private DataSource dataSource; 위와 같은 코드. 자체적으로 DI
//    @Autowired
//    public SpringConfigure(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
    // @Configuration한것도 Spring bean으로 관리하기 떄문에 스프링 부트가 스프링 부트 데이터베이스 연결 설정 파일을 보고
    // 자체적으로 dataSource(데이터베이스 정보가 있고 연결할 수 있는, ) 빈을 만들어준다🟠

    @Bean // @Bean Spring 빈을 스프링 컨테이너에 등록
    public MemberService memberService() {
        return new MemberService(memberRepository());
        // 스프링 빈에 등록되어 있는 memberRepository()를 MemberService에 넣어준다.
    }
    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource); // 순수 JDBC
        // 객체지향적인 설계가 좋은이유, 소위 다형성을 활용한다고 한다.
        // 인터페이스를 두고 구현체를 바꿔끼기 할 수 있다🟠 이것으 스프링 컨테이너가 지원
    }

//
//    memberController -> memberService -> memberRepository

}
