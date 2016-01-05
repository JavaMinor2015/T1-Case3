package teamonecasethree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The application server.
 */
@SpringBootApplication
public final class MainApplication {

    private MainApplication() {

    }

    /**
     * Go go gadget Kantilever!
     *
     * @param args arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
