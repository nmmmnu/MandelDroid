package nu.nmmm.android.mandelbrot;


public class ColorCosmosNew extends ColorCosmos{
	@Override
	protected int _transIt(int color, int maxcolor){
		return (int) (0xFF - color / (float) maxcolor * 0xFF);
	}
}
