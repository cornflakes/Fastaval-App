package dk.fastaval.fastavappen.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.SherlockFragment;

import dk.fastaval.fastavappen.Constants;
import dk.fastaval.fastavappen.R;
import dk.fastaval.fastavappen.data.NewsData;
import dk.fastaval.fastavappen.service.NewsFeedIntentService;
import dk.fastaval.fastavappen.util.NewsFeedBox;

public class MainFragment extends SherlockFragment {
	
	private ArrayList<NewsData> mNewsDataList;
	private ViewFlipper vf;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		vf = (ViewFlipper) getView().findViewById(R.id.vf_news_feed);

    	vf.setInAnimation(getActivity(), R.anim.in_from_right);
		vf.setOutAnimation(getActivity(), R.anim.out_to_left);
		
		vf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String url = mNewsDataList.get(vf.getDisplayedChild()).permalink;
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
		
	    // Initialize the messenger with the private handler
		Messenger messenger = new Messenger(mHandler);
		
		// Ready the intent with information for the service
	    Intent NewsFeedServiceIntent = new Intent(getActivity(), NewsFeedIntentService.class);
	    NewsFeedServiceIntent.putExtra(Constants.MESSENGER_EXTRA, messenger);
	    getActivity().startService(NewsFeedServiceIntent);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, container, false);
		return view;
	}
	
	public CharSequence getTitle() {
		return getText(R.string.title_main_page);
	}
	
	private final Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	mNewsDataList =  (ArrayList<NewsData>) msg.obj;
	    	for(NewsData ND : mNewsDataList) {
		    	NewsFeedBox NFB = new NewsFeedBox(getActivity(), null);
		    	NFB.setTitle(ND.title);
		    	NFB.setContent(Html.fromHtml(ND.content));
		    	
		    	SimpleDateFormat formatter; 
	            Date date = null; 
	            formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            try {
					date = (Date)formatter.parse(ND.date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            NFB.setDate(date.getTime());
		    	vf.addView(NFB);
	    	}
	    	vf.showNext();
	    	mHandler.postDelayed(NewsFeedTimer, Constants.NEWS_FEED_SLIDE_SPEED);
	    }
	};
	
	private Runnable NewsFeedTimer = new Runnable() {
		@Override
		public void run() {
			vf.showNext();
			mHandler.postDelayed(this, Constants.NEWS_FEED_SLIDE_SPEED);
		}
	};
}
