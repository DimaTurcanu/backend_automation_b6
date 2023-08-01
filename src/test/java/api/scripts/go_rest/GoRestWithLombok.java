package api.scripts.go_rest;

import api.pojo_classes.go_rest.CreateUserWithLombok;
import api.pojo_classes.go_rest.UpdateUserWithLombok;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.Matchers.equalTo;

public class GoRestWithLombok {


    Response response;
    Faker faker = new Faker();
    String updatedUserName;
    String updatedUserEmail;
    String goRestGender;
    String goRestStatus;
    RequestSpecification baseSpec;
    /**
     * ObjectMapper is a class that is coming from faster.xml Jackson library
     * It is helping us to do Serialization. It converts Java objects to JSON object
     * So we can use it inside the Request body for POST or PUT
     */
    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeMethod
    public void setAPI() {

        baseSpec = new RequestSpecBuilder().log(LogDetail.ALL)
                .setBaseUri(ConfigReader.getProperty("GoRestBaseURI"))
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", ConfigReader.getProperty("GoRestToken"))
                .build();
    }


    @Test
    public void goRestCRUDWithLombok(){

        CreateUserWithLombok createUser = CreateUserWithLombok.builder()
                .name("Tech Global").email(faker.internet().emailAddress())
                .gender("female").status("active").build();

        response = RestAssured.given()
                .spec(baseSpec)
                .body(createUser)
                .when().post("/public/v2/users")
                .then().log().all().assertThat().statusCode(201)
                .time(Matchers.lessThan(4000L)).body("name", equalTo(createUser.getName()))
                .extract().response();

        // Since we used Hamcrest for validation by chaining it to our API call, we don't need to use
        // TestNG to assert anymore.
//        String responseName = response.jsonPath().getString("name");
//        String requestName = createUser.getName();
//
//        Assert.assertEquals(responseName, requestName);

        /**
         * 1. Create a GET call
         * 2. Validate Status Code
         * 3. Validate Response Time
         * 4. Validate if the email coming from the response of GET call is equal to email we provided.
         */

        int user_id = response.jsonPath().getInt("id");

        response = RestAssured.given()
                .spec(baseSpec)
                .when().get("/public/v2/users/" + user_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(4000L))
                .body("email", equalTo(createUser.getEmail()))
                .extract().response();


        /**
         * 1. Update the user we created - update name and email
         * 2. Validate status code
         * 3. Validate response time
         * 4. Validate e-mail
         */

        UpdateUserWithLombok updateUser = UpdateUserWithLombok.builder()
                .name(faker.harryPotter().character())
                .email(faker.internet().emailAddress())
                .build();

        response = RestAssured.given()
                .spec(baseSpec)
                .body(updateUser)
                .when().put("/public/v2/users/" + user_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(4000L))
                .body("email", equalTo(updateUser.getEmail()))
                .extract().response();


        response = RestAssured.given()
                .spec(baseSpec)
                .when().delete("/public/v2/users/" + user_id)
                .then().log().all().assertThat()
                .statusCode(204).time(Matchers.lessThan(4000L))
                .extract().response();







    }
}
