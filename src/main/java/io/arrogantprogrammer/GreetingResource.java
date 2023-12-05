package io.arrogantprogrammer;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/hello")
public class GreetingResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    VerificationService verificationService;

    @Inject
    GreetingRepository  greetingRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    @WithTransaction
    public Uni<List<Greeting>> helloAll() {
        greetingRepository.listAll().onItem().transform(list -> {
            LOGGER.debug("records : {}", list.size());
            return null;
        });
        return Greeting.listAll();
    }

    @POST@WithTransaction
    public Uni<GreetingRecord> addGreeting(final String greetingToAdd) {

//        Greeting greeting = new Greeting(greetingToAdd, true);
//        return greetingRepository.persist(greeting).onItem().transform(persistedGreeting -> {
//            return new GreetingRecord(persistedGreeting.id, persistedGreeting.text, persistedGreeting.verified);
//        });

        Uni<Greeting> greetingUni = Uni.createFrom().item(new Greeting(greetingToAdd));
        return greetingUni.onItem().transform(greeting -> {
            return verificationService.verify()
                    .flatMap(verified -> {
                        greeting.verified = verified;
                        return greeting.persist();
            }).map(res -> {
                        return new GreetingRecord(greeting.id, greeting.text, greeting.verified);

                    });
        });
    }

}
