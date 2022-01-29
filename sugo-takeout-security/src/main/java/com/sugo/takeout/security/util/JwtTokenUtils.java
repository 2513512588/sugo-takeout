package com.sugo.takeout.security.util;



import com.sugo.takeout.security.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author hehaoyang
 * jwt 工具类
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecret";
    private static final String ISS = "echisan";

    // 角色的key
    private static final String ROLE_CLAIMS = "rol";
    private static final String USER_ID_CLAIMS = Role.ROLE_USER.getName();

    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 1000 * 60 * 60;

    /**
     * 选择了记住我之后的过期时间为7天
     */
    private static final long EXPIRATION_REMEMBER = 1000 * 60 * 60 * 24 * 7;

    /**
     * 创建token
     * @param username 用户名
     * @param role 用户角色
     * @param userId 用户id
     * @param isRememberMe 是否记住我
     * @return token字符串
     */
    public static String createToken(String username, String role, Integer userId, boolean isRememberMe){
        return createToken(username, role, userId, isRememberMe, new HashMap<>());
    }

    public static String createToken(String username, String role, Integer userId, boolean isRememberMe, Map<String, Object> map) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        map.put(ROLE_CLAIMS, role);
        map.put(USER_ID_CLAIMS, userId);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 从token中获取用户名
     * @param token token字符串
     * @return 用户名
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户角色
     * @param token token字符串
     * @return 用户角色信息
     */
    public static String getUserRole(String token){
        return getTokenBody(token).get(ROLE_CLAIMS).toString();
    }


    /**
     * 获取用户Id
     * @param token token字符串
     * @return 用户id
     */
    public static Integer getUserId(String token){
        return Integer.parseInt(getTokenBody(token).get(USER_ID_CLAIMS).toString());
    }

    /**
     * 获取附加数据
     * @param token token字符串
     * @param key 数据的key
     * @return 附加数据
     */
    public static Object getAttachData(String token, String key){
        return getTokenBody(token).get(key);
    }

    /**
     * 是否已过期
     * @param token token字符串
     * @return 是否过期？
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }


    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(JwtTokenUtils.TOKEN_PREFIX, ""))
                .getBody();
    }
}
