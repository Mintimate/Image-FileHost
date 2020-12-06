package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.ImageHost;
import cn.mintimate.filecloudplus.entity.ImageType;
import cn.mintimate.filecloudplus.service.ImageHostService;
import cn.mintimate.filecloudplus.service.ImageTypeService;
import cn.mintimate.filecloudplus.util.base64Encode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/imageType")
public class ImageTypeController {
    @Autowired
    private ImageTypeService imageTypeService;
    @Autowired
    private ImageHostService imageHostService;
//    ModelAndView modelAndView = new ModelAndView();

    //    @RequestMapping
//    public ModelAndView index() {
//        modelAndView.setViewName("ih/imageType");
//        modelAndView.addObject("imageType", imageTypeService.list());
//        modelAndView.addObject("imageHost", imageHostService.list());
//        return modelAndView;
//    }
    @GetMapping
    public String imageType(Model model, @RequestParam(required = false, value = "type") String type, @RequestParam(required = false, value = "page") String page, HttpServletRequest req) {
        List<ImageHost> list;
        //分页功能
        //没有指定页数，默认为第一页
        if (page == null) {
            page = "1";
        }
        HttpSession session = req.getSession();
        session.setAttribute("dataPrePage", 8);
        session.setAttribute("currentPage", page);
        if (type == null || type == "") {
            session.setAttribute("pages", imageHostService.getPages());
            list = imageHostService.FindImages(Integer.parseInt(page));
        } else {
            session.setAttribute("pages", imageHostService.getPages(type));
            list = imageHostService.FindImages(Integer.parseInt(page), type);
        }
        list.forEach(item -> {
            String temp= String.valueOf(item.getId());
            temp=base64Encode.convertToBase64(temp);
            item.setId(temp);
        });
        model.addAttribute("type", type);
        model.addAttribute("imageHost", list);
        model.addAttribute("imageType", imageTypeService.list());
        return "ih/imageType";
    }

    @RequestMapping("/addType")
    public String addType(Model model, @RequestParam(value = "newType", required = false) String newType) {
        ImageType image = new ImageType();
        image.setImageType(newType);
        imageTypeService.save(image);
        model.addAttribute("imageTypes", imageTypeService.list());
        return "manager/admin";
    }

    @RequestMapping("/RemoveType")
    public String removeType(Model model, @RequestParam(value = "newType", required = false) String newType) {
        ImageType image = new ImageType();
        image.setImageType(newType);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("image_Type", newType);
        // 在imageType实体表内删除该分类（非逻辑删除）
        imageTypeService.remove(wrapper);
        // 在imageHost实体表内删除该分类下图片（逻辑删除）
        imageHostService.remove(wrapper);
        model.addAttribute("imageTypes", imageTypeService.list());
        return "manager/admin";
    }


//    @GetMapping("/typeByPaper")
//    public String imageTypePaper(Model model, @RequestParam(required = false, value = "type") String type, @RequestParam(required = false, value = "page") String page, HttpServletRequest req) {
//        List list;
//        //分页功能
//        //没有指定页数，默认为第一页
//        if (page == null) {
//            page = "1";
//        }
//        HttpSession session = req.getSession();
//        session.setAttribute("dataPrePage", 8);
//        session.setAttribute("currentPage", page);
//        session.setAttribute("pages", imageHostService.getPages(type));
//        System.out.println(type);
//        list = imageHostService.FindImages(Integer.parseInt(page), type);
//        System.out.println(list.toString());
//        model.addAttribute("imageHost", list);
//        model.addAttribute("imageType", imageTypeService.list());
//        return "ih/imageType";
//    }
}



