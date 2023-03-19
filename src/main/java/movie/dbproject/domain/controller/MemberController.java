package movie.dbproject.domain.controller;


import lombok.extern.slf4j.Slf4j;
import movie.dbproject.domain.service.MemberService;
import movie.dbproject.domain.vo.LoginInfo;
import movie.dbproject.domain.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller @Slf4j
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("/")
    public String mainPage() {
        return "/movie/main_page";
    }

    @RequestMapping("/users/loginForm")
    public String loginForm() {

        return "/members/loginForm";

    }

    @PostMapping("/users/login")
    @ResponseBody
    public LoginInfo login(@ModelAttribute User user, HttpSession httpSession, Model model) {
        LoginInfo loginInfo = new LoginInfo();
        Optional<User> findUser = memberService.findOneById(user.getId());

        if (findUser.isEmpty()) {
            loginInfo.setIdInfo("no_id");
            return loginInfo;
        }
        if (!findUser.get().getPwd().equals(user.getPwd())) {
            loginInfo.setPwInfo("no_pwd");
            return loginInfo;
        }

        httpSession.setAttribute("id", findUser.get().getId());
        httpSession.setAttribute("pwd", findUser.get().getPwd());
        httpSession.setAttribute("name", findUser.get().getName());

        httpSession.setMaxInactiveInterval(3 * 60);
        loginInfo.setSucessInfo("clear");
        log.info("session iD={}", httpSession.getAttribute("id"));
        log.info("session PWD={}", httpSession.getAttribute("pwd"));
        log.info("session Name={}", httpSession.getAttribute("name"));

        return loginInfo;
    }

    @RequestMapping("/users/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("id");
        httpSession.removeAttribute("pwd");
        return "redirect:/";
    }

    @RequestMapping("/users/add")
    public String addForm() {
        return "members/addForm";
    }

    @RequestMapping("/users/addUser")
    public String addUser(@RequestParam String id, @RequestParam String pwd, @RequestParam String name,
                          @RequestParam String p_num,@RequestParam String e_mail,@RequestParam String e_mailAddr) {
        log.info("saveUserId={}", id);
        User user = new User();
        user.setId(id);
        user.setPwd(pwd);
        user.setName(name);
        user.setP_num(p_num);
        user.setE_mail(e_mail+e_mailAddr);
        memberService.join(user);
        return "redirect:/users/loginForm";
    }

    @RequestMapping("/users/addConfirm")
    @ResponseBody
    public LoginInfo addConfirm(@RequestParam String id) {
        log.info("id={}", id);
        LoginInfo loginInfo = new LoginInfo();
        Optional<User> confirmUser = memberService.findOneById(id);
        if (confirmUser.isEmpty()) {
            loginInfo.setIdInfo("yes_id");
        } else {
            loginInfo.setIdInfo("no_id");
        }
        return loginInfo;
    }
}
