package nu.nmmm.android.mandelbrot;


public class FColorStandard implements FColor{
	final private static int MAXCOLOR = 0xFF;

	private int _transIt(int color, int it){
		return (int) (MAXCOLOR - color / (double) it * MAXCOLOR);
	}

	@Override
	public int[] convertColor(int color, int maxcolor) {
		int a = _transIt(color, maxcolor);

		int x[] = {a, a, a};

		return x;
	}
}
