package nu.nmmm.android.mandeldroid;

import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.*;

import nu.nmmm.android.mandelbrot.*;
import nu.nmmm.android.mandelbrot.color.FColorFactory;

class MyMenuActions {
	final private static String FILE_PREFIX = "mandeldroid.";

	private Context _context;
	private MDView _surface;
	private SharedPreferences _prefs;

	private boolean _enableRefresh = true;
	
	public MyMenuActions(Context context, MDView surface) {
		this._context = context;
		this._surface = surface;
		
		this._prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	private void _setFractalType(int type){
		_surface.setFractalType(type);
		_prefsPutString("fractal_type", type);
		_refresh();
	}
	
	private void _setFColor(int type){
		_surface.setFractalColor( FColorFactory.getInstance(type) );
		_prefsPutString("fractal_color", type);
		_refresh();
	}

	private void _prefsPutString(String key, int value){
		Editor editor = _prefs.edit();
		editor.putString(key, "" + value);
		editor.commit();
	}
	
	private void _prefsPutBoolean(String key, boolean value){
		Editor editor = _prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private void _setIterations(int iterations){
		_surface.setFractalIterations(iterations);
		_prefsPutString("max_iterations", iterations);
		_refresh();
	}
	
	private void _setPixelDebugPreview(boolean pixelPreview){
		_surface.setPixelDebugPreview(pixelPreview);
		_prefsPutBoolean("pixel_preview", pixelPreview);
	}
	
	private void _resetCoordinates() {
		_surface.resetCoordinates();		
		_refresh();
	}

	private void _setMemento(int tid){
		Memento m = MandelbrotMementoFactory.getInstance(tid);
		_surface.setFractalMemento(m);
		_refresh();
	}

	private void _saveImage(){
		try{
			File dir = Environment.getExternalStorageDirectory();
			
			File f = File.createTempFile(FILE_PREFIX, ".png", dir);

			FileOutputStream out = new FileOutputStream(f);

			// The compression factor (100) is ignored for PNG
			_surface.getImage().compress(Bitmap.CompressFormat.PNG, 100, out); 
			
			out.close();
				
			_toaster(_context.getString(R.string.t_save_image_ok) + " " + f.getName());
		}catch(IOException e){
			_toaster(_context.getString(R.string.t_save_image_err));
		}
	}

	private void _setWallpaper(){
		try {
            WallpaperManager wm = WallpaperManager.getInstance(_context.getApplicationContext());
            wm.setBitmap(_surface.getImage());
			_toaster(_context.getString(R.string.t_set_wallpaper_ok));
		} catch (IOException e) {
			_toaster(_context.getString(R.string.t_set_wallpaper_err));
		}		
	}
	
	private void _copyToClipboard() {
		ClipboardManager clipboard;
		clipboard = (ClipboardManager) _context.getSystemService(Context.CLIPBOARD_SERVICE);

		double x[] = _surface.getCoordinate();
		
		String text = x[0] + " " + x[1] + " " + x[2]; 
		
		ClipData myClip = ClipData.newPlainText("text", text);
		clipboard.setPrimaryClip(myClip);
		
		_toaster(_context.getString(R.string.t_copy_to_clipboard_ok));		
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

	public void refreshFromPreferences() {
		int maxIterations		= __str2int( _prefs.getString("max_iterations", "256"), 256);
		int fractalType			= __str2int( _prefs.getString("fractal_type", "0"), 0 );
		int fractalColor		= __str2int( _prefs.getString("fractal_color", "0"), 0 );
		boolean pixelPreview = _prefs.getBoolean("pixel_preview", false);
		
		_enableRefresh = false;
		
		_setIterations(maxIterations);
		
		switch(fractalType){
		case 1:		_setFractalType(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP);		break;
		case 2:		_setFractalType(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR);	break;
		case 0:
		default:	_setFractalType(FractalCalculatorMandelbrot.TYPE_CLASSIC);
		}
		
		switch(fractalColor){		
		case 1:		_setFColor(FColorFactory.COLOR_COSMOS);		break;
		case 2:		_setFColor(FColorFactory.COLOR_RETRO);		break;
		case 3:		_setFColor(FColorFactory.COLOR_REVERSE);	break;
		case 4:		_setFColor(FColorFactory.COLOR_FIRE);		break;
		case 5:		_setFColor(FColorFactory.COLOR_ICE);		break;
		case 100:	_setFColor(FColorFactory.COLOR_NONE);		break;
		case 0:
		default:	_setFColor(FColorFactory.COLOR_STANDARD);
		}

		_setPixelDebugPreview(pixelPreview);

		_enableRefresh = true;

		_refresh();
	}
	
	public boolean checkMenu(int id){
		switch(id){
		
		// check type
		
		case R.id.m_fractal_type_mandelbrot:
			_setFractalType(FractalCalculatorMandelbrot.TYPE_CLASSIC);
			return true;
			
		case R.id.m_fractal_type_bs:
			_setFractalType(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP);
			return true;

		case R.id.m_fractal_type_pbs:
			_setFractalType(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR);
			return true;
		
		// check colors
		
		case R.id.m_fractal_colors_standard:
			_setFColor( FColorFactory.COLOR_STANDARD );
			return true;

		case R.id.m_fractal_colors_cosmos:
			_setFColor( FColorFactory.COLOR_COSMOS );
			return true;

		case R.id.m_fractal_colors_retro:
			_setFColor( FColorFactory.COLOR_RETRO );
			return true;

		case R.id.m_fractal_colors_reverse:
			_setFColor( FColorFactory.COLOR_REVERSE );
			return true;

		case R.id.m_fractal_colors_fire:
			_setFColor( FColorFactory.COLOR_FIRE );
			return true;

		case R.id.m_fractal_colors_ice:
			_setFColor( FColorFactory.COLOR_ICE );
			return true;

		case R.id.m_fractal_colors_none:
			_setFColor( FColorFactory.COLOR_NONE );
			return true;
			
		// check iterations
			
		case R.id.m_max_iterations_32:
			_setIterations(32);
			return true;

		case R.id.m_max_iterations_64:
			_setIterations(64);
			return true;

		case R.id.m_max_iterations_128:
			_setIterations(128);
			return true;

		case R.id.m_max_iterations_256:
			_setIterations(256);
			return true;

		case R.id.m_max_iterations_512:
			_setIterations(512);
			return true;

		case R.id.m_max_iterations_1024:
			_setIterations(1024);
			return true;

		case R.id.m_max_iterations_2048:
			_setIterations(2048);
			return true;

		// miscellaneous
			
		case R.id.m_reset:
			_resetCoordinates();
			return true;
		
		case R.id.m_places:
			// dummy menu item.
			return true;
			
		case R.id.m_save_image:
			_saveImage();
			return true;

		case R.id.m_set_wallpaper:
			_setWallpaper();
			return true;

		case R.id.m_copy_coordinates:
			_copyToClipboard();
			return true;

		}
		
		// check tourist places
		
		int tid = _getTouristicPlaceID(id);

		if (tid > 0){			
			_setMemento(tid);
			return true;
		}

		// nothing else to check...
		
		return false;
	}	

	private int _getTouristicPlaceID(int id){
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
	
	private void _refresh(){
		if (_enableRefresh)
			_surface.refresh();
	}
	
	private void _toaster(String msg){
		Toast.makeText(_context, msg, Toast.LENGTH_LONG).show();
	}
}