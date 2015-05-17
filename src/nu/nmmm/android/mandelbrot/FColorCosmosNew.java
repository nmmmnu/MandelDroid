package nu.nmmm.android.mandelbrot;

public class FColorCosmosNew extends FColorCosmos{
	final private static int MAXCOLOR = 0xFF;

	@Override
	protected int _transIt(int color, int maxcolor){
		return (int) (MAXCOLOR - color / (double) maxcolor * MAXCOLOR);
	}
}
