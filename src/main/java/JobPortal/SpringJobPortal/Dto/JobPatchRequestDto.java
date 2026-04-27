package JobPortal.SpringJobPortal.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPatchRequestDto {
    private Double salary;
    private String updates;
}
