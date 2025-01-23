package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestContext;

import com.github.javafaker.Faker;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import static org.hamcrest.Matchers.*;

public class TestPostUser {

    public int userId;
    Faker faker;
    User userPayload;
    Logger logger;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayload = new User();
        userPayload.setName(faker.name().username());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setGender(faker.options().option("male", "female"));
        userPayload.setStatus(faker.options().option("active", "inactive"));
        logger = LogManager.getLogger(this.getClass());
    }

    @Test
    public void testPostUser(ITestContext context) throws Exception {
        ExtentReportManager.test.log(Status.INFO, "Starting testPostUser...");
        logger.info("Serializing User object...");
        
        // Serialize userPayload to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userPayload);
        
        // Log serialized JSON
        /// added comment
        ExtentReportManager.test.info(MarkupHelper.createCodeBlock(userJson, CodeLanguage.JSON));
        logger.info("Serialized User JSON: " + userJson);
        
        // Send POST request with serialized JSON
        Response response = UserEndPoints.createUser(userJson);
        response.then().log().body();
        userId = response.jsonPath().getInt("id");
        context.setAttribute("user_id", userId);
        logger.info("User Created: " + userId);
  

        // Perform Hamcrest assertions
        Assert.assertEquals(response.getStatusCode(), 201, "POST User - Status Code Validation");
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));

        response.then().assertThat()
            .statusCode(201)
            .and()
            .body("name", equalTo(userPayload.getName()))
            .body("email", equalTo(userPayload.getEmail()))
            .body("gender", equalTo(userPayload.getGender()))
            .body("status", equalTo(userPayload.getStatus()))
            .body("id", greaterThan(0))
            .body("id", instanceOf(Integer.class))
            .body("name", instanceOf(String.class))
            .body("email", containsString("@"))
            .body("status", anyOf(equalTo("active"), equalTo("inactive")));

        // Logging success in the Extent Report
        ExtentReportManager.test.log(Status.PASS, "User successfully created with ID: " + userId);
    }
}
