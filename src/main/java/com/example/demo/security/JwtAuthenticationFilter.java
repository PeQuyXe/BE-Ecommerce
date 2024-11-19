package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Kiểm tra và lấy token từ Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Lấy JWT token từ header
            username = jwtTokenUtil.extractEmail(jwt); // Extract username từ JWT
        }

        // Nếu có username và chưa có authentication trong SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Kiểm tra token hợp lệ
            if (jwtTokenUtil.validateToken(jwt)) {
                Integer roleId = jwtTokenUtil.extractRoleId(jwt);  // Lấy roleId từ token
                String role = getRoleName(roleId);  // Dựa trên roleId để xác định role

                // Tạo đối tượng User với quyền hạn tương ứng
                User user = new User(username, "", Collections.singletonList(() -> role));

                // Tạo authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                // Cung cấp chi tiết request vào authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Cập nhật SecurityContext với authentication token mới
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Tiến hành chuỗi filter tiếp theo
        filterChain.doFilter(request, response);
    }

    // Phương thức giúp lấy tên quyền theo roleId
    private String getRoleName(Integer roleId) {
        if (roleId == 1) {
            return "ROLE_ADMIN";
        } else if (roleId == 2) {
            return "ROLE_MODERATOR"; // Ví dụ cho role Moderator
        } else {
            return "ROLE_USER"; // Role mặc định
        }
    }
}
