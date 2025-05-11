package tn.enicarthage.arimayitest.controller;

import tn.enicarthage.arimayitest.dto.UserCreationDto;
import tn.enicarthage.arimayitest.dto.UserRoleUpdateDto;
import tn.enicarthage.arimayitest.model.User;
import tn.enicarthage.arimayitest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API de gestion des utilisateurs")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs", description = "Récupère la liste de tous les utilisateurs (admin uniquement)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/admin")
    @Operation(summary = "Créer un administrateur", description = "Crée un nouvel utilisateur avec le rôle ADMIN (admin uniquement)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdmin(@RequestBody UserCreationDto userCreationDto) {
        User admin = userService.createAdmin(userCreationDto);
        return ResponseEntity.ok(admin);
    }

    @PatchMapping("/{userId}/role")
    @Operation(summary = "Modifier le rôle d'un utilisateur", description = "Change le rôle d'un utilisateur existant (admin uniquement)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Long userId,
            @RequestBody UserRoleUpdateDto roleUpdateDto) {
        User updatedUser = userService.updateUserRole(userId, roleUpdateDto.getRole());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Obtenir les détails d'un utilisateur", description = "Récupère les détails d'un utilisateur spécifique (admin uniquement)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
