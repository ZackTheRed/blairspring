package com.blair.blairspring.events;

import com.blair.blairspring.model.userschema.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler
@Component
@Slf4j
public class UserEventHandler {

    @HandleBeforeDelete
    public void handlePersonDelete(User user) {
        log.info("Attempting to delete User: {}", user.getUsername());
    }

}
