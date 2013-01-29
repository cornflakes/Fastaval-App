package dk.fastaval.fastavappen.service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dk.fastaval.fastavappen.Constants;
import dk.fastaval.fastavappen.business.ProgramHelper;
import dk.fastaval.fastavappen.data.GroupRowData;
import dk.fastaval.fastavappen.data.ProgramActivityData;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class ProgramIntentService extends IntentService {
	
	private ArrayList<GroupRowData> groupItems;
	private ArrayList<ArrayList<ProgramActivityData>> childItems = new ArrayList<ArrayList<ProgramActivityData>>();

	public ProgramIntentService() {
		super("ProgramIntentService");
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
			ProgramHelper PH = new ProgramHelper(this);

			try {
				msg.obj = groupItems = PH.getTimeBlocks();
				
				msg.what = 0;
				messenger.send(msg);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (android.os.RemoteException e1) {
				Log.w(getClass().getName(), "Exception sending message", e1);
			}
			
			msg = Message.obtain();
			
			try {
				for(GroupRowData GRD : groupItems) {
					childItems.add(PH.getTimeBlocksElements(GRD));
				}
				
				msg.obj = childItems;
				
				msg.what = 1;
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
