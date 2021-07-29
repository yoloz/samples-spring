package indi.yoloz.example.httpformLogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yoloz
 */

@Controller
public class SimpleController {

    @RequestMapping("/login")
    public String login() {
        return "index";
    }

    @PostMapping("/index")
    public ModelAndView index(@RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", username);
        modelAndView.setViewName("index");
        return modelAndView;
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
