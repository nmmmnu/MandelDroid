package nu.nmmm.android.mandelbrot.color;

public class FColorStandard implements FColor{
	private int _transIt(int color, int maxcolor){
		return (int) (RGB.MAX_COLOR - color / (double) maxcolor * RGB.MAX_COLOR);
	}

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = _transIt(color, maxcolor);

		rgb.setColor(a);

		return rgb;
	}
}
