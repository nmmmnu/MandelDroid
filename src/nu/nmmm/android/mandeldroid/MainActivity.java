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
	
	private void _setFColor(int type){
		_surface.setFractalColor( FColorFactory.getInstance(type) );
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
			_setFColor( FColorFactory.COLOR_STANDARD );
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_colors_cosmos:
			_setFColor( FColorFactory.COLOR_COSMOS );
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_colors_retro:
			_setFColor( FColorFactory.COLOR_RETRO );
			
			_surface.stopThread();
			_surface.startThread();

			break;

		case R.id.m_fractal_colors_none:
			_setFColor( FColorFactory.COLOR_NONE );
			
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

		int tid = _checkTouristicPlace(id);
		if (tid > 0){
			_menuSetMemento(tid);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private int _checkTouristicPlace(int id){
		switch(id){
		case R.id.m_MB_BIG_PICTURE								: return MandelbrotMementoFactory.MB_BIG_PICTURE;
		case R.id.m_MB_BULB_MANDELBROT							: return MandelbrotMementoFactory.MB_BULB_MANDELBROT;
		case R.id.m_MB_ELEPHANT_VALLEY							: return MandelbrotMementoFactory.MB_ELEPHANT_VALLEY;
		case R.id.m_MB_SEAHORSE_VALLEY							: return MandelbrotMementoFactory.MB_SEAHORSE_VALLEY;
		case R.id.m_MB_TRIPPLE_SPIRAL							: return MandelbrotMementoFactory.MB_TRIPPLE_SPIRAL;
		case R.id.m_MB_IMPERIAL_ORB_VALLEY						: return MandelbrotMementoFactory.MB_IMPERIAL_ORB_VALLEY;
		case R.id.m_BS_BIG_PICTURE								: return MandelbrotMementoFactory.BS_BIG_PICTURE;
		case R.id.m_BS_SHIP_IN_ARMADA							: return MandelbrotMementoFactory.BS_SHIP_IN_ARMADA;
		case R.id.m_BS_MYSTERIOUS_LADY							: return MandelbrotMementoFactory.BS_MYSTERIOUS_LADY;
		case R.id.m_BS_HIDDEN_TREASURE1							: return MandelbrotMementoFactory.BS_HIDDEN_TREASURE1;
		case R.id.m_BS_HIDDEN_FOREST							: return MandelbrotMementoFactory.BS_HIDDEN_FOREST;
		case R.id.m_BS_HIDDEN_TREASURE2							: return MandelbrotMementoFactory.BS_HIDDEN_TREASURE2;
		case R.id.m_BS_OVALS									: return MandelbrotMementoFactory.BS_OVALS;
		case R.id.m_PBS_BIG_PICTURE								: return MandelbrotMementoFactory.PBS_BIG_PICTURE;
		case R.id.m_PBS_FIRST_IFS_TREE_CARDIOUD					: return MandelbrotMementoFactory.PBS_FIRST_IFS_TREE_CARDIOUD;
		case R.id.m_PBS_RHOMBUS_INSIDE_FIRST_IFS_TREE_CARDIOUD	: return MandelbrotMementoFactory.PBS_RHOMBUS_INSIDE_FIRST_IFS_TREE_CARDIOUD;
		case R.id.m_PBS_COMMON_IFS_TREE_CARDIOUD				: return MandelbrotMementoFactory.PBS_COMMON_IFS_TREE_CARDIOUD;
		case R.id.m_PBS_SPIRAL_GALAXY							: return MandelbrotMementoFactory.PBS_SPIRAL_GALAXY;
		case R.id.m_PBS_HUMANOID_CREATURE_THE_ALIEN				: return MandelbrotMementoFactory.PBS_HUMANOID_CREATURE_THE_ALIEN;
		case R.id.m_PBS_HUMANOID_CREATURE_THE_MINOTAUR			: return MandelbrotMementoFactory.PBS_HUMANOID_CREATURE_THE_MINOTAUR;
		case R.id.m_PBS_HUMANOID_CREATURE_THE_SKULL				: return MandelbrotMementoFactory.PBS_HUMANOID_CREATURE_THE_SKULL;
		case R.id.m_PBS_BUTTERFLIES_BIG_PICTURE					: return MandelbrotMementoFactory.PBS_BUTTERFLIES_BIG_PICTURE;
		case R.id.m_PBS_BUTTERFLY								: return MandelbrotMementoFactory.PBS_BUTTERFLY;
		case R.id.m_PBS_APOLONIAN_GASKET						: return MandelbrotMementoFactory.PBS_APOLONIAN_GASKET;
		case R.id.m_PBS_SIERPINSKI								: return MandelbrotMementoFactory.PBS_SIERPINSKI;
		case R.id.m_PBS_IFS_SQUARE								: return MandelbrotMementoFactory.PBS_IFS_SQUARE;
		case R.id.m_PBS_BAOBAB									: return MandelbrotMementoFactory.PBS_BAOBAB;
		}
		
		return 0;
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
		
		
		
		switch(fractalColor){		
		case 1:
			_setFColor(FColorFactory.COLOR_COSMOS);

			break;
		
		case 2:
			_setFColor(FColorFactory.COLOR_RETRO);
			break;

		case 100:
			_setFColor(FColorFactory.COLOR_NONE);
			break;

		case 0:
		default:
			_setFColor(FColorFactory.COLOR_STANDARD);
		}
		
		
		
		_surface.setPixelDebugPreview(pixelPreview);
		
		
		
		_surface.stopThread();
		_surface.startThread();
	}

	private void _menuResetCoordinates() {
		_surface.resetCoordinates();		
	}
	
	private void _menuSetMemento(int tid){
		Memento m = MandelbrotMementoFactory.getInstance(tid);
		_surface.setFractalMemento(m);

		_surface.stopThread();
		_surface.startThread();
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
			
			_toaster(getString(R.string.t_save_image_ok) + " " + f.getName());
		}catch(IOException e){
			_toaster(getString(R.string.t_save_image_err));
		}
	}
	
	private void _toaster(String msg){
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
}
