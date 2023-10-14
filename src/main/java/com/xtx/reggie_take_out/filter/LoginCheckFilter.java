package com.xtx.reggie_take_out.filter;

import com.alibaba.fastjson.JSON;
import com.xtx.reggie_take_out.common.BaseContext;
import com.xtx.reggie_take_out.common.R;
import com.xtx.reggie_take_out.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经登录
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        // 1. 获取本次请求的URL
        String requestUri = request.getRequestURI();

        log.info("拦截到请求: {}", requestUri);
        // 定义不需要处理的请求路径
        String[] urls = new String[]{"/employee/login", "/employee/logout", "/backend/**", "/front/**"};
        // 2. 如果不需要处理，则直接放行
        if (check(urls, requestUri)) {
            log.info("不需要处理:{}", requestUri);
            // 如果需要处理，则放行
            filterChain.doFilter(request, response);
            return;
        }
        // 3. 判断用户是否已经登录，如果已经登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            log.info("已经登录:{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        log.info("未登录");
        // 4. 如果没有登录，则跳转到登录页面
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    /**
     * 检查是否需要处理
     *
     * @param urls
     * @param requestURI
     * @return
     **/
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestURI)) {
                return true;
            }
        }
        return false;
    }
}


