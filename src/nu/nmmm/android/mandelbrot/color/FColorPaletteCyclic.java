package nu.nmmm.android.mandelbrot.color;

import java.util.ArrayList;
import java.util.List;

public class FColorPaletteCyclic implements FColor{
	final public static int COLOR_FIRE = 0;
	final public static int COLOR_ICE = 1;
	final public static int COLOR_TURQUOISE = 2;
	
	final private static int PALETTE_COUNT = 64;
	
	private List <RGB>_list = new ArrayList<RGB>();
	private int _paletteCount;
	
	public FColorPaletteCyclic(int type){
		_setPalette(type);
	}

	private void _setPalette(int type){
		RGB[] colors = _getPalette(type);
		
		FPalette pal = new FPalette(colors, PALETTE_COUNT);
		pal.calc(this._list);
		
		_paletteCount = _list.size();
	}
	
	private  RGB[] _getPalette(int type){
		switch(type){
		case COLOR_ICE:			return _getPaletteIce();
		case COLOR_TURQUOISE:	return _getPaletteTurqise();
		case COLOR_FIRE:
		default:				return _getPaletteFire();
		}
	}
	
	private RGB[] _getPaletteFire(){
		RGB[] colors = {
				new RGB(1, 1, 0, 1),		// yellow
				new RGB(1, 0, 0, 1),		// red
				new RGB(1, 1, 0, 1),		// yellow
		};
		
		return colors;
	}
	
	private RGB[] _getPaletteIce(){
		RGB[] colors = {
				new RGB(1, 1, 1, 1),		// white
				new RGB(0, 0, 1, 1),		// blue
				new RGB(1, 1, 1, 1),		// white
		};

		return colors;
	}

	private RGB[] _getPaletteTurqise(){
		RGB[] colors = {
				new RGB(1, 2, 1, 2),		// greenish
				new RGB(0, 0, 2, 2),		// cyan
				new RGB(1, 2, 1, 2),		// greenish
		};

		return colors;
	}

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		if (color == maxcolor){
			rgb.setColorZero();
			
			return rgb;
		}

		int location = color % _paletteCount;
		RGB a = _list.get(location);
		rgb.setColorFromRGB(a);

		return rgb;
	}
}
