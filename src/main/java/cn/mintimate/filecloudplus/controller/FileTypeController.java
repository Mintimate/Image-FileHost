package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.FileHost;
import cn.mintimate.filecloudplus.service.FileHostService;
import cn.mintimate.filecloudplus.service.FileTypeService;
import cn.mintimate.filecloudplus.util.base64Encode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Controller
@RequestMapping("/fileType")
public class FileTypeController {
    @Autowired
    private FileTypeService fileTypeService;
    @Autowired
    private FileHostService fileHostService;
    private List <FileHost> list=null;

    @RequestMapping
    public String select(Model model, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "page", required = false) String page,
                         HttpServletRequest req, @RequestParam(value = "detailType", required = false) String detailType) {
        if (page == null) {
            page = "1";
        }
        // 一级分类
        if (type == null) {
            type = "Others";
        }
        model.addAttribute("type", type);
        // 二级分类查询
        List <String> detailTypeList=null;
        detailTypeList=fileTypeService.getDetailByType(type);
        model.addAttribute("filetype",detailTypeList);
        // 二级分类具体内容通过session给前端交互
        model.addAttribute("detailType", detailType);

        HttpSession session = req.getSession();
        session.setAttribute("dataPrePage", 8);
        session.setAttribute("currentPage", page);

        // 判断分类（是否为Magisk、Minecraft分类）
        if(fileTypeService.getType().contains(type)){
            session.setAttribute("pages", fileHostService.getPages(type));
            list=fileHostService.FindFiles(Integer.parseInt(page), type);
            list.forEach(item -> {
                String temp= String.valueOf(item.getId());
                temp= base64Encode.convertToBase64(temp);
                item.setId(temp);
            });
            model.addAttribute("fileList", list);
            return "fh/fileByType";
        }

        if (detailType != null) {
            session.setAttribute("pages", fileHostService.getPages(null, detailType));
            list= fileHostService.FindFiles(Integer.parseInt(page), type, detailType);
            list.forEach(item -> {
                String temp= String.valueOf(item.getId());
                temp= base64Encode.convertToBase64(temp);
                item.setId(temp);
            });
            model.addAttribute("fileList",list);
            return "fh/fileByType";
        }
        session.setAttribute("pages", fileHostService.getPages(type));
        list=fileHostService.FindFiles(Integer.parseInt(page), type);
        list.forEach(item -> {
            String temp= String.valueOf(item.getId());
            temp= base64Encode.convertToBase64(temp);
            item.setId(temp);
        });
        model.addAttribute("fileList", list);
        return "fh/fileByName";
    }

    @RequestMapping("/dataInfo")
    public String dataInfo(Model model, @RequestParam(value = "type", required = false) String type,
                           HttpServletRequest req, @RequestParam(value = "detailType", required = false) String detailType) {
        // 一级分类
        if (type == null) {
            type = "Others";
        }
        // 二级分类查询
        List <String> detailTypeList=null;
        detailTypeList=fileTypeService.getDetailByType(type);
        // 文件总大小
        double fileSizeTotal=0.00;
        // 文件下载次数统计
        int fileDownloadTotal;
        QueryWrapper wrapper = new QueryWrapper();
        if(detailType!=null){
            wrapper.eq("file_Type_Detail", detailType);
            if (detailTypeList.contains(detailType)) {
                model.addAttribute("filetype", detailTypeList);
            }
            if (detailTypeList.contains(detailType)) {
                model.addAttribute("filetype", detailTypeList);
            }
        }
        else {
            wrapper.eq("file_Type", type);
        }
        switch (type) {
            case "Magisk":
                model.addAttribute("filetype", detailTypeList);
                break;
            case "Minecraft":
                model.addAttribute("filetype", detailTypeList);
                break;
        }
        List <FileHost> fileList=fileHostService.list(wrapper);
        fileDownloadTotal = fileList.stream().collect(Collectors.summingInt(FileHost::getDownloadCount));
        for (FileHost temp : fileList) {
            fileSizeTotal=fileSizeTotal+temp.getFileSize()*temp.getDownloadCount();
        }
        model.addAttribute("type",type);
        model.addAttribute("fileList", fileHostService.list(wrapper));
        model.addAttribute("fileSizeTotal", fileSizeTotal);
        model.addAttribute("detailType", detailType);
        model.addAttribute("fileDownloadTotal", fileDownloadTotal);
        return "fh/dataInfo";
    }

    @RequestMapping("/homebrew")
    public String Homebrew(Model model){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("file_Type","Homebrew");
        List<FileHost> list=fileHostService.list(wrapper);
        int fileDownloadTotal=0;
        fileDownloadTotal = list.stream().collect(Collectors.summingInt(FileHost::getDownloadCount));
        model.addAttribute("fileList",fileHostService.list(wrapper));
        model.addAttribute("fileDownloadTotal", fileDownloadTotal);
        return "sundry/homebrew";
    }

}

