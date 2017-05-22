package com.weixin.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laowang on 2017/5/21.
 */
public class WeixinUtils {

    /**
     * 微信解析并获取XML
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> reradWeixinXml(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        //获取输入流
        InputStream inputStream = request.getInputStream();
        //使用dom4j的SAXReader读取
        SAXReader sax = new SAXReader();
        Document doc = sax.read(inputStream);
        //获取xml数据包根元素
        Element root = doc.getRootElement();
        //得到根元素的所有节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        //遍历所有节点将其放入map中
        for(Element e : elementList){
            map.put(e.getName(), e.getText());
        }
        //释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }
}
