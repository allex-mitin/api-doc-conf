package org.example.backend.apifirst.mapper;

import org.example.info.asyncapi.SavePetMessage;
import org.example.info.client.pet.dto.Pet;
import org.example.info.client.user.dto.User;
import org.example.info.controller.dto.InfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DtoMapper {


    @Mapping(target = "requestId", expression = "java(randomUUID())")
    @Mapping(target = "pet", source = "pet")
    @Mapping(target = "user", source = "user")
    InfoResponse toResponse(Pet pet, User user);

    @Mapping(target = "messageId", expression = "java(randomUUID().toString())")
    @Mapping(target = "pet", source = "pet")
    @Mapping(target = "user", source = "user")
    SavePetMessage toKafkaMessage(Pet pet, User user);

    default UUID randomUUID(){
        return UUID.randomUUID();
    }


}
