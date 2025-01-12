/*package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	public String user_id;
    Faker faker;
    User userpayload;
    
    public Logger logger;
    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userpayload = new User();

        // Setting the values using Faker
        userpayload.setName(faker.name().username()); // For username
        userpayload.setEmail(faker.internet().emailAddress()); // For email
        userpayload.setGender(faker.options().option("male", "female")); // For gender (random male/female)
        userpayload.setStatus(faker.options().option("active", "inactive")); // For status (random active/inactive)
        
        //logs
        
        logger=LogManager.getLogger(this.getClass());
    }
    
    @Test(priority=1)
    public void testPostUser()
    {
    	logger.info("****** Creating User *****");
    	Response response=UserEndPoints.createUser(userpayload);
    	
    	response.then().log().body();
    	Assert.assertEquals(response.getStatusCode(), 201);
    
    	user_id = response.jsonPath().getString("id");

    	logger.info("****** User Created *****");
    }
    
    @Test(priority=2)
    public void testGetUserByid()
    {
    	logger.info("****** Reading user information*****");
    	Response response=UserEndPoints.readUser(this.user_id);
    	
    	response.then().log().body();
    	Assert.assertEquals(response.getStatusCode(), 200);
    	logger.info("****** User info is Displayed *****");
    }
    
    @Test(priority=3)
    public void testUpdateUserByid()
    {
    	logger.info("****** Updating the User Fields*****");
    	//update the data
    	 userpayload.setEmail(faker.internet().emailAddress()); // For email
         userpayload.setGender(faker.options().option("male", "female"));
         
         
    	Response response=UserEndPoints.updateUser(this.user_id,userpayload);
    	response.then().log().body();
    	Assert.assertEquals(response.getStatusCode(), 200);
    	logger.info("****** User information updated *****");
    	// checking data after update
    	Response responseAfterUpdate=UserEndPoints.readUser(this.user_id);
    	Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
    }
    @Test(priority=4)
    public void testDeleteUserByid()
    {
    	logger.info("****** Deleting User*****");
    	Response response=UserEndPoints.deleteUser(this.user_id);
    	
    	response.then().log().body();
    	Assert.assertEquals(response.getStatusCode(), 204);
    	logger.info("****** User Deleted *****");
    }
}*/



package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserTests {

    public String userId;
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

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("Creating User...");
        Response response = UserEndPoints.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 201);
        userId = response.jsonPath().getString("id");
        logger.info("User Created: " + userId);

        Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));
    }

    @Test(priority = 2)
    public void testGetUserById() {
        logger.info("Fetching User Info...");
        Response response = UserEndPoints.readUser(userId);
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.jsonPath().getString("id"), userId);
        Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));
    }

    @Test(priority = 3)
    public void testUpdateUserById() {
        logger.info("Updating User...");
        userPayload.setEmail(faker.internet().emailAddress());
        Response response = UserEndPoints.updateUser(userId, userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("email"), userPayload.getEmail());
    }

    @Test(priority = 4)
    public void testDeleteUserById() {
        logger.info("Deleting User...");
        Response response = UserEndPoints.deleteUser(userId);
        Assert.assertEquals(response.getStatusCode(), 204);
        logger.info("User Deleted.");
    }
}

