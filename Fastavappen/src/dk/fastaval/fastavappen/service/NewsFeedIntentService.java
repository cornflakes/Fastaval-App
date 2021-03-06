package dk.fastaval.fastavappen.service;

import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.google.gson.JsonSyntaxException;

import dk.fastaval.fastavappen.Constants;
import dk.fastaval.fastavappen.business.NewsHelper;

public class NewsFeedIntentService extends IntentService {
	
	public NewsFeedIntentService() {
		super("NewsFeedIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// Get extras
		Bundle extras = intent.getExtras();

		if (extras != null) {

			// Get messenger
			Messenger messenger = (Messenger) extras.get(Constants.MESSENGER_EXTRA);

			// Obtain message
			Message msg = Message.obtain();
			NewsHelper NH = new NewsHelper(this);

			try {
				msg.obj = NH.getNewsData();
				msg.what = 0;
				messenger.send(msg);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (android.os.RemoteException e1) {
				Log.w(getClass().getName(), "Exception sending message", e1);
			}
		}
	}
}
