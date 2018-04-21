package org.redrock.web.servlet;

import net.sf.json.JSONObject;
import org.redrock.web.Utils.JsonUtil;
import org.redrock.web.Utils.StreamUtil;
import org.redrock.web.clazz.User;
import org.redrock.web.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
@WebServlet("/postPoint")
public class PostPointServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("error");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user= (User) req.getSession().getAttribute("user");
        String score= (String) req.getParameter("score");
        User newUser =null;

        UserDao userDao=new UserDao();
        try {
            userDao.postPoint(user,score);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            newUser=userDao.findUserBy("id",String.valueOf(user.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("user",newUser);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject=JsonUtil.getUserJson(newUser,"nickname","rank","count","imgurl");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject.toString());
        StreamUtil.writeStream(resp.getOutputStream(),jsonObject.toString());


    }
}
