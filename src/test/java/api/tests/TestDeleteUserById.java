package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

import com.aventstack.extentreports.Status;

import static org.hamcrest.Matchers.*;

public class TestDeleteUserById {

    Logger logger;
    
   
    public TestDeleteUserById() {
        logger = LogManager.getLogger(this.getClass());
    }

    @Test
    public void testDeleteUserById(ITestContext context) {
        ExtentReportManager.test.log(Status.INFO, "Starting testDeleteUserById...");
        logger.info("Deleting User...");

        int userId = (int) context.getAttribute("user_id");
        Response response = UserEndPoints.deleteUser(userId);
        response.then().log().body();

        // Log response in the Extent Report
        ExtentReportManager.test.log(Status.INFO, "Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 204, "DELETE User - Status Code Validation");

        // Hamcrest assertions
        response.then().assertThat()
            .statusCode(204) // Status code is 204 (No Content)
            .and()
            .body(emptyOrNullString());
        
        logger.info("User deleted successfully with ID: " + userId);
        ExtentReportManager.test.log(Status.PASS, "User deleted successfully with ID: " + userId);
    }

}
