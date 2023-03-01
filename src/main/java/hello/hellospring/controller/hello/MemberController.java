package hello.hellospring.controller.hello;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Spring Container가 처음에 뜰떄(시작할떄) @Controller라는 어노테이션이 있으면 MmeberController라는
// 객체를 만들어서 Spring에 넣어둔다. 그리고 Spring Container에서 Spring 빈이 관리된다고 표현.
@Controller
public class MemberController {
    /*
    private final MemberService memberService = new MemberService();
    // new로 생성할 수도 있지만, Spring이 관리하게 되면 모두 Spring Container에 등록하게 되고 Container로부터
    // 받아서 쓰도록 바꿔야 한다.
    */
    private final MemberService memberService;

    @Autowired
    // MemberController는 Spring Container가 뜰떄 생성(@Controller), 그때 생성자 호출을 한다.
    // 생성자에 @Autowired가 붙으면 memberService를 Spring이 Container에 있는 MemberService와
    // 연결시켜준다❗
    // |Controller - Service| 연결시켜줄떄 생성자에서 @Autowired 사용, MemberContorller가 생성될때
    // Spring 빈에 등록되어 있는 MemberService객체를 가져다가 넣어준다 => Dependency Injection(의존관계 주입)

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    // MemberService는 순수한 clsas이므로 Spring이 아무것도 할수없다, @Service 어노테이션을 붙여야함
    // @Service라고 붙이면 Spring이 Container에 MemberService를 등록해준다❗
    // MemoryMemberRepository구현체에는 @Repository어노테이션을 붙임
    // Controller를 통해서 외부요청을 받고 Service에서 비즈니스 로직을 만들고 Repository에서 데이터를 저장,

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
