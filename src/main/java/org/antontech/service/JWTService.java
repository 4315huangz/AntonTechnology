package org.antontech.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.antontech.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.Signature;
import java.util.Date;

@Service
public class JWTService {
    private final String SECRET_KEY = "ziwei-ascending";
    private final String ISSUER = "com.antontechnology";
    private final long EXPIRATION_TIME = 86400 * 1000;

    public String generateToken(Account account) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(account.getId()));
        claims.setSubject(account.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm,signingKey);
        String generatedToken = builder.compact();
        return generatedToken;
    }
}