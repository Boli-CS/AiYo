package com.hack.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.people.User;




/**
 * 核心API
 * @author lee
 *
 */
public class Hack {
	public static final String KEY_OAUTH_TOKEN = "flickrj-android-oauthToken"; //$NON-NLS-1$
	public static final String KEY_TOKEN_SECRET = "flickrj-android-tokenSecret"; //$NON-NLS-1$
	public static final String KEY_USER_NAME = "flickrj-android-userName"; //$NON-NLS-1$
	public static final String KEY_USER_ID = "flickrj-android-userId"; //$NON-NLS-1$
	public static final String PREFS_NAME = "flickrj-android-sample-pref"; //$NON-NLS-1$

	
	/**
	 * 获取flickr用户
	 * @param mContext
	 * @return
	 */
	public static User getUser(Context mContext){
		return getOAuthToken(mContext).getUser();
	}
	
	public static OAuth getOAuthToken(Context mContext) {
	   SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	   String oauthTokenString = settings.getString(KEY_OAUTH_TOKEN, null);
	   String tokenSecret = settings.getString(KEY_TOKEN_SECRET, null);
	   if (oauthTokenString == null && tokenSecret == null) {
	   	return null;
	   }
	   OAuth oauth = new OAuth();
	   String userName = settings.getString(KEY_USER_NAME, null);
	   String userId = settings.getString(KEY_USER_ID, null);
	   if (userId != null) {
	   	User user = new User();
	   	user.setUsername(userName);
	   	user.setId(userId);
	   	oauth.setUser(user);
	   }
	   OAuthToken oauthToken = new OAuthToken();
	   oauth.setToken(oauthToken);
	   oauthToken.setOauthToken(oauthTokenString);
	   oauthToken.setOauthTokenSecret(tokenSecret);
	   return oauth;
   }
}
