package nu.nmmm.android.fractal.calculator;

public class FCalculatorFactory {
	public final static int TYPE_MANDELBROT					= 0;
	public final static int TYPE_BURNINGSHIP				= 1;
	public final static int TYPE_PERPENDICULAR_BURNINGSHIP	= 2;
	public final static int TYPE_PERPENDICULAR_MANDELBROT	= 3;

	public static FCalculator getInstance(int type){
		switch(type){
		case TYPE_MANDELBROT				: return new FCalculatorMandelbrot();
		case TYPE_BURNINGSHIP				: return new FCalculatorBurningShip();
		case TYPE_PERPENDICULAR_BURNINGSHIP	: return new FCalculatorPerpendicularBurningShip();
		case TYPE_PERPENDICULAR_MANDELBROT	: return new FCalculatorPerpendicularMandelbrot();
		}
		
		return getInstance(TYPE_MANDELBROT);
	}
}
