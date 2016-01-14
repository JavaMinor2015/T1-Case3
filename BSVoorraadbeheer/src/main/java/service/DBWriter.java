package service;

import com.google.common.collect.Lists;
import entities.Product;
import entities.abs.PersistenceEntity;
import entity.BuildStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import peaseloxes.toolbox.util.IOUtil;
import peaseloxes.toolbox.util.RandUtil;
import repository.BuildRepository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/12/16.
 *
 * @param <T> the abstract entity for this service.
 */
@Service
public class DBWriter<T extends PersistenceEntity> {

    private static final int DEFAULT_MIN = 5000;
    private static final int DEFAULT_MAX = 15000;

    @Setter
    private int min = DEFAULT_MIN;
    @Setter
    private int max = DEFAULT_MAX;

    @Autowired
    @Setter
    private BuildRepository buildRepository;

    private static final Logger LOGGER = LogManager.getLogger(DBWriter.class);

    /**
     * Write a repository to a file, asynchronously.
     * <p>
     * Currently supports:
     * <li>Product
     *
     * @param repository the repository to dump.
     * @param clazz      the class type of the repository.
     * @param identifier the status identifier.
     */
    @Async
    public void write(final RestRepository<T> repository, final Class<T> clazz, final String identifier) {

        // TODO proper multi object support
        if (!clazz.equals(Product.class)) {
            throw new IllegalArgumentException("Can't write this object, it's not a supported class.");
        }

        // so we can sleep
        new Thread(
                // do not replace with lambda, sonarqube no likey.
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(RandUtil.rInt(min, max));
                        } catch (InterruptedException e) {
                            LOGGER.error(e);
                        }
                    }
                }
        ).start();
    }

    /**
     * Woop woop sonar.
     *
     * @param repository the repository to write.
     * @param identifier status identifier.
     */
    public void doWritingHereBecauseUnitTestingSucksOtherWise(
            final RestRepository<T> repository, final String identifier) {

        final List<T> result = Lists.newArrayList(repository.findAll());
        final List<String> lines = result.stream().map(T::toString).collect(Collectors.toList());
        IOUtil.writeLines("db-" + identifier + ".csv", lines);
        BuildStatus status = buildRepository.findOne(identifier);
        status.setReady(true);
        buildRepository.save(status);
    }
}
