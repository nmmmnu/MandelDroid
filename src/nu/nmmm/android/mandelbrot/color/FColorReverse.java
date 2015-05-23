package nu.nmmm.android.mandelbrot.color;


public class FColorReverse implements FColor{
	private int _transIt(int color, int maxcolor){
		return (int) (color / (double) maxcolor * RGB.MAX_COLOR);
	}

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color == maxcolor ? 0 : _transIt(color, maxcolor);

		rgb.setColor(a);

		return rgb;
	}
}
