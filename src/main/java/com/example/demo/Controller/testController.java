package com.example.demo.Controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.Repository.adminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/testController") // 실제 URL 에 들어가는 이름
public class testController {

    @Autowired
    private PasswordEncoder pwEncoder;

    @Autowired
    private adminRepository alRepo;

    // http://localhost:8080/testController/ControllerTest01
    @GetMapping("ControllerTest01") // 실제 URL 에 들어가는 이름
    public String ControllerTest01() {

        return "test/ControllerTest01"; // 내 템플릿 디렉터리안 구조, 맨 앞에 / 붙이면안댐.. prefix (앞머리) 가 타임리프 경로로 잡고있음..
    }

    @GetMapping("Cal")
    public String Cal(Model model, HttpServletRequest request, @RequestParam int X, @RequestParam int Y) {

        // http://localhost:8080/testController/Cal
        // 위 URL이 이 메서드의 주소가 됩니다. 여기다가 x와 y를 던져 result 를 뿌립니다. 그럼 위 주소가
        // http://localhost:8080/testController/Cal?X=5&Y=7
        // URL 파라미터를 던질 수 있는데, 주소 마지막에 물음표 ? 으로 시작하고 변수의 구분은 & 앤드기호입니다.

        // 이 메서드의 파라미터를 하나씩 설명하자면,
        // model은 여기서 계산된 결과를 html에서 사용할 수 있는 변수로 만들어 줍니다.
        // request 는 이 페이지에서 받은 파라미터들을 땡겨오는 역할을 합니다.
        // @RequestParam 어노테이션은 변수이름을 정의 하면서 받아오는데 원래 사용방식은 @RequestParam("X") 이지만, 생략시
        // 지역변수 이름과 같게 동작합니다.
        // 이거 내용 어려우니까 이해안되면 바로 물어보세용

        // 자 계산기를 만들어보면!
        int result = 0; // 유일하게 result 만 변수 선언이 없죠

        result = X + Y;

        // 이 계산된 result 를 html 에 보내는 작업을 'model' 에 올린다 라고 합니다.
        model.addAttribute("result", result); // 올릴 변수명, 실제 변수

        // 추가로 x , y 도 확인해야하니까 같이 올려줍시다.
        model.addAttribute("X", X); // 올릴 변수명, 실제 변수
        model.addAttribute("Y", Y); // 올릴 변수명, 실제 변수

        // 그리고 viewpage 가줍시다.

        return "test/Cal";
    }

    // securityencoding test
    @GetMapping("encoded")
    public String encoded(Model model, @RequestParam(required = false) String pw) {

        String encodedPW = pwEncoder.encode(pw);
        encodedPW = "$2a$10$XVZO5rLfbzd5yj3wSATC4ebN2RzmsUuoH7Ba2wvyVbsmorCWWAmCq";

        model.addAttribute("pw", pw);
        model.addAttribute("encodedPW", encodedPW);

        model.addAttribute("result", pwEncoder.matches(pw, encodedPW));

        return "test/sessiontest";
    }

    // PW 검증
    @GetMapping(value = "IDPW")
    public String IDPW() {
        return "test/IDPW";
    }

    @PostMapping("getEncoded")
    @ResponseBody
    public String getEncoded(@RequestParam(required = true) String pw) {

        return pwEncoder.encode(pw);

    }

    @PostMapping("compare")
    @ResponseBody
    public String compare(@RequestParam String pw, @RequestParam String pw1, @RequestParam String pw2) {

        if (pwEncoder.matches(pw, pw1) && pwEncoder.matches(pw, pw2)) {
            return "일치";
        } else {
            return "불일치";
        }

    }

    @PostMapping("logintest")
    @ResponseBody
    public String logintest(@RequestParam String userid, @RequestParam String userpw) {

        String encodedPW = alRepo.getUserPw(userid);

        if (pwEncoder.matches(userpw, encodedPW)) {
            return "일치";
        } else {
            return "불일치";
        }

    }

}
