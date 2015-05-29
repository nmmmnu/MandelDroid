package nu.nmmm.android.mandelbrot.color;


public class FColorReverse implements FColor{
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color == maxcolor ? 0 : color;

		rgb.setColor(a, maxcolor);

		return rgb;
	}
}
