package JobPortal.SpringJobPortal.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String companyName;


    private String webSite;

    @Column(nullable = false)
    private String location;

    private String discription;

    @OneToMany(mappedBy = "company")
    private List<RecruiterProfile> recruiters;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;



}
