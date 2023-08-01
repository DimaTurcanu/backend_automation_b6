package api.pojo_classes.pet_store;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Category {

    private int id;
    private String name;
}
