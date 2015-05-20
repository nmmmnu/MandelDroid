package nu.nmmm.android.mandelbrot;

public class FColorRetro implements FColor{
	final private static int MAX_CHANNEL = 0xFF;

	final private static int MAXCOLOR = 8;
	
	@Override
	public int[] convertColor(int color, int maxcolor) {
		int a = color == maxcolor ? 0 : (color % (MAXCOLOR - 1)) + 1;

		int ch = (int) (MAX_CHANNEL - color / (double) maxcolor * MAX_CHANNEL);
		
		int x[] = {
				(a & 0x01) > 0 ? ch : 0, 
				(a & 0x02) > 0 ? ch : 0, 
				(a & 0x04) > 0 ? ch : 0
		};

		return x;
	}
}
