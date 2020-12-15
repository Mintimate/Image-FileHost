package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.UserAdmin;
import cn.mintimate.filecloudplus.service.ImageTypeService;
import cn.mintimate.filecloudplus.service.UserAdminService;
import cn.mintimate.filecloudplus.util.sendEmailTool;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Controller
@RequestMapping("/userAdmin")
public class UserAdminController {
    @Autowired
    private UserAdminService adminService;
    @Autowired
    private ImageTypeService imageTypeService;
    @Autowired
    private sendEmailTool emailTool;
    private UserAdmin userAdmin=new UserAdmin();

//    访问管理员管理页面
    @GetMapping("/admin")
    public String admin(HttpServletRequest req,Model model) {
        model.addAttribute("imageTypes", imageTypeService.list());
        HttpSession session = req.getSession();
        if(!session.getAttribute("userRole").equals(99)){
            return "manager/consumer";
        }
        return "manager/admin";
    }

    @PostMapping("/enter")
    public String enter(HttpServletRequest req, @RequestParam(value = "username",required = false) String username, @RequestParam(value = "password",required = false) String password) {
        QueryWrapper wrapper = new QueryWrapper();
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        wrapper.allEq(map);
        UserAdmin userAdmin=null;
        userAdmin=adminService.getOne(wrapper);
        if (userAdmin!=null) {
            HttpSession session = req.getSession();
            session.setAttribute("sessionEmail",userAdmin.getUserEmail());
            session.setAttribute("sessionUser", userAdmin.getUsername());
            session.setAttribute("userRole",userAdmin.getUserRole());
            return "index";
        }
        return "manager/login";

    }

    // 拦截登录请求到登录页面
    @RequestMapping("/login")
    public String login(){
        return "manager/login";
    }

    // 注册请求
    @RequestMapping("/register")
    public String register(){
        return "manager/register";
    }

    // 注册信息验证
    @PostMapping("/register")
    @ResponseBody
    public String Register(@RequestParam(value = "userEmail") String email,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "code") String code ,
                           HttpServletRequest req ){
        HttpSession session=req.getSession();
        if(!code.equals(session.getAttribute("sessionCode"))){
            return "验证码不一样";
        }
        if(!adminService.findUser(username,email)){
            return "用户名或邮箱已经被使用";
        }
        userAdmin.setUserEmail(email);
        userAdmin.setUsername(username);
        userAdmin.setPassword(password);
        adminService.save(userAdmin);
        return "Success";
    }

    // 获取验证码
    @GetMapping("/email")
    @ResponseBody
    public String sendEmail(HttpServletRequest req,@RequestParam(value = "email",required = false) String email){
        if(email==null){
            return "非法请求嗷";
        }
        String code=null;
        code=emailTool.sendVerifyEmail(email);
        if (code==null){
            return "非法请求嗷";
        }
        HttpSession session = req.getSession();
        session.setAttribute("sessionCode", code);
        session.setMaxInactiveInterval(60*10);
        return "Success";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req){
        req.getSession().invalidate();
        return "sundry/support";

    }

    @RequestMapping("/test")
    public String test(HttpServletResponse response) {
        return "manager/login";
    }

}

