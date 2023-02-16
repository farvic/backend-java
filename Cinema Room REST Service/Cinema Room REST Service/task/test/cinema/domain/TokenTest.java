package cinema.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TokenTest {
    Token tokenUnderTest;
    @BeforeEach
    void setUp() {
        tokenUnderTest = new Token("token");
    }

    @Test
    void setToken() {
        tokenUnderTest.setToken("newToken");
        Assertions.assertEquals(tokenUnderTest.getToken(), "newToken");
    }


    @Test
    void testToString() {
        Assertions.assertEquals(tokenUnderTest.toString(), "Token{token='token'}");
    }
}