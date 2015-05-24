package nu.nmmm.android.mandelbrot.color;

class FColorCosmos implements FColor{
	protected int _transIt(int color, int maxcolor){
		return RGB.MAX_COLOR;
	}
	
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = (color & 1) != 0 ? _transIt(color, maxcolor) : 0;

		rgb.setColor(a);
		
		return rgb;
	}
}
