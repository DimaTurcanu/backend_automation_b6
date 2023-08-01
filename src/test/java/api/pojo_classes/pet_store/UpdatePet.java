package api.pojo_classes.pet_store;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdatePet {

    /**
     * {
     *   "id": 9223372016900058000,
     *   "category": {
     *     "id": 0,
     *     "name": "string"
     *   },
     *   "name": "doggie",
     *   "photoUrls": [
     *     "string"
     *   ],
     *   "tags": [
     *     {
     *       "id": 0,
     *       "name": "string"
     *     }
     *   ],
     *   "status": "available"
     * }
     */


    private int id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tags> tags;
    private String status;
}
