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

import nu.nmmm.android.fractal.*;
import nu.nmmm.android.fractal.calculator.FCalculatorFactory;
import nu.nmmm.android.fractal.color.FColorFactory;

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
		_surface.setFractalColor(type);
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
		Memento m = MementoFactory.getInstance(tid);
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
		case 0:		_setFractalType(FCalculatorFactory.TYPE_MANDELBROT);					break;
		case 1:		_setFractalType(FCalculatorFactory.TYPE_BURNINGSHIP);					break;
		case 2:		_setFractalType(FCalculatorFactory.TYPE_PERPENDICULAR_BURNINGSHIP);	break;
		case 3:		_setFractalType(FCalculatorFactory.TYPE_PERPENDICULAR_MANDELBROT);	break;
		
		default:	_setFractalType(FCalculatorFactory.TYPE_MANDELBROT);
		}
		
		switch(fractalColor){		
		case FColorFactory.COLOR_STANDARD:		_setFColor(FColorFactory.COLOR_STANDARD);	break;
		case FColorFactory.COLOR_COSMOS:		_setFColor(FColorFactory.COLOR_COSMOS);		break;
		case FColorFactory.COLOR_REVERSE:		_setFColor(FColorFactory.COLOR_REVERSE);	break;
		case FColorFactory.COLOR_CYCLIC:		_setFColor(FColorFactory.COLOR_CYCLIC);		break;
		case FColorFactory.COLOR_RETRO:			_setFColor(FColorFactory.COLOR_RETRO);		break;
		
		case FColorFactory.COLOR_FIRE:			_setFColor(FColorFactory.COLOR_FIRE);		break;
		case FColorFactory.COLOR_ICE:			_setFColor(FColorFactory.COLOR_ICE);		break;
		case FColorFactory.COLOR_TOURQUOISE:	_setFColor(FColorFactory.COLOR_TOURQUOISE);	break;
		
		case FColorFactory.COLOR_NONE:			_setFColor(FColorFactory.COLOR_NONE);		break;
		
		default:								_setFColor(FColorFactory.COLOR_STANDARD);
		}

		_setPixelDebugPreview(pixelPreview);

		_enableRefresh = true;

		_refresh();
	}
	
	public boolean checkMenu(int id){
		switch(id){
		
		// check type
		
		case R.id.m_fractal_type_mandelbrot:
			_setFractalType(FCalculatorFactory.TYPE_MANDELBROT);
			return true;
			
		case R.id.m_fractal_type_bs:
			_setFractalType(FCalculatorFactory.TYPE_BURNINGSHIP);
			return true;

		case R.id.m_fractal_type_pbs:
			_setFractalType(FCalculatorFactory.TYPE_PERPENDICULAR_BURNINGSHIP);
			return true;
		
		case R.id.m_fractal_type_pm:
			_setFractalType(FCalculatorFactory.TYPE_PERPENDICULAR_MANDELBROT);
			return true;
		
		// check colors
		
		case R.id.m_fractal_colors_standard:
			_setFColor( FColorFactory.COLOR_STANDARD );
			return true;

		case R.id.m_fractal_colors_cosmos:
			_setFColor( FColorFactory.COLOR_COSMOS );
			return true;

		case R.id.m_fractal_colors_reverse:
			_setFColor( FColorFactory.COLOR_REVERSE );
			return true;

		case R.id.m_fractal_colors_cyclic:
			_setFColor( FColorFactory.COLOR_CYCLIC );
			return true;

		case R.id.m_fractal_colors_retro:
			_setFColor( FColorFactory.COLOR_RETRO );
			return true;

		case R.id.m_fractal_colors_fire:
			_setFColor( FColorFactory.COLOR_FIRE );
			return true;

		case R.id.m_fractal_colors_ice:
			_setFColor( FColorFactory.COLOR_ICE );
			return true;

		case R.id.m_fractal_colors_turquoise:
			_setFColor( FColorFactory.COLOR_TOURQUOISE );
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
		case R.id.m_MB_BIG_PICTURE								: return MementoFactory.MB_BIG_PICTURE;
		case R.id.m_MB_BULB_MANDELBROT							: return MementoFactory.MB_BULB_MANDELBROT;
		case R.id.m_MB_ELEPHANT_VALLEY							: return MementoFactory.MB_ELEPHANT_VALLEY;
		case R.id.m_MB_SEAHORSE_VALLEY							: return MementoFactory.MB_SEAHORSE_VALLEY;
		case R.id.m_MB_TRIPPLE_SPIRAL							: return MementoFactory.MB_TRIPPLE_SPIRAL;
		case R.id.m_MB_IMPERIAL_ORB_VALLEY						: return MementoFactory.MB_IMPERIAL_ORB_VALLEY;
		
		case R.id.m_MB_MICRO_MANDELBROT							: return MementoFactory.MB_MICRO_MANDELBROT;
		case R.id.m_MB_MICRO_MANDELBROT_WITH_ANTENNA			: return MementoFactory.MB_MICRO_MANDELBROT_WITH_ANTENNA;

		// ====================================
			
		case R.id.m_BS_BIG_PICTURE								: return MementoFactory.BS_BIG_PICTURE;
		case R.id.m_BS_SHIP_IN_ARMADA							: return MementoFactory.BS_SHIP_IN_ARMADA;
		case R.id.m_BS_MYSTERIOUS_LADY							: return MementoFactory.BS_MYSTERIOUS_LADY;
		case R.id.m_BS_HIDDEN_TREASURE1							: return MementoFactory.BS_HIDDEN_TREASURE1;
		case R.id.m_BS_HIDDEN_FOREST1							: return MementoFactory.BS_HIDDEN_FOREST1;
		case R.id.m_BS_HIDDEN_TREASURE2							: return MementoFactory.BS_HIDDEN_TREASURE2;
		case R.id.m_BS_OVALS									: return MementoFactory.BS_OVALS;
		
		case R.id.m_BS_HIDDEN_FOREST2							: return MementoFactory.BS_HIDDEN_FOREST2;
		
		case R.id.m_BS_FLOWER1									: return MementoFactory.BS_FLOWER1;
		case R.id.m_BS_FLOWER2									: return MementoFactory.BS_FLOWER2;
		case R.id.m_BS_FLOWER3									: return MementoFactory.BS_FLOWER3;
		case R.id.m_BS_FLOWER4									: return MementoFactory.BS_FLOWER4;
		case R.id.m_BS_MICROSHIP								: return MementoFactory.BS_MICROSHIP;

		// ====================================
		
		case R.id.m_PBS_BIG_PICTURE								: return MementoFactory.PBS_BIG_PICTURE;
		case R.id.m_PBS_FIRST_IFS_TREE_CARDIOUD					: return MementoFactory.PBS_FIRST_IFS_TREE_CARDIOUD;
		case R.id.m_PBS_RHOMBUS_INSIDE_FIRST_CARDIOUD			: return MementoFactory.PBS_RHOMBUS_INSIDE_FIRST_CARDIOUD;
		case R.id.m_PBS_COMMON_IFS_TREE_CARDIOUD				: return MementoFactory.PBS_COMMON_IFS_TREE_CARDIOUD;
		case R.id.m_PBS_SPIRAL_GALAXY							: return MementoFactory.PBS_SPIRAL_GALAXY;
		case R.id.m_PBS_HUMANOID_CREATURE_THE_ALIEN				: return MementoFactory.PBS_HUMANOID_CREATURE_THE_ALIEN;
		case R.id.m_PBS_HUMANOID_CREATURE_THE_MINOTAUR			: return MementoFactory.PBS_HUMANOID_CREATURE_THE_MINOTAUR;
		case R.id.m_PBS_HUMANOID_CREATURE_THE_SKULL				: return MementoFactory.PBS_HUMANOID_CREATURE_THE_SKULL;
		case R.id.m_PBS_BUTTERFLIES_BIG_PICTURE					: return MementoFactory.PBS_BUTTERFLIES_BIG_PICTURE;
		case R.id.m_PBS_BUTTERFLY								: return MementoFactory.PBS_BUTTERFLY;
		case R.id.m_PBS_APOLONIAN_GASKET						: return MementoFactory.PBS_APOLONIAN_GASKET;
		case R.id.m_PBS_SIERPINSKI								: return MementoFactory.PBS_SIERPINSKI;
		case R.id.m_PBS_IFS_SQUARE								: return MementoFactory.PBS_IFS_SQUARE;
		case R.id.m_PBS_BAOBAB									: return MementoFactory.PBS_BAOBAB;

		case R.id.m_PBS_MICRO_PBS								: return MementoFactory.PBS_MICRO_PBS;
		case R.id.m_PBS_HIDDEN_FLAMES							: return MementoFactory.PBS_HIDDEN_FLAMES;
		case R.id.m_PBS_GATE									: return MementoFactory.PBS_GATE;
		case R.id.m_PBS_ARMADA									: return MementoFactory.PBS_ARMADA;
		case R.id.m_PBS_MIRROR									: return MementoFactory.PBS_MIRROR;
		case R.id.m_PBS_PENDANT									: return MementoFactory.PBS_PENDANT;

		case R.id.m_PBS_SWIRL									: return MementoFactory.PBS_SWIRL;
		case R.id.m_PBS_MINI_PM									: return MementoFactory.PBS_MINI_PM;
		case R.id.m_PBS_CARDIOID_PM								: return MementoFactory.PBS_CARDIOID_PM;
		case R.id.m_PBS_GRID_PM									: return MementoFactory.PBS_GRID_PM;
		case R.id.m_PBS_MASQUERADE								: return MementoFactory.PBS_MASQUERADE;
		case R.id.m_PBS_LE_ROI_SOLEIL							: return MementoFactory.PBS_LE_ROI_SOLEIL;
		
		// ====================================
		
		case R.id.m_PM_BIG_PICTURE								: return MementoFactory.PM_BIG_PICTURE;
		case R.id.m_PM_DRAKULA									: return MementoFactory.PM_DRAKULA;
		case R.id.m_PM_SQUID									: return MementoFactory.PM_SQUID;
		case R.id.m_PM_GROTESQUE								: return MementoFactory.PM_GROTESQUE;
		case R.id.m_PM_RANDOM_SHAPE								: return MementoFactory.PM_RANDOM_SHAPE;
		case R.id.m_PM_SKULL_VALLEY								: return MementoFactory.PM_SKULL_VALLEY;
		
		case R.id.m_PM_BLOB										: return MementoFactory.PM_BLOB;
		case R.id.m_PM_CHAINS									: return MementoFactory.PM_CHAINS;
		case R.id.m_PM_VISAGE_OF_WAR							: return MementoFactory.PM_VISAGE_OF_WAR;
		case R.id.m_PM_FIRE										: return MementoFactory.PM_FIRE;
		case R.id.m_PM_CARDIOID									: return MementoFactory.PM_CARDIOID;
		case R.id.m_PM_SWIRL									: return MementoFactory.PM_SWIRL;

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
