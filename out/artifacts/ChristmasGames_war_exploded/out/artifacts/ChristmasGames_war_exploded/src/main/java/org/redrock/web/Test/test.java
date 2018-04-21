package org.redrock.web.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.web.Utils.JsonUtil;
import org.redrock.web.clazz.User;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        User user=new User();
        user.setNickname("Password");
        user.setOpenid("qwerqwer");
        ArrayList<String> strings=new ArrayList<String>();
        strings.add("nickname");
        strings.add("openid");
        JSONArray jsonObject=JsonUtil.getData(user,strings);
        System.out.println(jsonObject);
    }
}
