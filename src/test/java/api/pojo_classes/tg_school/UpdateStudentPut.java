package api.pojo_classes.tg_school;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStudentPut {

    private String firstName;
    private String lastName;
    private String email;
    private String dob;
}
