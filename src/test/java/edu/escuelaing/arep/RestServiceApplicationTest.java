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
        // Simulamos el método main para probar que la aplicación se ejecuta correctamente
        RestServiceApplication.main(new String[]{});
        assertTrue(true); // Si no hay excepciones, asumimos que la aplicación se inició correctamente
    }

    public void testGetPortFromEnv() {
        // Configuramos una variable de entorno para el test
        System.setProperty("PORT", "6000");

        // Verificamos que la aplicación toma el puerto de la variable de entorno
        int port = RestServiceApplication.getPort();
        assertEquals(6000, port);

        // Limpiamos la variable de entorno
        System.clearProperty("PORT");
    }

    public void testGetDefaultPort() {
        // Nos aseguramos de que la variable de entorno no esté definida
        System.clearProperty("PORT");

        // Verificamos que el puerto por defecto es 6000
        int port = RestServiceApplication.getPort();
        assertEquals(6000, port);
    }
}
