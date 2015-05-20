package nu.nmmm.android.mandelbrot;

public class FColorFactory {
	public final static int COLOR_STANDARD	= 0;
	public final static int COLOR_COSMOS	= 1;
	public final static int COLOR_COSMOS2	= 1001;
	public final static int COLOR_RETRO		= 2;
	public final static int COLOR_NONE		= 100;
	
	public static FColor getInstance(int type){
		switch(type){
		case COLOR_COSMOS:		return new FColorCosmosNew();
		case COLOR_RETRO:		return new FColorRetro();
		case COLOR_COSMOS2:		return new FColorCosmos();
		case COLOR_NONE:		return new FColorNone();
		case COLOR_STANDARD:
		default:				return new FColorStandard();
		}
	}
}
