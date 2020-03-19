package server.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;

public class Token {

    private static SecretKey secretKey;

    private static boolean noSecretKey() {
        return secretKey == null;
    }

    public static void generateSecretKey() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @SuppressWarnings("PMD.NullAssignment")
    public static void removeSecretKey() {
        secretKey = null;
    }

    /**
     * Generate a specific token for a username.
     *
     * @param username as string
     * @return token as string
     */
    public static String generate(String username) {
        if (noSecretKey()) {
            generateSecretKey();
        }
        return Jwts.builder().setSubject(username).signWith(secretKey).compact();
    }

    /**
     * Check if the token is valid.
     *
     * @param token as string
     * @return true if valid
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static boolean verifyToken(String token, String username) {
        if (noSecretKey()) {
            return false;
        }
        boolean result = true;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            result = generate(username).equals(token);
        } catch (IllegalArgumentException | SignatureException
                | ExpiredJwtException | MalformedJwtException e) {
            result = false;
        }
        return result;
    }
}
