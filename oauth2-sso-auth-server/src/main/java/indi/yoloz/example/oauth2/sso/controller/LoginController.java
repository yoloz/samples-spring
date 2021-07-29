package indi.yoloz.example.oauth2.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yoloz
 */

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("failLogin")
    public String failLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Object error = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "fail";
    }
}
