package nu.nmmm.android.mandelbrot;


public class FColorNone implements FColor{
	@Override
	public int convertColor(int color, int maxcolor) {
		return color < maxcolor ? 0xFF : 0;
	}
}