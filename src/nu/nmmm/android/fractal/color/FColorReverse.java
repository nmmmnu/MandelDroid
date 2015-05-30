package nu.nmmm.android.fractal.color;


public class FColorReverse implements FColor{
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		// bounded color
		if (color == maxcolor)
			return rgb.setColorZero();

		return rgb.setColor(color, maxcolor);
	}
}
