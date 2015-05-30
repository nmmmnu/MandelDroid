package nu.nmmm.android.fractal.calculator;

class FCalculatorMandelbrot implements FCalculator {
	final private static double ESCAPE2 = 4;

	@Override
	public int Z(double x, double y, int iterations){
		double zr = 0;
		double zi = 0;

		int i;
		for(i = 0; i < iterations; ++i){
			double zr2 = zr * zr;
			double zi2 = zi * zi;

			if (zr2 + zi2 > ESCAPE2)
				return i;

			// z = z*z + c

			zi = 2 * zr * zi + y;
			zr = zr2 - zi2 + x;
		}

		return i;
	}
}
