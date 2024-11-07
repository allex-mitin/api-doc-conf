package org.example.pet;

import org.example.pet.web.PetApi;
import org.example.pet.web.dto.ModelApiResponse;
import org.example.pet.web.dto.Pet;
import org.instancio.Instancio;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController implements PetApi {

    @Override
    public ResponseEntity<Pet> addPet(Pet pet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByStatus(String status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByTags(List<String> tags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Pet> getPetById(Long petId) {
        return ResponseEntity.ok(Instancio.create(Pet.class));
    }

    @Override
    public ResponseEntity<Pet> updatePet(Pet pet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<ModelApiResponse> uploadFile(Long petId, String additionalMetadata, Resource body) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
