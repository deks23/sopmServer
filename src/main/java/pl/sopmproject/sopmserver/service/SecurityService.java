package pl.sopmproject.sopmserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.util.Constants;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class SecurityService {
    private static final Logger logger = LogManager.getLogger(SecurityService.class);
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${security.secret}")
    private String secret;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String createJWT(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts
                .builder()
                .setId(user.getId().toString())
                .setIssuedAt(now)
                .setIssuer(user.getUsername())
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    public Long getUserIdFromJWT(String jwt) throws JwtParseException {
        Claims claims;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            logger.warn(Constants.INVALID_TOKEN);
            throw new JwtParseException();
        }
        return Long.valueOf(claims.getId());
    }

    public boolean validatePassword(User user, CharSequence password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    public String getHashedPassword(CharSequence password){
        return passwordEncoder.encode(password);
    }

}
