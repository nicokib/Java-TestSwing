// Add the library for password hashing via Maven : com.password4j:password4j:1.8.4

package utils;

import com.password4j.Hash;
import com.password4j.Password;

public final class PasswordUtils {

    // The returned hash swill never conatians ";" character...
    public static String hashPassword(String password) {
        Hash hash = Password.hash(password)
                .addRandomSalt()
                .withArgon2();

        return hash.getResult();
    }

    public static boolean checkPassword(String passwordToCheck, String hashedPassword) {
        return Password.check(passwordToCheck, hashedPassword)
                .withArgon2();
    }
}
