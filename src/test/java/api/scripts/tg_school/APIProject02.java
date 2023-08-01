package api.scripts.tg_school;

import api.pojo_classes.tg_school.CreateStudent;
import api.pojo_classes.tg_school.UpdateStudentPatch;
import api.pojo_classes.tg_school.UpdateStudentPut;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class APIProject02 {

    Response response;
    RequestSpecification baseSpec;
    Faker faker = new Faker();

    @BeforeMethod
    public void setAPI(){

        baseSpec = new RequestSpecBuilder().log(LogDetail.ALL)
                .setBaseUri(ConfigReader.getProperty("TGSchoolBaseURI"))
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void APIProject02(){

        /**
         * 1. Retrieve a list of all users.
         *
         * - Make a GET call for all TechGlobal students.
         * - Verify that a GET request status is 200 successes.
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         * - Validate the number of students when you get all students are more than equal to 2
         * - Validate second default users name is “John”, and the last name is “Doe”
         */


        response = RestAssured.given()
                .spec(baseSpec)
                .when().get("/students")
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("[1].firstName", equalTo("John"))
                .body("[1].lastName", equalTo("Doe"))
                .body("", hasSize(greaterThanOrEqualTo(2)))
                .extract().response();

        List<String> firstNameList = response.jsonPath().getList("firstName");

        System.out.println(firstNameList.size() + " IS THE AMOUNT OF USERS WE HAVE");
        Assert.assertTrue(firstNameList.size() >= 2);

        /**
         * 2. Create a new user
         *
         * - Make a POST call for all TechGlobal students.
         * - Verify that a POST request status is 200 successes.
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         * - Validate first name, last name, email, and dob you set is matching with the response.
         */


        CreateStudent createStudent = CreateStudent.builder()
                .firstName(faker.name().firstName()).lastName(faker.name().lastName())
                .email(faker.internet().emailAddress()).dob("2000-01-01")
                .build();


        response = RestAssured.given()
                .spec(baseSpec)
                .body(createStudent)
                .when().post("/students")
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("firstName", equalTo(createStudent.getFirstName()))
                .body("lastName", equalTo(createStudent.getLastName()))
                .body("email", equalTo(createStudent.getEmail()))
                .body("dob", equalTo(createStudent.getDob()))
                .extract().response();

        int student_id = response.jsonPath().getInt("id");

        /**
         * 3. Retrieve a specific user-created
         *
         * - Make a GET call for the specific user created.
         * - Verify that a GET request status is 200 successes.
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         * - Validate response of a specific user’s GET call is matching with the user you created.
         */

        response = RestAssured.given()
                .spec(baseSpec)
                .when().get("/students/" + student_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("firstName", equalTo(createStudent.getFirstName()))
                .body("lastName", equalTo(createStudent.getLastName()))
                .body("email", equalTo(createStudent.getEmail()))
                .body("dob", equalTo(createStudent.getDob()))
                .extract().response();


        /**
         * 4. Update an existing user
         *
         * - Make a PUT call to update ALL details of a created TechGlobal student.
         * - Verify that the PUT request status is 200 (success).
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         * - Retrieve the updated student's details and validate that the updated details match the changes made.
         */

        UpdateStudentPut updateStudentPut = UpdateStudentPut.builder()
                .firstName(faker.name().firstName()).lastName(faker.name().lastName())
                .email(faker.internet().emailAddress()).dob("2000-02-02")
                .build();

        response = RestAssured.given()
                .spec(baseSpec)
                .body(updateStudentPut)
                .when().put("/students/" + student_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("firstName", equalTo(updateStudentPut.getFirstName()))
                .body("lastName", equalTo(updateStudentPut.getLastName()))
                .body("email", equalTo(updateStudentPut.getEmail()))
                .body("dob", equalTo(updateStudentPut.getDob()))
                .extract().response();


        /**
         * 5. Partially update an existing User
         *
         * - Make a PATCH call and update the email and dob of a user you created.
         * - Verify that the PATCH request status is 200 (success).
         * - Retrieve the updated student's details and validate that the updated details match the changes made
         * and untouched fields remain the same.
         */

        UpdateStudentPatch updateStudentPatch = UpdateStudentPatch.builder()
                .email(faker.internet().emailAddress()).dob("2000-03-03")
                .build();


        response = RestAssured.given()
                .spec(baseSpec)
                .body(updateStudentPatch)
                .when().patch("/students/" + student_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("firstName", equalTo(updateStudentPut.getFirstName()))
                .body("lastName", equalTo(updateStudentPut.getLastName()))
                .body("email", equalTo(updateStudentPatch.getEmail()))
                .body("dob", equalTo(updateStudentPatch.getDob()))
                .extract().response();


        /**
         * 6. Retrieve a list of all users again
         *
         * - Make a GET call for all TechGlobal students.
         * - Verify that a GET request status is 200 successes.
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         * - Validate the number of students when you get all students are more than equal to 3
         */

        response = RestAssured.given()
                .spec(baseSpec)
                .when().get("/students")
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("", hasSize(greaterThanOrEqualTo(3)))
                .extract().response();

        firstNameList = response.jsonPath().getList("firstName");
        System.out.println(firstNameList.size() + " IS THE AMOUNT OF USERS WE HAVE");
        Assert.assertTrue(firstNameList.size() >= 3);


        /**
         * 7. Retrieve a specific user created to confirm the update.
         *
         * - Make a GET call for the specific user created again.
         * - Verify that a GET request status is 200 successes.
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         * - Validate that the information in the response body of a specific user’s GET call is matching with the
         * user you updated.
         */


        response = RestAssured.given()
                .spec(baseSpec)
                .when().get("/students/" + student_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .body("firstName", equalTo(updateStudentPut.getFirstName()))
                .body("lastName", equalTo(updateStudentPut.getLastName()))
                .body("email", equalTo(updateStudentPatch.getEmail()))
                .body("dob", equalTo(updateStudentPatch.getDob()))
                .extract().response();


        /**
         * 8. Finally, delete the user that you created.
         *
         * - Verify that a DELETE request status is 200 success.
         * - Assert that the response time is less than a particular value, say 200ms, to ensure the API's
         * performance is within acceptable limits. ( If it’s more than 200, you can increase the limit )
         */


        response = RestAssured.given()
                .spec(baseSpec)
                .when().delete("/students/" + student_id)
                .then().log().all().assertThat()
                .statusCode(200).time(Matchers.lessThan(3000L))
                .extract().response();

    }
}
