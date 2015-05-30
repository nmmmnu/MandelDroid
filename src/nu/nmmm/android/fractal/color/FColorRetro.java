package nu.nmmm.android.fractal.color;


public class FColorRetro implements FColor{
	final private static int MAXCOLOR = 8 - 1;

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		// bounded color
		if (color == maxcolor)
			return rgb.setColorZero();

		int a = (color % MAXCOLOR) + 1;

		int c = maxcolor - color;

		return rgb.setColor(
				_bit(a, 0x01, c),
				_bit(a, 0x02, c),
				_bit(a, 0x04, c),

				maxcolor
		);
	}

	private int _bit(int a, int b, int one){
		return (a & b) > 0 ? one : 0;
	}
}
