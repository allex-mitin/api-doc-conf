package org.example.backend.apifirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.apifirst.client.pet.client.PetApi;
import org.example.backend.apifirst.client.user.client.UserApi;
import org.example.backend.apifirst.controller.dto.InfoResponse;
import org.example.backend.apifirst.mapper.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InfoApiController implements InfoApi {

    private final PetApi petApi;
    private final UserApi userApi;
    private final DtoMapper mapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public ResponseEntity<InfoResponse> getInfo(Long petId, String username) {
        var pet = petApi.getPetById(petId);
        var user = userApi.getUserByName(username);

        kafkaTemplate.send("savePetTopic", mapper.toKafkaMessage(pet, user));

        return ResponseEntity.ok(mapper.toResponse(pet,user));
    }
}
