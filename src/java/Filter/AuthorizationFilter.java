/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filter;

import domain.EUserType;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UserModel;
import domain.User;
import javax.servlet.FilterConfig;

/**
 *
 * @author Joshua
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    private FilterConfig filterConfig = null;
    private static final boolean debug = true;

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        
        if (debug) {
            log("LoginFilter:DoBeforeProcessing");
        }
        HttpServletRequest reqt = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        UserModel userRole = new UserModel();
        String path = reqt.getRequestURI().substring(reqt.getContextPath().length());
//        if (path.startsWith("pages/admin/") && !userRole.getHasUserTYpe().matches("ADMIN")) {
//            resp.sendRedirect(reqt.getContextPath() + "/index.xhtml");
//        }

    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            String path = reqt.getRequestURI().substring(reqt.getContextPath().length());

            //User usr = new User();
            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/index.xhtml") >= 0
                    || (ses != null && ses.getAttribute("username") != null)
                    || reqURI.indexOf("/private/") >= 0
                    || reqURI.contains("javax.faces.resource")) {

                chain.doFilter(request, response);

            } else {
                resp.sendRedirect(reqt.getContextPath() + "/index.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
