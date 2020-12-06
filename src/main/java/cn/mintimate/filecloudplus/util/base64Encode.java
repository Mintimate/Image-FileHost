package cn.mintimate.filecloudplus.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class base64Encode {
    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();
    // 编码
    public static String convertToBase64(String text){
        byte[] textByte = new byte[0];
        try {
            textByte = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedText = encoder.encodeToString(textByte);
        return encodedText;
    }
    public static String reverseBase64(String encodedText){
        String Text = null;
        try {
            Text = new String(decoder.decode(encodedText), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Text;
    }
}
