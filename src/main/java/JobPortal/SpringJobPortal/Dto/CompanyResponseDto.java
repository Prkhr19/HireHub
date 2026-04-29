package JobPortal.SpringJobPortal.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponseDto {
    private String message;
    private String companyName;
    private String description;

}
