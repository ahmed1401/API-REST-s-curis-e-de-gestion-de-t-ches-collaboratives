package tn.enicarthage.arimayitest.dto;

import tn.enicarthage.arimayitest.model.Task;
import lombok.Data;

@Data
public class TaskStatusUpdateDto {
    private Task.Status status;
}
