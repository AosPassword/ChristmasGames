package org.redrock.web.Utils;


import net.sf.json.JSONObject;
import org.redrock.web.clazz.User;
import org.redrock.web.dao.UserDao;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理json格式
 */
public class JsonUtil {
    private static String[] params;

    public static JSONObject getRankJson(User my,int num,String... params) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        JsonUtil.params=params;
        ArrayList<String> strings = new ArrayList<String>();
        for (String s : params) { strings.add(s); }

        JSONObject jsonObject=new JSONObject();
        JSONObject rankDataJson=new JSONObject();
        UserDao userDao=new UserDao();
        int index=1;

        List<User> users=userDao.getRank(num);
        if (users.size()>0) {
            for (User user : users) {
                if (user!=null) {
                    JSONObject dataJson = getUserdata(user, strings);
                    rankDataJson.put(index++, dataJson);
                }else {
                    System.out.println("未得到user");
                }
            }
            JSONObject myDataJson=getUserdata(my,strings);
            rankDataJson.put("my",myDataJson);

        }else {
            System.out.println("你这dao层写的不行啊哥，你再看看，再看看");
        }

        jsonObject=getJsonHead(jsonObject,rankDataJson);

        return jsonObject;

    }

    public static JSONObject getJsonHead(JSONObject jsonObject,JSONObject dataJson){
        if (dataJson.size()>0) {
            jsonObject.put("status", "200");
            jsonObject.put("msg", "success");
            jsonObject.put("data", dataJson);
            return jsonObject;
        }else {
            jsonObject.put("status", "400");
            jsonObject.put("msg", "false");
        }
        return jsonObject;
    }

    public static JSONObject getUserJson(Object object, String... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataJson=null;
        JsonUtil.params = params;
        ArrayList<String> strings = new ArrayList<String>();

        for (String s : params) {
            strings.add(s);
        }
        if (strings.size() > 0) {
            dataJson = getUserdata(object, strings);
            jsonObject=getJsonHead(jsonObject,dataJson);
            return jsonObject;
        } else {
            jsonObject.put("status", "400");
            jsonObject.put("msg", "false");
        }
        return jsonObject;
    }

    public static JSONObject getUserdata(Object o, ArrayList<String> strings) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        for (String s : strings) {
            String field_type = null;
            boolean flag = false;
            //保证strings中传入的属性在对象中存在
            if (fields.length > 0) {
                for (Field field : fields) {
                    if (field.getName().equals(s)) {
                        flag = true;
                        field_type = field.getType().getSimpleName();
                    }
                }
            } else {
                System.out.println("未获得对象属性");
            }
            if (flag) {
                String firstLetter = s.substring(0, 1).toUpperCase();
                String methodName = "get" + firstLetter + s.substring(1);
                Method method = o.getClass().getMethod(methodName);
                Object object = method.invoke(o);
                String value = null;
                if (field_type.equalsIgnoreCase("String")) {
                    value = (String) object;
                } else {
                    value = String.valueOf(object);
                }
                jsonObject.put(s, value);
            } else {
                System.out.println("未在对象类中找到所求属性！");
            }
        }
        return jsonObject;
    }
}
