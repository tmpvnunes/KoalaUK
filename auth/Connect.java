package auth;

import java.util.List;
import java.util.Scanner;

import org.jinstagram.Instagram;
import org.jinstagram.InstagramClient;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;

public class Connect {
	
	static UserInfo userInfo = null;
	static String userID=null;
	static InstagramClient instagram = null;
	
	
	private static InstagramService createService()
	{
		String username="your username";
		String password="your password";	
		
		InstagramService service = new InstagramAuthService()
				.apiKey(username)
				.apiSecret(password)
				.callback("http://localhost:3000/auth")
				.scope("basic public_content likes comments follower_list relationships")
				.build();
		
		return service;
	}
	
	private static InstagramClient getInstaClient(Token token) throws InstagramException
	{
		instagram = new Instagram(token);

		userInfo = instagram.getCurrentUserInfo();
		userID = userInfo.getData().getId();
		System.out.println("***** User Info ******");
		System.out.println("ID : " + userID);
		System.out.println("Username : " + userInfo.getData().getUsername());
		System.out.println("First Name : " + userInfo.getData().getFirstName());
		System.out.println("Last Name : " + userInfo.getData().getLastName());
		System.out.println("Website : " + userInfo.getData().getWebsite());
		System.out.println("API Limit Status: " + userInfo.getAPILimitStatus());
		System.out.println("API Remaining Limit St: " + userInfo.getRemainingLimitStatus());
		System.out.println("Followed by: " + userInfo.getData().getCounts().getFollowedBy());
		System.out.println("Following: " + userInfo.getData().getCounts().getFollows());
		System.out.println("");
		System.out.println("***********************\n");
		
		return instagram;
	}
	
	private static void readMedia(InstagramClient instagram) throws InstagramException
	{
		MediaFeed mediaFeed = instagram.getRecentMediaFeed(userInfo.getData().getId());
		List<MediaFeedData> listMedia = mediaFeed.getData();
		
		for (MediaFeedData m : listMedia)
		{
			System.out.println("ID: " +  m.getId());
			System.out.println("Title: " +  m.getCaption());
			System.out.println("Likes: " +  m.getLikes());
			System.out.println();
		}

	}
	
	private static void userFollowList() throws InstagramException 
	{
				//will fail
				UserFeed feed = instagram.getUserFollowList(userID);
				List<UserFeedData> users = feed.getUserList();
				
				for(UserFeedData listUser : users)
				{
					System.out.println(listUser.getId());
					System.out.println(listUser.getFullName());
				}
	}
	
	public static void InstaAPP () throws InstagramException
	{
		
		System.out.println("antes de criar o serviço");
		InstagramService service = createService();
		System.out.println("depois de serviço");
				
		String authUrl = service.getAuthorizationUrl();
		System.out.println("URL criado");
		System.out.println("** Instagram Authorization ** \n\n");		
		System.out.println("Copy & Paste the below Authorization URL in your browser...");		
		System.out.println("Authorization URL : " + authUrl);
		
		Scanner sc = new Scanner(System.in);

		String verifierCode;
		System.out.print("Your Verifier Code : ");
		verifierCode = sc.next();
		System.out.println("Codigo recebido");
		
		Verifier verifier = new Verifier(verifierCode);
		System.out.println("verifier criado");
		
		Token accessToken = service.getAccessToken(verifier);

		System.out.println("Access Token :: " + accessToken.getToken());
		
		instagram = getInstaClient(accessToken);
		
		readMedia(instagram);
		
		System.out.println("vamos aceder a lista de seguidores");
		userFollowList();
		System.out.println("lista de seguidores conseGUIDA");
		
	}
	
	

}
