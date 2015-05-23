package nu.nmmm.android.mandelbrot.color;

import java.util.ArrayList;
import java.util.List;

public class FColorPaletteCyclic implements FColor{
	final public static int COLOR_FIRE = 0;
	final public static int COLOR_ICE = 1;
	
	final private static int PALETTE_COUNT = 64;
	
	private List <RGB>_list = new ArrayList<RGB>();
	
	public FColorPaletteCyclic(int type){
		_setPalette(type);
	}

	private void _setPalette(int type){
		RGB[] colors = _getPalette(type);
		
		FPalette pal = new FPalette(colors, PALETTE_COUNT);
		pal.calc(this._list);
	}
	
	private  RGB[] _getPalette(int type){
		switch(type){
		case COLOR_ICE:		return _getPaletteIce();
		case COLOR_FIRE:
		default:			return _getPaletteFire();
		}
	}
	
	private RGB[] _getPaletteFire(){
		RGB[] colors = {
				new RGB(0xff, 0xff, 0x00),		// yellow
				new RGB(0xff, 0x00, 0x00),		// red
				new RGB(0xff, 0xff, 0x00)		// yellow
		};
		
		return colors;
	}
	
	private RGB[] _getPaletteIce(){
		RGB[] colors = {
				new RGB(0xff, 0xff, 0xff),		// white
				new RGB(0x00, 0x00, 0xff),		// blue
				new RGB(0xff, 0xff, 0xff)		// white
		};

		return colors;
	}

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		if (color == maxcolor){
			rgb.setColor(0);
			
			return rgb;
		}

		int location = color % PALETTE_COUNT;
		RGB a = _list.get(location);
		rgb.setColor(a);

		return rgb;
	}
}