package org.example.pet;

import jakarta.validation.Valid;
import org.example.user.web.UserApi;
import org.example.user.web.dto.User;
import org.instancio.Instancio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {
    @Override
    public ResponseEntity<User> createUser(User user) {
throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<User> createUsersWithListInput(List<@Valid User> user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Void> deleteUser(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<User> getUserByName(String username) {
        return ResponseEntity.ok(Instancio.create(User.class));
    }

    @Override
    public ResponseEntity<String> loginUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Void> logoutUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Void> updateUser(String username, User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
