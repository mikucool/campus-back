package com.hzz.campusback.jwt;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 请求过滤器
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // 如果请求的是受保护的信息，则进行权限判定
            if(isProtectedUrl(request)) {
//                System.out.println(request.getMethod());
                // 过滤 options 请求
                if(!request.getMethod().equals("OPTIONS"))
                    // 校验 token，把用户 id 加到请求头中
                    request = JwtUtil.validateTokenAndAddUserIdToHeader(request);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        // 放行
        filterChain.doFilter(request, response);
    }

    // 受保护请求
    private boolean isProtectedUrl(HttpServletRequest request) {
        List<String> protectedPaths = new ArrayList<String>();
        protectedPaths.add("/campus/user/info");
        protectedPaths.add("/campus/user/update");
        protectedPaths.add("/campus/user/updateAvatar");
        protectedPaths.add("/post/create");
        protectedPaths.add("/post/update");
        protectedPaths.add("/post/delete/*");
        protectedPaths.add("/comment/add_comment");
        protectedPaths.add("/relationship/subscribe/*");
        protectedPaths.add("/relationship/unsubscribe/*");
        protectedPaths.add("/relationship/validate/*");
        protectedPaths.add("/message/*");

        boolean bFind = false;
        for( String passedPath : protectedPaths ) {
            bFind = pathMatcher.match(passedPath, request.getServletPath());
            if( bFind ) {
                break;
            }
        }
        return bFind;
    }

}