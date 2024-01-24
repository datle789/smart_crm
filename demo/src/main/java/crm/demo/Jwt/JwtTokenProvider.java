package crm.demo.Jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import crm.demo.Security.CustomUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

  @Value("${crm.demo.jwt.secret}")
  private String JWT_SECRET;
  @Value("${crm.demo.jwt.expiration}")
  private int JWT_EXPIRATION;

  public String genarateToken(CustomUserDetail customUserDetail) {
    Date now = new Date();
    Date dateExpried = new Date(now.getTime() + JWT_EXPIRATION);
    return Jwts.builder().setSubject(customUserDetail.getUsername())
        .setIssuedAt(now)
        .setExpiration(dateExpried)
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact();
  }

  public String getUserNameFromJwt(String token) {
    Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT Token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT Token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT Token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims String is empty");
    }
    return false;
  }
}
