package vision.cotegory.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    @Value("${jwt.secret}")
    private String key;

    public String generateToken(Map<String, Object> valueMap, int days) {

        log.info("generateKey..." + key);

        //헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>(valueMap);

        int time = 60 * 24 * days;

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(40).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return jwtStr;
    }


    public Map<String, Object> validateToken(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getMemberId(String token) {
        String subString = token.substring(7);
        return Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(subString)
                .getBody()
                .get("memberId", Long.class);
    }
}