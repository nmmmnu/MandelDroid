package nu.nmmm.android.mandelbrot;


class FColorCosmos implements FColor{
	protected int _transIt(int color, int it){
		return 0xFF;
	}
	
	@Override
	public int[] convertColor(int color, int maxcolor) {
		int a = (color & 1) != 0 ? _transIt(color, maxcolor) : 0;

		int x[] = {a, a, a};

		return x;
	}
}
