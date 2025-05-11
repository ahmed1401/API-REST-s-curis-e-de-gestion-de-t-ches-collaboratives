package tn.enicarthage.arimayitest.dto;

import lombok.Data;

@Data
public class TaskCreationDto {
    private String title;
    private String description;
    private Long assignedUserId;
}
