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
    	
    	response.then().log().all();
    	Assert.assertEquals(response.getStatusCode(), 201);
    
    	user_id = response.jsonPath().getString("id");

    	logger.info("****** User Created *****");
        // Print the extracted ID
        System.out.println("User ID: " + user_id);
    }
    
    @Test(priority=2)
    public void testGetUserByid()
    {
    	Response response=UserEndPoints.readUser(this.user_id);
    	
    	response.then().log().all();
    	Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    @Test(priority=3)
    public void testUpdateUserByid()
    {
    	//update the data
    	 userpayload.setEmail(faker.internet().emailAddress()); // For email
         userpayload.setGender(faker.options().option("male", "female"));
         
         
    	Response response=UserEndPoints.updateUser(this.user_id,userpayload);
    	response.then().log().body();
    	Assert.assertEquals(response.getStatusCode(), 200);
    	
    	// checking data after update
    	Response responseAfterUpdate=UserEndPoints.readUser(this.user_id);
    	Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
    	
    }
    
    
    
}
