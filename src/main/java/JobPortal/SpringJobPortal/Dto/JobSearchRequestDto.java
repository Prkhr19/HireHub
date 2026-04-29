package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.type.Jobtype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSearchRequestDto {

    private String keyword;
    private String location;
    private Jobtype jobType;
    private String company;
    private int page;
    private int size;

}
