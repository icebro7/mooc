package com.cy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @BelongsPackage: org.wql.util
 * @Author: 阿隆
 * @CreateTime: 2024-10-19 00:03
 * @Description: JWT工具类
 * @Version: 1.0
 */
public class JwtUtils {
    // 定义用于签名 JWT 的密钥
    private static String signKey = "test123test123test123test123testdfdsfdsfds1234567890";

    // 定义 JWT 的过期时间为 24 小时（单位：毫秒）
     private static int expire = 60 * 60 * 24 * 1000;
    // 如果需要测试较短时效, 可以使用 10 秒
   // private static int expire = 1000 * 10;

    /**
     * @Description: 生成JWT令牌
     * @Param: [claims] JWT 第二部分负载 payload 中存储的内容
     * @return: java.lang.String
     */
    public static String generateJwt(Map<String, Object> claims) {
        // 创建一个 JWT builder，添加 payload，签名算法及密钥，设置过期时间，并生成 token 字符串
        String jwt = Jwts.builder()
                .addClaims(claims)  // 添加声明，即 payload 数据
                .signWith(SignatureAlgorithm.HS256, signKey)  // 使用 HS256 签名算法和密钥进行签名
                .setExpiration(new Date(System.currentTimeMillis() + expire))  // 设定过期时间
                .compact();  // 构建并返回最终的 JWT 字符串
        return jwt;  // 返回生成的 JWT 字符串
    }

    /**
     * @Description: 解析JWT令牌
     * @Param: [jwt] JWT 令牌
     * @return: io.jsonwebtoken.Claims JWT 第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt) {
        // 使用密钥解析 JWT，获取其声明部分（payload）
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)  // 设置用于解析的签名密钥
                .parseClaimsJws(jwt)  // 解析 JWT 字符串
                .getBody();  // 获取 payload 中的 Claims
        return claims;  // 返回解析得到的 Claims 对象
    }

    // 获取 JWT 的过期时间
    public static int getExpire() {
        return expire;
    }

    // 私有化构造方法，防止实例化
    private JwtUtils() {
    }
}
