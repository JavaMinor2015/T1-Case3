package teamonecasethree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableAsync;
import peaseloxes.spring.aspect.HateoasLinkAspect;

/**
 * The application server.
 */
@SpringBootApplication
@ComponentScan({"service"})
@EntityScan("entities")
@EnableJpaRepositories(value = "repository")
@EnableMongoRepositories(value = "repository")
@EnableAsync
@EnableAspectJAutoProxy
public class MainApplication {

    public MainApplication() {

    }

    /**
     * Go go gadget Kantilever!
     *
     * @param args arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    /**
     * Create embedded database.
     *
     * @return an embedded data source.
     */
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                build();
    }

    /**
     * Register a HateoasLinkAspect.
     *
     * @return a HateoasLinkAspect instance.
     * @see peaseloxes.spring.annotations.WrapWithLink
     * @see peaseloxes.spring.annotations.WrapWithLinks
     */
    @Bean
    public HateoasLinkAspect hateoasAspect() {
        return new HateoasLinkAspect();
    }
}
