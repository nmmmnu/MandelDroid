package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import nu.nmmm.android.mandelbrot.*;

public class MainActivity extends Activity {
	private MDView _surface;
	private MyMenuActions _menu;
	private boolean _hasMenuButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_setFullScreen();
		
		this._hasMenuButton = _hasMenuButton();
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		
		FractalCalculator fractalCalculator = new FractalCalculatorMandelbrot(FractalCalculatorMandelbrot.TYPE_CLASSIC, 256);
		
	    FColor fractalColor = new FColorStandard();

		this._surface = new MDView(this, width, height, fractalCalculator, fractalColor);
		
		setContentView(_surface);
		
		this._menu = new MyMenuActions(getApplicationContext(), _surface);

		_refreshFromPreferences();
	}
	
	private void _setFullScreen(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
	}
	
	@SuppressLint("NewApi")
	private boolean _hasMenuButton(){
		if (Build.VERSION.SDK_INT >= 14)
			return ViewConfiguration.get(this).hasPermanentMenuKey();
		
		return true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		_surface.stopThread();
	}

	@Override
	protected void onResume() {
		super.onResume();

		_surface.startThread();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed(){
		if (_hasMenuButton == false){
			openOptionsMenu();
			return;
		}

		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch(id){
		case R.id.m_settings:
			startActivityForResult(new Intent(getBaseContext(), PreferencesActivity.class), R.id.m_settings);
			
			return true;
		}
		
		if ( _menu.checkMenu(id))
			return true;
		
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
		switch (requestCode){
		case R.id.m_settings:
			_refreshFromPreferences();
			break;
			
		}
	}
	
	private static int __str2int(String s, int def){
		if (s == null)
			return def;
		
		try{
			return Integer.parseInt(s);
		}catch(NumberFormatException e){
			return def;
		}
	}
	
	private void _refreshFromPreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		int maxIterations = __str2int( prefs.getString("max_iterations", "256"), 256);
		int fractalType = __str2int( prefs.getString("fractal_type", "0"), 0 );
		int fractalColor = __str2int( prefs.getString("fractal_color", "0"), 0 );
		boolean pixelPreview = prefs.getBoolean("pixel_preview", false);
		
		_menu.checkPref(fractalType, fractalColor, maxIterations, pixelPreview);
	}
	
}
