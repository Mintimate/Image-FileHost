package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.UserAdmin;
import cn.mintimate.filecloudplus.service.ImageTypeService;
import cn.mintimate.filecloudplus.service.UserAdminService;
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

//    访问管理员管理页面
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("imageTypes", imageTypeService.list());
        return "manager/admin";
    }

    @PostMapping("/enter")
    public String enter(HttpServletRequest req, @RequestParam(value = "username",required = false) String username, @RequestParam(value = "password",required = false) String password) {
        QueryWrapper wrapper = new QueryWrapper();
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        wrapper.allEq(map);
        if (adminService.list(wrapper).size() != 0) {
            HttpSession session = req.getSession();
            session.setAttribute("sessionUser", username);
            return "index";
        }
        return "manager/login";

    }
    @GetMapping("/login")
    public String login(){
        return "manager/login";
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

