package cn.mintimate.filecloudplus.controller;


import cn.mintimate.filecloudplus.entity.FileHost;
import cn.mintimate.filecloudplus.entity.ImageHost;
import cn.mintimate.filecloudplus.entity.UserIp;
import cn.mintimate.filecloudplus.service.ImageHostService;
import cn.mintimate.filecloudplus.service.UserIpService;
import cn.mintimate.filecloudplus.util.base64Encode;
import cn.mintimate.filecloudplus.util.getUserIP;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
@RequestMapping("/imageHost")
public class ImageHostController {

    @Autowired
    private ImageHostService imageHostService;
    @Autowired
    private UserIpService userIpService;


    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("imageType");
        System.out.println(imageHostService.list());
        modelAndView.addObject("list",imageHostService.list());
        return modelAndView;
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("imageType") String imageType,
                         Model model, HttpServletRequest request ){
        ImageHost imageHost = new ImageHost();
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        imageHost.setImageName(fileName);
        imageHost.setImageType(imageType);
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件数据库存储路径并重命名，防止重复
        String filePathSql="/imageHostFile/"+UUID.randomUUID() +"-"+ fileName;
        imageHost.setPath(filePathSql);
        // 文件存储地址和名字
        fileName = System.getProperty("user.dir")+"/file" + filePathSql;
        imageHost.setUploadUser(String.valueOf(request.getSession().getAttribute("sessionUser")));
        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            imageHostService.save(imageHost);
            model.addAttribute("status","success");
            return "manager/admin";
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("status","error");
        return "manager/admin";
    }

    @GetMapping(value = "/getImage/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[]  getImage(@PathVariable(value = "id", required = false) String id, HttpServletRequest req) throws IOException {
        id= base64Encode.reverseBase64(id);
        try (InputStream is = new FileInputStream(System.getProperty("user.dir")+"/file"+imageHostService.getById(id).getPath())){
            byte[] bytes = new byte[is.available()];
            is.read(bytes, 0, is.available());
            ImageHost imageHost=imageHostService.getById(id);
            imageHost.setDownloadCount(imageHost.getDownloadCount()+1);
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("id",id);
            imageHostService.update(imageHost,wrapper);
            //记录IP
            UserIp userIp=new UserIp();
            userIp.setTargetClassify("ImageHost");
            userIp.setTargetId(Long.valueOf(id));
            userIp.setUserIp(getUserIP.getIpAddr(req));
            userIpService.save(userIp);
            is.close();
            return bytes;
        }
    }

    @GetMapping(value = "/download/{id}")
    public void download(@PathVariable(value = "id", required = false) String id, HttpServletResponse response, HttpServletRequest request) {
        id= base64Encode.reverseBase64(id);
        try {
            File file = new File(System.getProperty("user.dir")+"/file"+imageHostService.getById(id).getPath());
            // 穿件输入对象
            FileInputStream fis = new FileInputStream(file);
            // 设置相关格式
            response.setContentType("application/force-download");
            // 设置下载后的文件名以及header(UTF-8)
            response.addHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode(imageHostService.getById(id).getImageName(), "UTF-8"));
            // 创建输出对象
            OutputStream os = response.getOutputStream();
            // 常规操作
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            fis.close();
            ImageHost imageHost=imageHostService.getById(id);
            imageHost.setDownloadCount(imageHost.getDownloadCount()+1);
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("id",id);
            imageHostService.update(imageHost,wrapper);
            //记录IP
            UserIp userIp=new UserIp();
            userIp.setTargetClassify("ImageHost");
            userIp.setTargetId(Long.valueOf(Integer.valueOf(id)));
            userIp.setUserIp(getUserIP.getIpAddr(request));
            userIpService.save(userIp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/imageDetail/{id}")
    public String getDetail(@PathVariable(value = "id", required = false) String id, Model model){
        ImageHost imageHost=imageHostService.getById(base64Encode.reverseBase64(id));
        imageHost.setId(id);
        model.addAttribute("imgDetail",imageHost);
        return "ih/imageDetail";
    }

    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable(value = "id", required = false) String id){
        id= base64Encode.reverseBase64(id);
        File file = new File(System.getProperty("user.dir")+"/file"+imageHostService.getById(id).getPath());
        if(file.exists()){
            imageHostService.removeById(id);
            return "Success";
        }else{
            System.out.println("文件删除失败！");
            return "Fail";
        }
    }

    @RequestMapping("/test")
    public String test(){
        return "upload";
    }

}

