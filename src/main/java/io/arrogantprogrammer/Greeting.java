package io.arrogantprogrammer;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Greeting extends PanacheEntity{

    String text;

    boolean verified;

    public Greeting(final String text, final boolean verified){
        this.text = text;
        this.verified = verified;
    }

    public Greeting(){

    }

    public Greeting(String greetingText) {
        this.text = greetingText;
    }

    public String getText(){
        return text;
    }

    public boolean isVerified(){
        return verified;
    }
}