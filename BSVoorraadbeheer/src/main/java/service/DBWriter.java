package service;

import com.google.common.collect.Lists;
import entities.Product;
import entities.abs.PersistenceEntity;
import entity.BuildStatus;
import java.util.List;
import java.util.stream.Collectors;
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
 */
@Service
public class DBWriter<T extends PersistenceEntity> {

    @Autowired
    private BuildRepository buildRepository;

    private static final Logger LOGGER = LogManager.getLogger(DBWriter.class);

    @Async
    public void write(final RestRepository<T> repository, final Class<T> clazz, final String identifier) {

        // TODO proper multi object support
        if (!clazz.equals(Product.class)) {
            throw new IllegalArgumentException("Can't write this object, it's not a supported class.");
        }

        ((Runnable) () -> {
            try {
                // it's a kind of magic
                Thread.sleep(RandUtil.rInt(5000, 15000));
                final List<T> result = Lists.newArrayList(repository.findAll());
                final List<String> lines = result.stream().map(T::toString).collect(Collectors.toList());
                IOUtil.writeLines("db-" + identifier + ".csv", lines);
                BuildStatus status = buildRepository.findOne(identifier);
                status.setReady(true);
                buildRepository.save(status);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }).run();

    }
}
