package tn.enicarthage.arimayitest.dto;

import tn.enicarthage.arimayitest.model.User;
import lombok.Data;

@Data
public class UserRoleUpdateDto {
    private User.Role role;
}
