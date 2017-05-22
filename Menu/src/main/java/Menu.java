import button.ClickButton;
import button.ViewButton;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.HttpUtils;


/**
 * Created by laowang on 2017/5/22.
 */
public class Menu {
    public static void main(String []args){
        ClickButton cbt=new ClickButton();
        cbt.setKey("image");
        cbt.setName("回复图片");
        cbt.setType("click");


//        ViewButton vbt=new ViewButton();
//        vbt.setUrl("http://www.cuiyongzhi.com");
//        vbt.setName("博客");
//        vbt.setType("view");
        ClickButton clickButton = new ClickButton();
        clickButton.setKey("personal");
        clickButton.setName("个人中心");
        clickButton.setType("click");

//        JSONArray sub_button=new JSONArray();
//        sub_button.add(cbt);
//        sub_button.add(vbt);


//        JSONObject buttonOne=new JSONObject();
//        buttonOne.put("name", "菜单");
//        buttonOne.put("sub_button", sub_button);

        JSONArray button=new JSONArray();
        button.add(clickButton);
//        button.add(buttonOne);
        button.add(cbt);

        JSONObject menujson=new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson);

        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+"q7DOD6oe9l-r92L6M8NSYT_F0pfTPCEiezZc9-058s8VMzVZ3AgA8cePKwdWeW3jqCiwIJoc459F47wMtTjXEcWqwxPI8fP07fEUWuUUh0F8GzSh7ftNVEVpBayzBFWpNVTiAGADLJ";

        try{
            String rs= HttpUtils.doPost(url, menujson.toJSONString());
            System.out.println(rs);
        }catch(Exception e){
            System.out.println("请求错误！");
        }
    }
}
