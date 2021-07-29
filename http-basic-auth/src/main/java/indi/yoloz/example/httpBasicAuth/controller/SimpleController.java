package indi.yoloz.example.httpBasicAuth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yoloz
 *
 * 1. 返回rest json数据
 * 2、3 返回逻辑视图名+数据，逻辑视图名为index，resources/templates 目录下提供一个名为 index.html 的 Thymeleaf 模板文件
 */

//@RestController
@Controller
public class SimpleController {

//    @GetMapping("/")
//    public Map<String, Object> simple() {
//        return new HashMap<String, Object>(){{
//            put("username", "张三");
//            put("sex", "男");
//            put("age", "20");
//        }};
//    }

//    @GetMapping("/")
//    public ModelAndView simple() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("username", "张三");
//        modelAndView.addObject("sex", "男");
//        modelAndView.addObject("age", "20");
//        modelAndView.setViewName("index");
//        return modelAndView;
//    }

    @GetMapping({"","/","/index"})
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            model.addAttribute("username","anonymous");
        }else {
            User user = (User)principal;
            model.addAttribute("username",user.getUsername());
        }
        return "index";
    }

}
