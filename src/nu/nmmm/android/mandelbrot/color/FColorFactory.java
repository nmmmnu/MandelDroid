package nu.nmmm.android.mandelbrot.color;


public class FColorFactory {
	public final static int COLOR_STANDARD	= 0;
	public final static int COLOR_COSMOS	= 1;
	public final static int COLOR_COSMOS_O	= 1001;
	public final static int COLOR_RETRO		= 2;
	public final static int COLOR_REVERSE	= 3;
	public final static int COLOR_FIRE		= 4;
	public final static int COLOR_ICE		= 5;
	public final static int COLOR_NONE		= 100;
	
	public static FColor getInstance(int type){
		switch(type){
		case COLOR_FIRE:		return new FColorPaletteCyclic(FColorPaletteCyclic.COLOR_FIRE);
		case COLOR_ICE:			return new FColorPaletteCyclic(FColorPaletteCyclic.COLOR_ICE);
		case COLOR_REVERSE:		return new FColorReverse();
		case COLOR_COSMOS:		return new FColorCosmosNew();
		case COLOR_RETRO:		return new FColorRetro();
		case COLOR_COSMOS_O:	return new FColorCosmos();
		case COLOR_NONE:		return new FColorNone();
		case COLOR_STANDARD:
		default:				return new FColorStandard();
		}
	}
}
