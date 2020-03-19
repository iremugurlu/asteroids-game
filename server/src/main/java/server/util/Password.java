package server.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class Password {

    private static final int KEY_LENGTH = 512;
    private static final int CYCLES = 81235;
    private static String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final SecureRandom RAND = new SecureRandom();

    public static void setALGORITHM(String ALGORITHM) {
        Password.ALGORITHM = ALGORITHM;
    }

    /**
     * Generate random salt.
     *
     * @return salt as string
     */
    public static String salt() {
        byte[] salt = new byte[KEY_LENGTH];
        RAND.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hash the password and the salt.
     *
     * @param password password to be hashed
     * @param salt     random salt
     * @return hash of password and salt
     */
    public static String hash(String password, String salt) {
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, bytes, CYCLES, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hashedPassword = skf.generateSecret(spec).getEncoded();
            spec.clearPassword();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return null;
        }
    }

    /**
     * Check if password is valid.
     *
     * @param original original password to check against
     * @param password to be checked
     * @param salt     random string used when hashing the original password.
     * @return true if valid
     */
    public static boolean valid(String original, String password, String salt) {
        String encrypted = hash(password, salt);
        if (encrypted != null) {
            return encrypted.equals(original);
        }
        return false;
    }
}
