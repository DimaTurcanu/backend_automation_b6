package api.pojo_classes.tg_school;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStudentPatch {

    private String email;
    private String dob;
}
