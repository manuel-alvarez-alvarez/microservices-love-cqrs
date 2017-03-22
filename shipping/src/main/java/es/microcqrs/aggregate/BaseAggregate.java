package es.microcqrs.aggregate;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * Base class for all the aggregates
 */
abstract class BaseAggregate {

    @Resource
    private Processor stream;

    <E> E send(E message) {
        stream.output().send(MessageBuilder.withPayload(message).setHeader("type", message.getClass().getSimpleName()).build());
        return message;
    }
}
