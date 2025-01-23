package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import static org.hamcrest.Matchers.*;

public class TestUpdateUserById {
    Faker faker;
    User userPayload;
    Logger logger;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayload = new User();
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setName(faker.name().username());
        userPayload.setGender(faker.options().option("male", "female"));
        userPayload.setStatus(faker.options().option("active", "inactive"));
        logger = LogManager.getLogger(this.getClass());
    }

    @Test
    public void testUpdateUserById(ITestContext context) {
        ExtentReportManager.test.log(Status.INFO, "Starting testUpdateUserById...");
        logger.info("Updating User...");
        
        int userId = (int) context.getAttribute("user_id");
        Response response = UserEndPoints.updateUser(userId, userPayload);
        response.then().log().body();

        // Log the response body for reporting
        ExtentReportManager.test.info(MarkupHelper.createCodeBlock(response.getBody().asString(), CodeLanguage.JSON));

        // Basic status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "UPDATE User - Status Code Validation");

        // JSON Schema validation
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));

        response.then().assertThat()
            .statusCode(200) 
            .and()
            .body("id", equalTo(userId)) 
            .body("name", equalTo(userPayload.getName())) 
            .body("email", equalTo(userPayload.getEmail())) 
            .body("gender", equalTo(userPayload.getGender()))
            .body("status", equalTo(userPayload.getStatus()))
            .body("name", notNullValue()) 
            .body("email", notNullValue()) 
            .body("email", containsString("@")) 
            .body("status", anyOf(equalTo("active"), equalTo("inactive"))) 
            .body("id", instanceOf(Integer.class)) 
            .body("name", instanceOf(String.class)) 
            .body("email", instanceOf(String.class));
        
        // Logging success in the Extent Report
        ExtentReportManager.test.log(Status.PASS, "User updated successfully with ID: " + userId);
    }
}
