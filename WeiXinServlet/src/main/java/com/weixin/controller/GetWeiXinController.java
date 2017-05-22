package com.weixin.controller;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.weixin.util.WeixinUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by laowang on 2017/5/21.
 */
@Controller
@RequestMapping(value = "/weixin")
public class GetWeiXinController {
    private static Logger logger = Logger.getLogger(GetWeiXinController.class);

    @RequestMapping(value = "/test")
    public void connect(HttpServletRequest request, HttpServletResponse response){
        logger.info("接入成功");
        String token = "weixinSource";

        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        String echostr = request.getParameter("echostr");
        logger.info("参数 token:"+token+",timestamp:"+timeStamp+",nonce:"+nonce+",signature:"+signature+",echostr:"+echostr);
        System.out.println("参数 token:"+token+",timestamp:"+timeStamp+",nonce:"+nonce+",signature:"+signature+",echostr:"+echostr);
        //对参数进行字典排序
        String []para = {token,timeStamp,nonce};
        Arrays.sort(para);
        //排序之后转换成字符串
        StringBuilder stringBuilder = new StringBuilder("");
        for (String aPara : para) {
            stringBuilder.append(aPara);
        }
        logger.info("排序结果: " +stringBuilder.toString());

        //sha1加密工具
        MessageDigest messageDigest = null;
        try {
            //获取加密工具对象
            messageDigest = MessageDigest.getInstance("SHA-1");
            //加密并转换成十六进制字符串
            String mySignature = byteToStr(messageDigest.digest(stringBuilder.toString().getBytes()));
            if(mySignature.equals(signature.toUpperCase())){
                logger.info("=============验证成功=============");
                //原样返回微信服务器发送的参数，同样不需要加密
                PrintWriter out = response.getWriter();
                out.print(echostr);
                logger.info("============写出返回值成功==============");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("获取加密对象失败: "+e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("输出流失败: "+e);
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/request")
    public void replayTextMessage(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> map = null;
        //从工具类获取解析后的xml'
        try{
            map = WeixinUtils.reradWeixinXml(request);
        } catch (DocumentException e) {
            logger.error("读取XML失败： "+e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("获取输入流失败: "+e);
            e.printStackTrace();
        }
        //获取发送方账号
        String fromUserName = map.get("FromUserName");
        //接收方账号（开发者微信号）
        String toUserName = map.get("ToUserName");
        //消息类型
        String msgType = map.get("MsgType");
        //文本内容
        String content = map.get("Content");
        logger.info("发送方账号:"+fromUserName+",接收方账号(开发者微信号):"+toUserName+",消息类型:"+msgType+",文本内容:"+content);
        //回复消息
        if(msgType.equals("text")){
            //根据开发文档要求构造XML字符串，本文为了让流程更加清晰，直接拼接
            //这里在开发的时候可以优化，将回复的文本内容构造成一个java类
            //然后使用XStream(com.thoughtworks.xstream.XStream)将java类转换成XML字符串，后面将会使用这个方法。
            //而且由于参数中没有任何特殊字符，为简单起见，没有添加<![CDATA[xxxxx]]>
            String replyMsg = "<xml>"+
                    "<ToUserName>"+fromUserName+"</ToUserName>"+
                    "<FromUserName>"+toUserName+"</FromUserName>"+
                    "<CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime>"+
                    "<MsgType>"+msgType+"</MsgType>"+
                    "<Content>"+content+"</Content>"+
                    "</xml>";
            //响应消息
            logger.info("响应消息："+replyMsg);
            PrintWriter out = null;
            try {
                //设置回复内容编码方式为UTF-8，防止乱码
                response.setCharacterEncoding("UTF-8");
                out = response.getWriter();
                //我们这里将用户发送的消息原样返回
                out.print(replyMsg);
                logger.info("==============响应成功==================");
            } catch (IOException e) {
                logger.error("获取输出流失败",e);
            }finally {
                if(out != null){
                    out.close();
                    out = null;
                }
            }
        }
    }
    /**
     * 将自己饿转换成十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;

    }


    /**
     * 将字节数组转换成十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {

        StringBuilder strDigest = new StringBuilder();
        for (byte aByteArray : byteArray) {
            strDigest.append(byteToHexStr(aByteArray));
        }
        return strDigest.toString();

    }
}
