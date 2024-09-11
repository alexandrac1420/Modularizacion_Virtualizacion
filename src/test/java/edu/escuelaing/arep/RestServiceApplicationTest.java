package edu.escuelaing.arep;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestServiceApplicationTest extends TestCase {

    public RestServiceApplicationTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RestServiceApplicationTest.class);
    }

    public void testMain() {
        RestServiceApplication.main(new String[]{});
        assertTrue(true);
    }

    public void testGetPortFromEnv() {
        System.setProperty("PORT", "6000");
        int port = RestServiceApplication.getPort();
        assertEquals(6000, port);

        System.clearProperty("PORT");
    }

    public void testGetDefaultPort() {
        System.clearProperty("PORT");

        int port = RestServiceApplication.getPort();
        assertEquals(6000, port);
    }
}
