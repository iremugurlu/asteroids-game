package server.util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PasswordTest {

    @Test
    public void randomSalt() {
        String salt1 = Password.salt();
        String salt2 = Password.salt();

        assertEquals(salt1.equals(salt2), false);
    }

    @Test
    public void inValid() {
        String salt = Password.salt();
        String pass1 = "1234";
        String pass2 = "5678";
        String hash1 = Password.hash(pass1,salt);

        assertEquals(Password.valid(hash1, pass2, salt), false);
    }

    @Test
    public void valid() {
        String salt = Password.salt();
        String pass1 = "1234";
        String pass2 = "1234";
        String hash1 = Password.hash(pass1,salt);

        assertEquals(Password.valid(hash1, pass2, salt), true);
    }

    @Test
    public void exception() {
        String salt = Password.salt();
        String pass1 = "1234";
        String pass2 = "1234";
        String hash1 = Password.hash(pass1,salt);
        Password.setALGORITHM("hello");

        assertEquals(Password.valid(hash1, pass2, salt), false);
    }
}
