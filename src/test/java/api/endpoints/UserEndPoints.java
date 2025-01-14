package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

// Created to perform  Create, Read, Update, Delete requests of the user API.  
public class UserEndPoints {
	
	private static final String TOKEN = "f862a1c5f12a1b107322e1e8f07fa14fcb1375f74203d7ca411ef2edcb2a03eb";
	
	public static Response createUser(User payload)
	{
		Response response=given()
			.header("Authorization", "Bearer " + TOKEN)	
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.post_url);
		return response;
		
	}

	public static Response readUser(String user_id)
	{
		ValidatableResponse validatableResponse = given()
		        .header("Authorization", "Bearer " + TOKEN)    
		        .pathParam("userid", user_id)
		    .when()
		        .get(Routes.get_url)
		    .then()
		        .contentType(ContentType.JSON);
		    
		    // Extract the response object from the validatable response
		    return validatableResponse.extract().response();
	}
	
	public static Response updateUser(String user_id,User payload)
	{
		Response response=given()
			.header("Authorization", "Bearer " + TOKEN)	
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
			.pathParam("userid", user_id)
		.when()
			.put(Routes.update_url);
		return response;
	}

	public static Response deleteUser(String user_id)
	{
		Response response=given()
			.header("Authorization", "Bearer " + TOKEN)	
			.pathParam("userid",user_id)
		.when()
			.delete(Routes.delete_url);
		return response;
	}

}
