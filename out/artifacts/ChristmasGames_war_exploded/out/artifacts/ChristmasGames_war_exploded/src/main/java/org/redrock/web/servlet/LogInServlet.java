package org.redrock.web.servlet;

import net.sf.json.JSONObject;
import org.redrock.web.Utils.JsonUtil;
import org.redrock.web.clazz.User;
import org.redrock.web.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print("请走正规渠道");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDao userDao=new UserDao();
        String openid= req.getParameter("openid");
        System.out.println("<-------"+openid+"------->");
        User user=null;

        try {
            user=userDao.findUserByOpenId(openid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user!=null){
            req.getSession().setAttribute("user",user);
            resp.setHeader("refresh", "0;url=" + req.getContextPath() + "/index.jsp");
        }else {
            resp.getWriter().write("登录失败，页面一秒钟后刷新");
            resp.setHeader("refresh", "1;url=" + req.getContextPath() + "/login.jsp");
        }
    }
}
