package org.example.backend.apifirst.mapper;

import org.example.backend.apifirst.client.pet.dto.Pet;
import org.example.backend.apifirst.client.user.dto.User;
import org.example.backend.apifirst.controller.dto.InfoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    InfoResponse toResponse(Pet pet, User user);

}
