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
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
@WebServlet("/rank")
public class RankServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user= (User) req.getSession().getAttribute("user");
        UserDao userDao=new UserDao();
        JSONObject jsonObject=null;

        try {
            jsonObject=JsonUtil.getRankJson(user,50,"nickname","imgurl","rank");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        StreamUtil.writeStream(resp.getOutputStream(),jsonObject.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
