package com.example.tablelingdingdong.config.filter;

import com.example.tablelingdingdong.config.security.JwtAuthenticationProvider;
import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.service.ManagerService;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/manager/*")
@RequiredArgsConstructor
public class ManagerFilter implements Filter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final ManagerService managerService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        if(!jwtAuthenticationProvider.validateToken(token)){
            throw new ServletException("Invalid token");
        }
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        managerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()-> new ServletException("Invalid user")
        );
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
