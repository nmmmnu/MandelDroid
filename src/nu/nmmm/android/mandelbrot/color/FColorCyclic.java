package nu.nmmm.android.mandelbrot.color;

public class FColorCyclic  implements FColor{
	final private static int SIZE2 = 64;
	final private static int SIZE = SIZE2 >> 1;
	final private static int STEP = RGB.MAX_COLOR / SIZE;
	
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		int a = color == maxcolor ? 0 : color % SIZE2;
		
		if (a > SIZE)
			a = SIZE2 - a;
				
		rgb.setColor(a * STEP);
		
		return rgb;
	}
}
