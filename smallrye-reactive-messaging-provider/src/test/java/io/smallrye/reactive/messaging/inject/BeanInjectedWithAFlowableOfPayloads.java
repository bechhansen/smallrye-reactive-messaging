package io.smallrye.reactive.messaging.inject;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Channel;

@ApplicationScoped
public class BeanInjectedWithAFlowableOfPayloads {

    private final Flowable<String> constructor;
    @Inject
    @Channel("hello")
    private Flowable<String> field;

    @Inject
    public BeanInjectedWithAFlowableOfPayloads(@Channel("bonjour") Flowable<String> constructor) {
        this.constructor = constructor;
    }

    public List<String> consume() {
        return Flowable
                .concat(
                        Flowable.fromPublisher(constructor),
                        Flowable.fromPublisher(field))
                .toList()
                .blockingGet();
    }

}
