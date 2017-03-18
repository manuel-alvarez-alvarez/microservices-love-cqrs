package es.microcqrs.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Base class for the services of the readmodels
 */
abstract class AbstractReadmodelService {

    @Resource
    private PlatformTransactionManager txtManager;

    <I> Flux<I> executeQuery(Supplier<Stream<I>> supplier) {
        TransactionTemplate txt = new TransactionTemplate();
        txt.setReadOnly(true);
        TransactionStatus status = this.txtManager.getTransaction(txt);
        Stream<I> stream = supplier.get();
        return Flux
                .fromIterable(stream::iterator)
                .doOnTerminate(stream::close)
                .doOnTerminate((() -> this.txtManager.commit(status)));
    }


}
