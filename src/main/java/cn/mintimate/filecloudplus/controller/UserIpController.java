package cn.mintimate.filecloudplus.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Controller
@RequestMapping("/support")
public class UserIpController {
    @RequestMapping
    public String support(){
        return "sundry/support";
    }

    @RequestMapping("/test")
    public String tips(){
        return "sundry/tips";
    }
}

