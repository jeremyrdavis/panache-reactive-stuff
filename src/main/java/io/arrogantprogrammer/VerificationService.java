package io.arrogantprogrammer;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VerificationService {


    Uni<Boolean> verify(){
        return Uni.createFrom().item(true);
    }
    
}
