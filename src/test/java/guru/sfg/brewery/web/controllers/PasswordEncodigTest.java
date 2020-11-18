package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class PasswordEncodigTest {

    static final String PASSWORD = "guru";
    static final String SALTVAL = "123LongSaltVal";


    @Test
    void testBcrypt15() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

        System.out.println(passwordEncoder.encode("tiger"));
    }

    @Test
    void testBcrypt() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));
    }

    @Test
    void testSha256() {

        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));
    }

    @Test
    void testNoOp() {

        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println(noOp.encode(PASSWORD));

    }

    @Test
    void testLdap() {

        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        String encoded = ldap.encode(PASSWORD);
        System.out.println(encoded);

        assertTrue(ldap.matches(PASSWORD, encoded));
    }

    @Test
    void hashingExample(){
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + SALTVAL;
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));

    }
}
