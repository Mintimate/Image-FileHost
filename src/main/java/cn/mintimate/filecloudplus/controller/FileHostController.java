package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.FileHost;
import cn.mintimate.filecloudplus.entity.ImageHost;
import cn.mintimate.filecloudplus.entity.UserIp;
import cn.mintimate.filecloudplus.service.FileHostService;
import cn.mintimate.filecloudplus.service.UserIpService;
import cn.mintimate.filecloudplus.util.base64Encode;
import cn.mintimate.filecloudplus.util.getUserIP;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Controller
@RequestMapping("/fileHost")
public class FileHostController {
    @Autowired
    private FileHostService fileHostService;
    @Autowired
    private UserIpService userIpService;

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "fileType",required = false) String fileType,
                         @RequestParam(value = "fileTypeDetail",required = false) String fileTypeDetail, Model model,
                         HttpServletRequest request){
        FileHost fileHost=new FileHost();
        // 判断具体分类
        if(fileType==null){
            fileType="Others";
        }
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        fileHost.setFileName(fileName);
        fileHost.setFileType(fileType);
        fileHost.setFileTypeDetail(fileTypeDetail);
        // 获取文件大小
        double fileSize=file.getSize();
        fileHost.setFileSize(fileSize);
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePathSql=null;
        if(fileTypeDetail!=null) {
            filePathSql = "/fileHostFile/" + fileType + "/" + fileTypeDetail + "/" + UUID.randomUUID() + "-" + fileName;
        }
        else {
            filePathSql = "/fileHostFile/" + fileType  + "/" + UUID.randomUUID() + "-" + fileName;
        }
        fileHost.setPath(filePathSql);
        fileHost.setUploadUser(String.valueOf(request.getSession().getAttribute("sessionUser")));
        // 文件重命名，防止重复
        fileName = System.getProperty("user.dir")+"/file" + filePathSql;
        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            fileHostService.save(fileHost);
            model.addAttribute("status","success");
            return "manager/admin";
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("status","error");
        return "manager/admin";
    }

    @GetMapping(value = "/download/{id}")
    public void download(@PathVariable(value = "id", required = false) String id, HttpServletResponse response, HttpServletRequest request) {
        id= base64Encode.reverseBase64(id);
        try {
            File file = new File(System.getProperty("user.dir")+"/file"+fileHostService.getById(id).getPath());
            // 穿件输入对象
            FileInputStream fis = new FileInputStream(file);
            // 设置相关格式
            response.setContentType("application/force-download");
            // 设置下载后的文件名以及header
            response.addHeader("Content-disposition", "attachment;fileName=" + fileHostService.getById(id).getFileName());
            // 创建输出对象
            OutputStream os = response.getOutputStream();
            // 常规操作
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            fis.close();
            os.close();
            FileHost fileHost=fileHostService.getById(id);
            fileHost.setDownloadCount(fileHost.getDownloadCount()+1);
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("id",id);
            fileHostService.update(fileHost,wrapper);
            //记录IP
            UserIp userIp=new UserIp();
            userIp.setTargetClassify(fileHost.getFileType()+"-"+fileHost.getFileTypeDetail());
            userIp.setTargetId(Long.valueOf(Integer.valueOf(id)));
            userIp.setUserIp(getUserIP.getIpAddr(request));
            userIpService.save(userIp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable(value = "id", required = false) String id){
        id=base64Encode.reverseBase64(id);
        File file = new File(System.getProperty("user.dir")+"/file"+fileHostService.getById(id).getPath());
        if(file.exists()){
            //如果文件存在，则在数据库内逻辑删除
            fileHostService.removeById(id);
            return "Success";
        }else{
            System.out.println("文件删除失败！");
            return "Fail";
        }
    }

}

