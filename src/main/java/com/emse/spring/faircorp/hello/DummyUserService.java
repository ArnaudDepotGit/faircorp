package com.emse.spring.faircorp.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyUserService implements UserService{

    private final GreetingService greetingService;

    @Autowired
    public DummyUserService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public void greetAll() {
        List<String> prenoms = List.of("Elodie","Charles");
        for (String prenom : prenoms) {
            greetingService.greet(prenom);
        }
    }
}
