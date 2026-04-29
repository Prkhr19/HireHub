package JobPortal.SpringJobPortal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidateId")
    private CandidateProfile candidates;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private Job job;

    @CreationTimestamp
    private LocalDateTime savedAt;

}
