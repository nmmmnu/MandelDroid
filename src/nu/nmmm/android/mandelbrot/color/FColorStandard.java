package nu.nmmm.android.mandelbrot.color;

public class FColorStandard implements FColor{
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		rgb.setColor(maxcolor - color, maxcolor);

		return rgb;
	}
}
