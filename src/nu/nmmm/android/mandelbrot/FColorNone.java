package nu.nmmm.android.mandelbrot;


class FColorNone implements FColor{
	@Override
	public int[] convertColor(int color, int maxcolor) {
		int a = color < maxcolor ? 0xFF : 0;

		int x[] = {a, a, a};

		return x;
	}
}
