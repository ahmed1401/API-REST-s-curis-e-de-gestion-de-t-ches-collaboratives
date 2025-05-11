package tn.enicarthage.arimayitest.controller;

import tn.enicarthage.arimayitest.dto.TaskCreationDto;
import tn.enicarthage.arimayitest.dto.TaskStatusUpdateDto;
import tn.enicarthage.arimayitest.model.Task;
import tn.enicarthage.arimayitest.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "API de gestion des tâches")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @Operation(summary = "Créer une tâche", description = "Crée une nouvelle tâche et l'assigne à un utilisateur")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Task> createTask(@RequestBody TaskCreationDto taskDto, Authentication authentication) {
        String email = authentication.getName();
        Task createdTask = taskService.createTask(taskDto, email);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/my-tasks")
    @Operation(summary = "Voir ses tâches", description = "Récupère toutes les tâches assignées à l'utilisateur connecté")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getMyTasks(Authentication authentication) {
        String email = authentication.getName();
        List<Task> tasks = taskService.getUserTasks(email);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping
    @Operation(summary = "Voir toutes les tâches", description = "Récupère toutes les tâches (admin uniquement)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{taskId}/status")
    @Operation(summary = "Mettre à jour le statut d'une tâche", description = "Met à jour le statut d'une tâche (uniquement par l'utilisateur assigné)")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody TaskStatusUpdateDto statusUpdateDto,
            Authentication authentication) {
        String email = authentication.getName();
        Task updatedTask = taskService.updateTaskStatus(taskId, statusUpdateDto, email);
        return ResponseEntity.ok(updatedTask);
    }
}
