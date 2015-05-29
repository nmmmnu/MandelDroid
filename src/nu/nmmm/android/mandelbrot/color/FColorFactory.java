package nu.nmmm.android.mandelbrot.color;


public class FColorFactory {
	public final static int COLOR_STANDARD		= 0;
	public final static int COLOR_COSMOS		= 1;
	public final static int COLOR_REVERSE		= 2;
	public final static int COLOR_CYCLIC		= 3;
	public final static int COLOR_RETRO			= 4;
	public final static int COLOR_FIRE			= 101;
	public final static int COLOR_ICE			= 102;
	public final static int COLOR_TOURQUOISE	= 103;
	public final static int COLOR_NONE			= 500;
	
	public static FColor getInstance(int type){
		switch(type){
		case COLOR_STANDARD:	return new FColorStandard();
		case COLOR_COSMOS:		return new FColorCosmos(true);
		case COLOR_REVERSE:		return new FColorReverse();
		case COLOR_CYCLIC:		return new FColorCyclic();
		case COLOR_RETRO:		return new FColorRetro();
		
		case COLOR_FIRE:		return new FColorPaletteCyclic(FColorPaletteCyclic.COLOR_FIRE);
		case COLOR_ICE:			return new FColorPaletteCyclic(FColorPaletteCyclic.COLOR_ICE);
		case COLOR_TOURQUOISE:	return new FColorPaletteCyclic(FColorPaletteCyclic.COLOR_TURQUOISE);

		case COLOR_NONE:		return new FColorNone();
		
		default:				return new FColorStandard();
		}
	}
}
