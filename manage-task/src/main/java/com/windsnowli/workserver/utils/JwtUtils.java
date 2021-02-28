package com.windsnowli.workserver.utils;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.windsnowli.workserver.entity.User;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author windSnowLi
 */
@Component
public class JwtUtils {
    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj
     */
    public static final String SECRET = "JKKLJOoasdlfj";
    /**
     * token 过期时间: 180天
     */
    public static final int CALENDAR_FIELD = Calendar.DATE;
    public static final int CALENDAR_INTERVAL = 180;

    public static String getToken(User user) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(CALENDAR_FIELD, CALENDAR_INTERVAL);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // header
        return JWT.create().withHeader(map)
                // payload
                .withClaim("userId", String.valueOf(user.getUserId()))
                .withClaim("userPower", String.valueOf(user.getUserPower()))
                // sign time
                .withIssuedAt(iatDate)
                // expire time
                .withExpiresAt(expiresDate)
                //signature
                .sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * 解密Token
     *
     * @param token 信息串
     * @return 揭秘结果
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 根据Token获取userId
     *
     * @param token 信息串
     * @return 用户ID
     */
    public static int getTokenUserId(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim userIdClaim = claims.get("userId");
        if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
            return -1;
        }
        return Integer.parseInt(userIdClaim.asString());
    }

    /**
     * 根据Token获取权限等级
     *
     * @param token 信息串
     * @return 用户权限
     * @throws Exception 不存在异常
     */
    public static int getTokenPower(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim userIdClaim = claims.get("userPower");
        if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
            return -1;
        }
        return Integer.parseInt(userIdClaim.asString());
    }
}
