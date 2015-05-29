package nu.nmmm.android.mandelbrot.color;



class FColorNone implements FColor{
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color == maxcolor ? 0 : maxcolor;

		rgb.setColor(a, maxcolor);

		return rgb;
	}
}
