package JobPortal.SpringJobPortal.Entity;

import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import JobPortal.SpringJobPortal.Entity.type.Jobtype;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;


    @Column(nullable = false)

    private Double salary;

    @Column(nullable = false)
    private String experienceRequired;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Jobtype jobType;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime postedAt;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private RecruiterProfile recruiter;




}
