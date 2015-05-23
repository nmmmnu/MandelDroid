package nu.nmmm.android.mandelbrot.color;



class FColorNone implements FColor{
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color < maxcolor ? RGB.MAX_COLOR : 0;

		rgb.setColor(a);

		return rgb;
	}
}
