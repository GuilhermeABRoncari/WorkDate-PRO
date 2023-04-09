package github.guilhermeabroncari.workdatepro.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;

@Service
public class TokenService {
    @Value("{api.token.security.secret}")
    private String secret;

    public String generateToken(WorkDateUser workDateUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("WorkDate-PRO")
                    .withSubject(workDateUser.getLogin())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating JWT Token.", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("WorkDate-PRO")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Expired or invalid token.");
        }
    }

    private Instant expirationDate() {
        return OffsetDateTime.now().plusHours(12).toInstant();
    }
}
