package indi.yoloz.example.oauth2ssoclientusermsg.controller;

import indi.yoloz.example.oauth2ssoclientusermsg.model.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yoloz
 */

@Controller
public class UserMsgController {

    @RequestMapping({"/", "/list"})
    public String list(HttpServletRequest request, Model model) {
        List<UserInfo> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(i);
            userInfo.setName("test" + i);
            userInfo.setAge(random.nextInt(50));
            userInfo.setPhone("1547216148" + i);
            userInfo.setEmail("test" + i + "@126.com");
            userInfo.setAddr("浙江省杭州市滨江区");
            list.add(userInfo);
        }
        model.addAttribute("userList", list);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",principal);
        return "list";
    }


    @RequestMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @RequestMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }

    @PreAuthorize("hasAuthority('member:save')")
    @ResponseBody
    @RequestMapping("/add")
    public String add() {
        return "add";
    }

    @PreAuthorize("hasAuthority('member:detail')")
    @ResponseBody
    @RequestMapping("/detail")
    public String detail() {
        return "detail";
    }
}
