package api.pojo_classes.go_rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserWithLombok {

    private String name;
    private String email;
}
