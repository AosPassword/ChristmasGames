package org.redrock.web.filter;


import org.redrock.web.Utils.FormatUtil;
import org.redrock.web.clazz.User;
import org.redrock.web.dao.UserDao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebFilter(value = "/views/start.jsp")
public class Indexfilter implements Filter {
    private boolean isDebug=true;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset-UTF-8");

        User user = (User) request.getSession().getAttribute("user");
        if (isDebug) {
            if (user == null) {
                response.getWriter().write("您还未曾登陆，页面一秒钟后刷新");
                response.setHeader("refresh", "1;url=" + request.getContextPath() + "/login.jsp");
            } else {
                Date now=new Date();
                String str_now=FormatUtil.getDate(now);
                String str_last=FormatUtil.getDate(user.getLast_login_time());
                if (!str_now.equals(str_last)){
                    UserDao userDao=new UserDao();
                    try {
                        userDao.reFresh(user);
                        User newUser=userDao.findUserBy("id",String.valueOf(user.getId()));
                        request.getSession().setAttribute("user",newUser);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                filterChain.doFilter(request, response);
            }
        }
    }

    public void destroy() {

    }
}
