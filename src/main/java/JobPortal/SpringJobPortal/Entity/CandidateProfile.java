package JobPortal.SpringJobPortal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phoneNo;

    private String skills;

    private String resumeUrl;

    @Column(nullable = false)
    private Long experience;

    @Column(nullable = false)
    private String Education;

    @Column(nullable = false)
    private String location;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

   }
