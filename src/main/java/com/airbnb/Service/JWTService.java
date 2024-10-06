package com.airbnb.Service;

import com.airbnb.Entity.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expirytime}")
    private int expirytime;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expirytime);

          algorithm = algorithm.HMAC256(algorithmKey);



    }
    public String generateToken(AppUser appUser){
        return JWT.create()
                .withClaim("UserName",appUser.getName())
                .withExpiresAt(new Date(System.currentTimeMillis()+expirytime))
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
