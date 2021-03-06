package dk.fastaval.fastavappen.presentation;

import java.io.IOException;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.google.gson.JsonSyntaxException;

import dk.fastaval.fastavappen.Constants;
import dk.fastaval.fastavappen.R;
import dk.fastaval.fastavappen.R.id;
import dk.fastaval.fastavappen.R.layout;
import dk.fastaval.fastavappen.business.ProgramHelper;
import dk.fastaval.fastavappen.data.ProgramActivityData;

public class ActivityViewActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		ProgramHelper PH = new ProgramHelper(this);
		ProgramActivityData PAD = null;
		try {
			PAD = PH.getProgramActivityData(getIntent().getIntExtra(Constants.ACTIVITY_ID, 0));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TextView title = (TextView) findViewById(R.id.tv_activity_title);
		TextView auther = (TextView) findViewById(R.id.tv_activity_auther);
		TextView content = (TextView) findViewById(R.id.tv_activity_content);
		
		title.setText(PAD.info.title_da);
		if(PAD.info.author.size() > 1) {
			String authers = PAD.info.author.get(0);
			for(int i = 1; i < PAD.info.author.size(); i++)
				authers += " & " + PAD.info.author.get(0);
		auther.setText(authers);
		} else 
			auther.setText(PAD.info.author.get(0));
		content.setText(Html.fromHtml(PAD.info.text_da));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
	   inflater.inflate(R.menu.menu, (com.actionbarsherlock.view.Menu) menu);
	   return super.onCreateOptionsMenu(menu);
	}
}
