package api.endpoints;


/*
 POST /public/v2/users	------------  Create a new user
GET /public/v2/users/6945473 -------  Get user details
PUT|PATCH /public/v2/users/6945473--  Update user details
DELETE /public/v2/users/6945473	----- Delete user

  
 */

// Maintaining the urls 
public class Routes {
	
	public static String base_url="https://gorest.co.in";
	
	//user model
	public static String post_url=base_url+"/public/v2/users";
	public static String get_url=base_url+"/public/v2/users/{userid}";
	public static String update_url=base_url+"/public/v2/users/{userid}";
	public static String delete_url=base_url+"/public/v2/users/{userid}";
	

}
