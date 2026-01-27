package com.example.practiceapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationMs;

    // コンストラクタ: application.propertiesから設定値を読み込む
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * トークン生成（ログイン成功時に呼ぶ）
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)           // ペイロードに username を入れる
                .issuedAt(now)               // 発行日時
                .expiration(expiry)          // 有効期限
                .signWith(secretKey)         // 秘密鍵で署名
                .compact();                  // 文字列に変換
    }

    /**
     * トークンからユーザー名を取得
     */
    public String getUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    /**
     * トークンが有効か検証
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            // 署名不正、期限切れ等
            return false;
        }
    }

    /**
     * トークンをパースしてClaimsを取得（内部メソッド）
     */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)       // 秘密鍵で署名を検証
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
