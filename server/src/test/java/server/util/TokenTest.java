package server.util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals"})
public class TokenTest {

    @Test
    public void noSecretKey() {
        Token.removeSecretKey();

        assertEquals(Token.verifyToken("some token", "some username"), false);
    }

    @Test
    public void invalidToken() {
        Token.generateSecretKey();

        assertEquals(Token.verifyToken("some token", "some username"), false);
    }

    @Test
    public void validToken() {
        String generatedToken = Token.generate("bob");

        assertEquals(Token.verifyToken(generatedToken, "bob"), true);
    }

    @Test
    public void validTokenInvalidName() {
        String generatedToken = Token.generate("bob");

        assertEquals(Token.verifyToken(generatedToken, "alice"), false);
    }

    @Test
    public void multipleTokens() {
        String generatedToken1 = Token.generate("bob");
        String generatedToken2 = Token.generate("alice");
        String generatedToken3 = Token.generate("mike");

        assertEquals(generatedToken1.equals(generatedToken2), false);
        assertEquals(generatedToken3.equals(generatedToken2), false);
        assertEquals(generatedToken1.equals(generatedToken3), false);

        assertEquals(Token.verifyToken(generatedToken1, "bob"), true);
        assertEquals(Token.verifyToken(generatedToken2, "alice"), true);
        assertEquals(Token.verifyToken(generatedToken3, "mike"), true);
    }

}
