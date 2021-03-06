/**
 * 
 */
package com.hack.flikr;

import android.os.AsyncTask;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.hack.activity.BaseActivity;
import com.hack.core.Hack;

public class ImageUploadTask extends AsyncTask<OAuth, Void, String> {
	private BaseActivity activity;
	private byte[] data;
	private String imageName;
	private UploadMetaData metaData;
	public ImageUploadTask(BaseActivity activity,byte[] data,String imageName,UploadMetaData metaData) {
		this.activity = activity;
		this.data = data;
		this.imageName= imageName;
		this.metaData = metaData;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.makeToast("正在上传到Flickr...");
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(OAuth... params) {
		OAuth oauth = params[0];
		OAuthToken token = oauth.getToken();
		try {
			Flickr f = FlickrHelper.getInstance()
					.getFlickrAuthed(token.getOauthToken(), token.getOauthTokenSecret());
			return f.getUploader().upload(imageName, data, metaData);
		
		} catch (Exception e) {
			activity.makeToast(e.toString());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String string) {
		if (Double.valueOf(string)>116){
			activity.makeToast("上传成功");
			//Hack
			Hack.addNewPhoto(activity, string, "", Hack.galleryID);
		}else{
			activity.makeToast("上传失败，正在重试...");
		}
	}
	
	
}