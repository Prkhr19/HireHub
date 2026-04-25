package JobPortal.SpringJobPortal.Dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDto {
    String message;;
    String status;
    Long id;
    String title;
}
