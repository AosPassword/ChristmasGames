package org.redrock.web.filter;


import org.redrock.web.clazz.User;
import org.redrock.web.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Indexfilter implements Filter {
    private boolean isDebug=true;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        if (isDebug) {
            if (user == null) {
                response.getWriter().write("您还未曾登陆，页面一秒钟后刷新");
                response.setHeader("refresh", "1;url=" + request.getContextPath() + "/login.jsp");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    public void destroy() {

    }
}
