package nu.nmmm.android.mandelbrot;


public class ColorNone implements Color{
	@Override
	public int convertColor(int color, int maxcolor) {
		return color < maxcolor ? 0xFF : 0;
	}
}
