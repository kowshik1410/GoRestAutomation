package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Locale;

import com.aventstack.extentreports.Status;

public class UserTests {

    public String userId;
    Faker faker;
    User userPayload;
    Logger logger;

    @BeforeClass
    public void setupData() {
    	faker = new Faker(Locale.forLanguageTag("en-IN"));
        userPayload = new User();
        userPayload.setName(faker.name().username());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setGender(faker.options().option("male", "female"));
        userPayload.setStatus(faker.options().option("active", "inactive"));
        logger = LogManager.getLogger(this.getClass());
    }
    

    @Test
    public void testPostUser() {
        ExtentReportManager.test.log(Status.INFO, "Starting testPostUser...");
        logger.info("Creating User...");
        
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().body();
        userId = response.jsonPath().getString("id");
        logger.info("User Created: " + userId);
        
        // Log body
        ExtentReportManager.test.log(Status.INFO, "Response Body: " + response.getBody().asString());

        // Assertions
        ExtentReportManager.test.log(Status.INFO, "Validating response code.");
        Assert.assertEquals(response.getStatusCode(), 201, "POST User - Status Code Validation");
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));
       
        ExtentReportManager.test.log(Status.PASS, "User successfully created with ID: " + userId);
    }

    @Test
    public void testGetUserById() {
        ExtentReportManager.test.log(Status.INFO, "Starting testGetUserById...");

        logger.info("Fetching User Info...");
        Response response = UserEndPoints.readUser(userId);
        response.then().log().body();

        // Log body
        ExtentReportManager.test.log(Status.INFO, "Response Body: " + response.getBody().asString());

        // Assertions
        ExtentReportManager.test.log(Status.INFO, "Validating response code.");
        Assert.assertEquals(response.getStatusCode(), 200, "GET User - Status Code Validation");
        Assert.assertEquals(response.jsonPath().getString("id"), userId, "GET User - ID Validation");
        Assert.assertEquals(response.jsonPath().getString("name"), userPayload.getName(), "GET User - Name Validation");
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));
        ExtentReportManager.test.log(Status.PASS, "User fetched successfully.");
    }

    @Test
    public void testUpdateUserById() {
        ExtentReportManager.test.log(Status.INFO, "Starting testUpdateUserById...");

        logger.info("Updating User...");
        userPayload.setEmail(faker.internet().emailAddress());
        Response response = UserEndPoints.updateUser(userId, userPayload);
        response.then().log().body();

        // Log body
        ExtentReportManager.test.log(Status.INFO, "Response Body: " + response.getBody().asString());

        // Assertions
        ExtentReportManager.test.log(Status.INFO, "Validating response code.");
        Assert.assertEquals(response.getStatusCode(), 200, "UPDATE User - Status Code Validation");
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));
        Assert.assertEquals(response.jsonPath().getString("email"), userPayload.getEmail(), "UPDATE User - Email Validation");

        ExtentReportManager.test.log(Status.PASS, "User email updated successfully.");
    }

    @Test
    public void testDeleteUserById() {
        ExtentReportManager.test.log(Status.INFO, "Starting testDeleteUserById...");

        logger.info("Deleting User...");
        Response response = UserEndPoints.deleteUser(userId);
        response.then().log().body();

        // Log body
        ExtentReportManager.test.log(Status.INFO, "Response Body: " + response.getBody().asString());
        // Assertions
        ExtentReportManager.test.log(Status.INFO, "Validating response code.");
        Assert.assertEquals(response.getStatusCode(), 204, "DELETE User - Status Code Validation");

        logger.info("User deleted successfully.");
        ExtentReportManager.test.log(Status.PASS, "User deleted successfully.");
    }
}
