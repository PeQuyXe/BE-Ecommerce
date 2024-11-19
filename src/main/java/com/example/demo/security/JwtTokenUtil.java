package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Khóa bí mật (lưu trong môi trường an toàn hơn cho sản xuất)
    private final long JWT_EXPIRATION_MS = 86400000; // Thời gian hết hạn mặc định là 1 ngày

    /**
     * Tạo token với thông tin email, roleId và userId
     */
    public String generateToken(String email, Integer roleId, Integer userId, long expirationTime) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .claim("roleId", roleId) // Thêm thông tin roleId vào token
                .claim("userId", userId) // Thêm thông tin userId vào token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY) // Sử dụng SECRET_KEY để ký token
                .compact();
    }

    /**
     * Tạo accessToken (thời gian sống 5 phút)
     */
    public String generateAccessToken(String email, Integer roleId, Integer userId) {
        return generateToken(email, roleId, userId, 5 * 60 * 1000); // Thời gian sống: 5 phút
    }

    /**
     * Tạo refreshToken (thời gian sống 1 ngày)
     */
    public String generateRefreshToken(String email, Integer roleId, Integer userId) {
        return generateToken(email, roleId, userId, 24 * 60 * 60 * 1000); // Thời gian sống: 1 ngày
    }

    /**
     * Trích xuất email từ token
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Trích xuất roleId từ token
     */
//    public Integer extractRoleId(String token) {
//        return extractClaim(token, claims -> claims.get("roleId", Integer.class));
//    }
    public Integer extractRoleId(String token) {
        // Trích xuất roleId từ token (có thể từ claims)
        return Integer.parseInt(Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("roleId", String.class));  // giả sử bạn lưu roleId trong claims với tên "roleId"
    }

    /**
     * Trích xuất userId từ token
     */
    public Integer extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Integer.class));
    }

    /**
     * Trích xuất một claim cụ thể từ token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    /**
     * Kiểm tra tính hợp lệ của token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Token đã hết hạn: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token không được hỗ trợ: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token không hợp lệ: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("Chữ ký token không hợp lệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Token trống hoặc không hợp lệ: " + e.getMessage());
        }
        return false;
    }

    /**
     * Kiểm tra xem token có hết hạn hay không
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Trích xuất expiration từ token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
