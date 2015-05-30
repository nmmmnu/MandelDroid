package nu.nmmm.android.fractal.color;

public class FColorCyclic  implements FColor{
	final private static int SIZE2 = 64;
	final private static int SIZE = SIZE2 >> 1;

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		// bounded color
		if (color == maxcolor)
			return rgb.setColorZero();

		int a = color % SIZE2;

		if (a > SIZE)
			a = SIZE2 - a;

		return rgb.setColor(a, SIZE2);
	}
}
