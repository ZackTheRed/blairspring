package com.blair.blairspring.util.serialization;

import com.blair.blairspring.model.ibatisschema.Nationality;
import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.model.ibatisschema.Team;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.Optional;

@Profile("spring-data-rest-disabled")
@JsonComponent
public class PlayerJsonSerializer extends JsonSerializer<Player> {

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldId(player.getId());
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("fullName", player.getFirstName() + " " + player.getLastName());
        jsonGenerator.writeStringField("team", Optional.ofNullable(player.getTeam()).map(Team::getName).orElse(null));
        jsonGenerator.writeStringField("birthDate", player.getBirthDate().toString());

        jsonGenerator.writeStringField("nationality", player.getNationalities()
                .stream()
                .findFirst()
                .map(Nationality::getName)
                .orElse(null));
        jsonGenerator.writeEndObject();
    }
}
