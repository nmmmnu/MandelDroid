package nu.nmmm.android.mandelbrot;

public class FractalCalculatorPerpendicularBurningShip implements FractalCalculator{
	final private static double ESCAPE2 = 4;

	@Override
	public int Z(double x, double y, int iterations) {
		double zx = 0;
		double zy = 0;

		int i;
		for(i = 0; i < iterations; ++i){
			double zx2 = zx * zx;
			double zy2 = zy * zy;

			if (zy < 0)
				zy = -zy;

			if (zx2 + zy2 > ESCAPE2)
				return i;

			// z = z*z + c

			zy = 2 * zx * zy + y;
			zx = zx2 - zy2 + x;
		}
		
		return i;
	}
}
