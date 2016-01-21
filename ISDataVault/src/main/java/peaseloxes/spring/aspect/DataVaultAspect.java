package peaseloxes.spring.aspect;

import entities.abs.PersistenceEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import peaseloxes.spring.annotations.DataVaultObservable;
import service.DataVaultService;

/**
 * Created by alex on 1/19/16.
 */
@Aspect
public class DataVaultAspect {
    @Autowired
    @Setter
    private DataVaultService vaultService;

    /**
     * Handle the vault annotation.
     *
     * @param jointPoint the original method.
     * @param observable the annotation.
     * @return the original method with a business key.
     * @throws Throwable and catchable.
     */
    @SuppressWarnings("squid:S00112")
    @Around("@annotation(observable)")
    public Object handleVaultAnnotation(final ProceedingJoinPoint jointPoint,
                                        final DataVaultObservable observable) throws Throwable {
        final List<Object> processedArgs = new ArrayList<>();
        for (Object o : jointPoint.getArgs()) {
            processedArgs.add(o);
            if (PersistenceEntity.class.isAssignableFrom(o.getClass())) {
                vaultService.postToVault((PersistenceEntity) o);
            }
        }
        return jointPoint.proceed(processedArgs.toArray());
    }
}
