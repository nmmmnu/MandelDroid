package nu.nmmm.android.fractal.color;



class FColorNone implements FColor{
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color == maxcolor ? 0 : maxcolor;

		return rgb.setColor(a, maxcolor);
	}
}
