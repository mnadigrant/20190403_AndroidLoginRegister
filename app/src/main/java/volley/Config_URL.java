package volley;

//This class is for storing all URLs as a model of URLs

public class Config_URL
{
	private static String base_URL = "http://192.168.31.212:5000/";
	public static String URL_LOGIN = base_URL+"todo/api/v1.0/users/";
	public static String URL_REGISTER = base_URL+"todo/api/v1.0/putusers";
	public static String URL_TASKS = base_URL+"todo/api/v1.0/tasks";
}
