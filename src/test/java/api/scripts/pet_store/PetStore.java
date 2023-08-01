package api.scripts.pet_store;

import api.pojo_classes.pet_store.AddPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdatePet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;

public class PetStore {

    Response response;
    private RequestSpecification baseSpec;


    @BeforeMethod
    public void setAPI() {

        baseSpec = new RequestSpecBuilder().log(LogDetail.ALL)
                .setBaseUri(ConfigReader.getProperty("PetStoreBaseURI"))
                .setContentType(ContentType.JSON)
                .build();
    }


    @Test
    public void petStoreAPI() throws JsonProcessingException {

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

        Category category = Category.builder()
                .id(10).name("horse").build();

        Tags tags = Tags.builder()
                .id(15).name("unicorn").build();

        AddPet addPet = AddPet.builder()
                .id(9).category(category).name("mustang")
                .photoUrls(Arrays.asList("Mustang photo URL"))
                .tags(Arrays.asList(tags))
                .status("available").build();

        ObjectMapper objectMapper = new ObjectMapper();

        String JSONFormat = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(addPet);

        System.out.println(JSONFormat);

        response = RestAssured.given()
                .spec(baseSpec)
                .body(addPet)
                .when().post("/v2/pet")
                .then().log().all().assertThat().statusCode(200)
                .time(Matchers.lessThan(3000L)).body("tags[0].name", equalTo(tags.getName()))
                .extract().response();


        /**
         * 1. Update the existing pet
         * 2. Validate status
         * 3. Validate response time
         * 4. Validate the category name is matching with updated category name
         */


        int id = response.jsonPath().getInt("id");

        System.out.println(id);

        Category updateCategory = Category.builder()
                .id(11).name("snake").build();

        UpdatePet updatePet = UpdatePet.builder()
                .id(id).category(updateCategory)
                .name("musthang").photoUrls(Arrays.asList("Mustang photo URL"))
                .tags(Arrays.asList(tags))
                .status("unavailable").build();

        response = RestAssured.given()
                .spec(baseSpec)
                .body(updatePet)
                .when().put("/v2/pet/")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();


        response = RestAssured.given()
                .spec(baseSpec)
                .when().get("/v2/pet/" + id)
                .then().log().all()
                .assertThat().statusCode(200).time(Matchers.lessThan(5000L))
                .body("category.name", equalTo(updateCategory.getName()))
                .body("status", equalTo(updatePet.getStatus()))
                .extract().response();
    }
}
