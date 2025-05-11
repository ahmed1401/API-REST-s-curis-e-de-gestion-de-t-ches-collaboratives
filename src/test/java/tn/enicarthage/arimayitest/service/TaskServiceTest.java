package tn.enicarthage.arimayitest.service;

import tn.enicarthage.arimayitest.dto.TaskStatusUpdateDto;
import tn.enicarthage.arimayitest.model.Task;
import tn.enicarthage.arimayitest.model.User;
import tn.enicarthage.arimayitest.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(User.Role.USER);

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(Task.Status.A_FAIRE);
        task.setAssignedUser(user);
    }

    @Test
    void updateTaskStatus_ShouldUpdateStatusWhenUserIsAssigned() {
        // Arrange
        TaskStatusUpdateDto statusUpdateDto = new TaskStatusUpdateDto();
        statusUpdateDto.setStatus(Task.Status.EN_COURS);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userService.findByEmail("test@example.com")).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task updatedTask = taskService.updateTaskStatus(1L, statusUpdateDto, "test@example.com");

        // Assert
        assertEquals(Task.Status.EN_COURS, updatedTask.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTaskStatus_ShouldThrowExceptionWhenUserIsNotAssigned() {
        // Arrange
        TaskStatusUpdateDto statusUpdateDto = new TaskStatusUpdateDto();
        statusUpdateDto.setStatus(Task.Status.EN_COURS);

        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setEmail("other@example.com");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userService.findByEmail("other@example.com")).thenReturn(otherUser);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.updateTaskStatus(1L, statusUpdateDto, "other@example.com");
        });

        assertEquals("Only the assigned user can update the task status", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }
}
