package nu.nmmm.android.mandelbrot.color;


class FColorCosmosNew extends FColorCosmos{
	@Override
	protected int _transIt(int color, int maxcolor){
		return (int) (RGB.MAX_COLOR - color / (double) maxcolor * RGB.MAX_COLOR);
	}
}
