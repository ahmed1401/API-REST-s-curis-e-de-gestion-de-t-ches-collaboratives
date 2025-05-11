package tn.enicarthage.arimayitest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.A_FAIRE;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;
    
    public enum Status {
        A_FAIRE, EN_COURS, TERMINEE
    }
}
