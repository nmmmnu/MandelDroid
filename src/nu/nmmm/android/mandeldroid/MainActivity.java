package nu.nmmm.android.mandeldroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private MDView _surface;
	
	private Thread _thread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point(); 
		display.getSize(size);
				
		this._surface = new MDView(this, size);
		
		setContentView(_surface);
		
		_thread = new Thread(_surface);
		_thread.start();
		Log.v("main", "Thread supposedly started..." );
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		//_surface.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		//_surface.resume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
