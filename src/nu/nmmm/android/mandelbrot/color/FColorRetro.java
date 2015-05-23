package nu.nmmm.android.mandelbrot.color;


public class FColorRetro implements FColor{
	final private static int MAXCOLOR = 8;
	
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color == maxcolor ? 0 : (color % (MAXCOLOR - 1)) + 1;

		int ch = (int) (RGB.MAX_COLOR - color / (double) maxcolor * RGB.MAX_COLOR);
		
		rgb.setColor(
				(a & 0x01) > 0 ? ch : 0, 
				(a & 0x02) > 0 ? ch : 0, 
				(a & 0x04) > 0 ? ch : 0
		);

		return rgb;
	}
}
