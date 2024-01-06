package com.example.tablelingdingdong.config.security;

import com.example.tablelingdingdong.config.util.Aes256Util;
import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.type.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
public class JwtAuthenticationProvider {
    @Value("{secret.key}")
    private  String secretKey;
    private static final long tokenValidTime = 1000L * 60 * 60 *24;
    public  String createToken(String userPk, Long id, UserType userType){
        Claims claims = Jwts.claims()
                .setSubject(Aes256Util.encrypt(userPk))
                .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles",userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidTime))
                .signWith(SignatureAlgorithm.HS256,this.secretKey)
                .compact();
    }
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claimsJwts =
                    Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJwts.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
    public UserVo getUserVo(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        System.out.println(Aes256Util.decrypt(claims.getId()));
        return new UserVo(
                Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
                Aes256Util.decrypt(claims.getSubject()));
    }
}
