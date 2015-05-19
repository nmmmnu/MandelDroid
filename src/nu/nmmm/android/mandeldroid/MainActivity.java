package nu.nmmm.android.mandeldroid;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import nu.nmmm.android.mandelbrot.*;

public class MainActivity extends Activity {
	final private static String FILE_PREFIX = "mandeldroid";

	private MDView _surface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_setFullScreen();
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		
	    FColor fractalColor;
	    
	    fractalColor = new FColorStandard();

		this._surface = new MDView(this, width, height, fractalColor);
		
		setContentView(_surface); 

		_refreshFromPreferences();
	}
	
	private void _setFullScreen(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch(id){
		case R.id.m_fractal_type_mandelbrot:
			_surface.setFractalType(FractalCalculatorMandelbrot.TYPE_CLASSIC);
			
			_surface.stopThread();
			_surface.startThread();

			break;
			
		case R.id.m_fractal_type_bs:
			_surface.setFractalType(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP);
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_type_pbs:
			_surface.setFractalType(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR);
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_colors_standard:
			_surface.setFractalColor(new FColorStandard());
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_colors_cosmos:
			_surface.setFractalColor(new FColorCosmosNew());
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_colors_none:
			_surface.setFractalColor(new FColorNone());
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_reset:
			_menuResetCoordinates();
			return true;
		
		case R.id.m_places:
			return true;

		case R.id.m_settings:
			startActivityForResult(new Intent(getBaseContext(), PreferencesActivity.class), R.id.m_settings);
			
			return true;
	
		case R.id.m_save_image:
			_menuSaveImage();
			return true;

		case R.id.m_copy_coordinates:
			_menuCopyToClipboard();
			return true;

		}

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
		
		
		
		_surface.setFractalIterations(maxIterations);
		
		
		
		final int ft;
		
		switch(fractalType){
		case 1:
			ft = FractalCalculatorMandelbrot.TYPE_BURNINGSHIP;
			break;

		case 2:
			ft = FractalCalculatorMandelbrot.TYPE_PERPENDICULAR;
			break;

		case 0:
		default:
			ft = FractalCalculatorMandelbrot.TYPE_CLASSIC;
				
		}
		
		_surface.setFractalType(ft);
		
		
		
		final FColor fc;
		
		switch(fractalColor){		
		case 1:
			fc = new FColorCosmosNew();
			break;
		
		case 2:
			fc = new FColorNone();
			break;

		case 0:
		default:
			fc = new FColorStandard();
		}

		_surface.setFractalColor(fc);
		
		
		
		_surface.setPixelDebugPreview(pixelPreview);
		
		
		
		_surface.stopThread();
		_surface.startThread();
	}

	private void _menuResetCoordinates() {
		_surface.resetCoordinates();		
	}

	private void _menuCopyToClipboard() {
		ClipboardManager clipboard;
		clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

		double x[] = _surface.getCoordinate();
		
		String text = x[0] + " " + x[1] + " " + x[2]; 
		
		ClipData myClip = ClipData.newPlainText("text", text);
		clipboard.setPrimaryClip(myClip);
		
		_toaster(getString(R.string.t_copy_to_clipboard_ok));		
	}

	private void _menuSaveImage(){
		try{
			File dir = Environment.getExternalStorageDirectory();
			
			File f = File.createTempFile(FILE_PREFIX, ".png", dir);

			_surface.saveToFile(f);
			
			_toaster(getString(R.string.t_save_image_ok) + f.getName());
		}catch(IOException e){
			_toaster(getString(R.string.t_save_image_err));
		}
	}
	
	private void _toaster(String msg){
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
}
