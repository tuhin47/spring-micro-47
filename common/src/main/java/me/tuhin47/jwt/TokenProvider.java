package me.tuhin47.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.AppProperties;
import me.tuhin47.config.exception.JwtTokenMalformedException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTHENTICATED = "authenticated";
    public static final long TEMP_TOKEN_VALIDITY_IN_MILLIS = 300000;

    private final AppProperties appProperties;

    public String createToken(boolean authenticated, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (authenticated ? appProperties.getAuth().getTokenExpirationMsec() : TEMP_TOKEN_VALIDITY_IN_MILLIS));

        return Jwts.builder().subject(email).claim(AUTHENTICATED, authenticated).issuedAt(now).expiration(expiryDate)
                   .signWith(signingKey(), Jwts.SIG.HS512).compact();
    }

    public String getUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(final String token) {
        return Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();
    }

    public Boolean isAuthenticated(String token) {
        return getClaims(token).get(AUTHENTICATED, Boolean.class);
    }

    public boolean validateToken(String authToken) {
        try {
            getClaims(authToken);
            return true;
        } catch (Exception ex) {
            throw new JwtTokenMalformedException("Token Invalid.");
        }
        /*catch (SignatureException e) {
            log.error("TokenProvider | validateJwtToken | Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("TokenProvider | validateJwtToken | Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("TokenProvider | validateJwtToken | JWT token is expired: {}", ex.getMessage());
            throw new JwtTokenMissingException("Token Expired");
        } catch (UnsupportedJwtException e) {
            log.error("TokenProvider | validateJwtToken | JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("TokenProvider | validateJwtToken | JWT claims string is empty: {}", e.getMessage());
        }*/

    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.getAuth().getTokenSecret()));
    }
}
