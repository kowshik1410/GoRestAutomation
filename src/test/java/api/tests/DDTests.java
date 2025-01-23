package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userName,String useremail,String gender,String status) throws JsonProcessingException
	{
		User userPayload=new User();
		 ObjectMapper objectMapper = new ObjectMapper();
	     String userJson = objectMapper.writeValueAsString(userPayload);
		
		userPayload.setName(userName);
		userPayload.setEmail(useremail);
		userPayload.setGender(gender);
		userPayload.setStatus(status);
		
		Response response=UserEndPoints.createUser(userJson);
		Assert.assertEquals(response.getStatusCode(),201);
		
			
	}
	
	/*@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
			Response response=UserEndPoints.deleteUser(userName);
			Assert.assertEquals(response.getStatusCode(),200);	
	
	}
	*/
	
	
}