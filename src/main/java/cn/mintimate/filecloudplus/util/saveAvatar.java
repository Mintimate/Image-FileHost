package cn.mintimate.filecloudplus.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class saveAvatar {
    public static void main(String[] args) throws Exception {
        System.out.println(saveAvatar("198330181@qq.com","Small"));
    }
    public static String saveAvatar(String email, String name){
        //判断是否为QQ邮箱
        String type=email.substring(email.indexOf("@"));
        String url=null;
        if(type.equals("@qq.com")){
            url=analyzingQQAvatar(email);
        }
        else {
            url=analyzingGravatarAvatar(email);
        }
        try {
            url=downloadAvatar(url,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
    /**
     * 下载文件到本地
     *
     * @param urlString
     *          被下载的文件地址
     *          本地文件名
     * @throws Exception
     *           各种异常
     */
    public static String downloadAvatar(String urlString, String name) throws Exception {
        String filename=name+".jpg";
        String filePath="/userAvatar/"+filename;
        File avatar=new File(System.getProperty("user.dir")+filePath);
        if(!avatar.getParentFile().exists()){
            avatar.getParentFile().mkdirs();
        }
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(avatar);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
        return filePath;
    }

    public static String analyzingQQAvatar(String email){
        String Path;
        String QQNumber=email.substring(0, email.indexOf("@"));
        String url = "https://ptlogin2.qq.com/getface?imgtype=3&uin="+QQNumber+"@qq.com";
        StringBuilder sb = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            //设置编码格式 解决中文乱码问题
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonStr= sb.toString();
        jsonStr = jsonStr.substring(jsonStr.indexOf("(") + 1, jsonStr.lastIndexOf(")"));
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Path=jsonObject.getString(QQNumber);
        if (Path==null){
            Path=jsonObject.getString("10000");
        }
        return Path;
    }
    public static String analyzingGravatarAvatar(String email){
        email= DigestUtils.md5DigestAsHex(email.getBytes());
        String url="https://gravatar.loli.net/avatar/"+email;
        return url;
    }
}