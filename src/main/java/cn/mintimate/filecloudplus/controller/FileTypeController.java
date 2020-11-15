package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.FileHost;
import cn.mintimate.filecloudplus.service.FileHostService;
import cn.mintimate.filecloudplus.service.FileTypeService;
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
        model.addAttribute("detailType", detailType);
        List<String> magiskList = new ArrayList<>();
        magiskList.add("app");
        magiskList.add("installer");
        magiskList.add("uninstaller");
        magiskList.add("stub");
        List<String> mcList = new ArrayList<>();
        mcList.add("Official");
        mcList.add("Spigot");
        mcList.add("Forge");

        HttpSession session = req.getSession();
        session.setAttribute("dataPrePage", 8);
        session.setAttribute("currentPage", page);

        switch (type) {
            case "Magisk":
                session.setAttribute("pages", fileHostService.getPages("Magisk"));
                model.addAttribute("filetype", magiskList);
                model.addAttribute("fileList", fileHostService.FindFiles(Integer.parseInt(page), type));
                return "fh/fileByType";
            case "Minecraft":
                session.setAttribute("pages", fileHostService.getPages("Minecraft"));
                model.addAttribute("filetype", mcList);
                model.addAttribute("fileList", fileHostService.FindFiles(Integer.parseInt(page), type));
                return "fh/fileByType";
        }

        if (detailType != null) {
            session.setAttribute("pages", fileHostService.getPages(null, detailType));
            model.addAttribute("fileList", fileHostService.FindFiles(Integer.parseInt(page), type, detailType));
            if (mcList.contains(detailType)) {
                model.addAttribute("filetype", mcList);
            }
            if (magiskList.contains(detailType)) {
                model.addAttribute("filetype", magiskList);
            }
            return "fh/fileByType";
        }
        session.setAttribute("pages", fileHostService.getPages(type));
        model.addAttribute("fileList", fileHostService.FindFiles(Integer.parseInt(page), type));
        return "fh/fileByName";
    }

    @RequestMapping("/dataInfo")
    public String dataInfo(Model model, @RequestParam(value = "type", required = false) String type,
                           HttpServletRequest req, @RequestParam(value = "detailType", required = false) String detailType) {
        // 一级分类
        if (type == null) {
            type = "Others";
        }
        List<String> magiskList = new ArrayList<>();
        magiskList.add("app");
        magiskList.add("installer");
        magiskList.add("uninstaller");
        magiskList.add("stub");
        List<String> mcList = new ArrayList<>();
        mcList.add("Official");
        mcList.add("Spigot");
        mcList.add("Forge");
        double fileSizeTotal=0.00;
        int fileDownloadTotal;
        QueryWrapper wrapper = new QueryWrapper();
        if(detailType!=null){
            wrapper.eq("file_Type_Detail", detailType);
            if (mcList.contains(detailType)) {
                model.addAttribute("filetype", mcList);
            }
            if (magiskList.contains(detailType)) {
                model.addAttribute("filetype", magiskList);
            }
        }
        else {
            wrapper.eq("file_Type", type);
        }
        switch (type) {
            case "Magisk":
                model.addAttribute("filetype", magiskList);
                break;
            case "Minecraft":
                model.addAttribute("filetype", mcList);
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

