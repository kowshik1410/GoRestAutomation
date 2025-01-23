package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import static org.hamcrest.Matchers.*;
public class TestGetUserById {

    Logger logger;

    @BeforeClass
    public void setupData() {
        logger = LogManager.getLogger(this.getClass());
    }

    @Test
    public void testGetUserById(ITestContext context) {
        ExtentReportManager.test.log(Status.INFO, "Starting testGetUserById...");
        
        int userId = (int) context.getAttribute("user_id");
        logger.info("Fetching User Info for ID: " + userId);
        
        Response response = UserEndPoints.readUser(userId);
        response.then().log().body();

        // Log the response body for reporting
        ExtentReportManager.test.info(MarkupHelper.createCodeBlock(response.getBody().asString(), CodeLanguage.JSON));

        // Basic status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "GET User - Status Code Validation");

        // Hamcrest assertions
        response.then().assertThat()
            .statusCode(200) 
            .and()
            .body("id", equalTo(userId)) 
            .body("name", notNullValue()) 
            .body("email", notNullValue()) 
            .body("email", containsString("@"))
            .body("gender", anyOf(equalTo("male"), equalTo("female"))) 
            .body("status", anyOf(equalTo("active"), equalTo("inactive"))) 
            .body("id", instanceOf(Integer.class)) 
            .body("name", instanceOf(String.class)) 
            .body("email", instanceOf(String.class)); 

        // Logging success in the Extent Report
        ExtentReportManager.test.log(Status.PASS, "User fetched successfully with ID: " + userId);
    }

}
