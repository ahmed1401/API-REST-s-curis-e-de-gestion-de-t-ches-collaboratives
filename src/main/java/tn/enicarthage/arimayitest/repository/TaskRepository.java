package tn.enicarthage.arimayitest.repository;

import tn.enicarthage.arimayitest.model.Task;
import tn.enicarthage.arimayitest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUser(User user);
}
