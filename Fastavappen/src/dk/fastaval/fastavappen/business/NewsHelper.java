package dk.fastaval.fastavappen.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import dk.fastaval.fastavappen.Constants;
import dk.fastaval.fastavappen.data.NewsData;

public class NewsHelper {

	private Context mContext;

	public NewsHelper(Context context) {
		mContext = context;
	}
	
	public ArrayList<NewsData> getNewsData() throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		Type listOfProgramActivity = new TypeToken<ArrayList<NewsData>>() {}.getType();
		
		return gson.fromJson(readNewsFile(), listOfProgramActivity);
	}

	private String readNewsFile() throws IOException {
		// TODO: Remove before ship!
		FileInputStream fis = null;
		try {
			fis = mContext.openFileInput(Constants.FASTA_NEWS_FILE);
		} catch (FileNotFoundException e) {
			if (MoveFileFromAssets(Constants.FASTA_NEWS_FILE))
				fis = mContext.openFileInput(Constants.FASTA_NEWS_FILE);
			else
				throw new FileNotFoundException();
		}

		String content = "";

		byte[] input = new byte[fis.available()];
		while (fis.read(input) != -1) {
			content += new String(input);
		}

		return content;
	}

	private boolean MoveFileFromAssets(String FileName) {
		AssetManager assetManager = mContext.getAssets();

		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = assetManager.open(FileName);
			out = mContext.openFileOutput(FileName, Context.MODE_PRIVATE);

			copyFile(in, out);

			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private void copyFile(InputStream in, FileOutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
}
