package com.blair.blairspring.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ValidatorEventRegistry {

    private final ValidatingRepositoryEventListener validatingRepositoryEventListener;
    private final Map<String, Validator> validators;

    @PostConstruct
    public void postConstruct() throws Exception {
        List<String> events = Arrays.asList("beforeDelete");
        for (Map.Entry<String, Validator> entry : validators.entrySet()) {
            events.stream()
                    .filter(p -> entry.getKey().startsWith(p))
                    .findFirst()
                    .ifPresent(
                            p -> validatingRepositoryEventListener
                                    .addValidator(p, entry.getValue()));
        }
    }

}