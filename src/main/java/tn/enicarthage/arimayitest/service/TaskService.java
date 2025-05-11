package tn.enicarthage.arimayitest.service;

import tn.enicarthage.arimayitest.dto.TaskCreationDto;
import tn.enicarthage.arimayitest.dto.TaskStatusUpdateDto;
import tn.enicarthage.arimayitest.model.Task;
import tn.enicarthage.arimayitest.model.User;
import tn.enicarthage.arimayitest.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final UserService userService;
    
    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }
    
    public Task createTask(TaskCreationDto taskDto, String creatorEmail) {
        User assignedUser = userService.findById(taskDto.getAssignedUserId());
        
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(Task.Status.A_FAIRE);
        task.setAssignedUser(assignedUser);
        
        return taskRepository.save(task);
    }
    
    public List<Task> getUserTasks(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return taskRepository.findByAssignedUser(user);
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Task updateTaskStatus(Long taskId, TaskStatusUpdateDto statusUpdateDto, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        User user = userService.findByEmail(userEmail);
        
        if (!task.getAssignedUser().getId().equals(user.getId())) {
            throw new RuntimeException("Only the assigned user can update the task status");
        }
        
        task.setStatus(statusUpdateDto.getStatus());
        return taskRepository.save(task);
    }
}
