package hello.hellospring.controller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

//    웹 어플리케이션에서 /hello로 들어오면 아래의 메서드를 호출
//    model은 mvc에서 model
//    http get 메서드와 동일, /hello, Spring이 model을 만들어서 넣어주고 key: "data", value: "hello!"
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
//        resources/templates/에 있는 hello.html로 가서 렌더링해라🔵
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // Model model 모델에 담고 View에서 렌더링하기 위함

        model.addAttribute("name", name);
        // "name"은 key, name은 파라미터로 넘겨받은 것

        return "hello-template";
        // hello-template으로 이동
    }

    @GetMapping("hello-spring")
    @ResponseBody
    // http에서 통신 프로토콜에서 header부와 body부로 나뉘는데 body부에 return값을 직접 넣어주는 것
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // "hello spring"
    }


    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        // 문자열이 아닌 객체를 넘김
        return hello;
    }

    static class Hello {
        private String name;
        // java bean 규약, property 접근방식
        // private 외부에서 접근❌, 메서드를 통해 접근
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
