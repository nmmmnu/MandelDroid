package nu.nmmm.android.mandelbrot;

public class FractalCalculatorFactory {
	public final static int TYPE_MANDELBROT					= 0;
	public final static int TYPE_BURNINGSHIP				= 1;
	public final static int TYPE_PERPENDICULAR_BURNINGSHIP	= 2;
	public final static int TYPE_PERPENDICULAR_MANDELBROT	= 3;

	public static FractalCalculator getInstance(int type){
		switch(type){
		case TYPE_MANDELBROT				: return new FractalCalculatorMandelbrot();
		case TYPE_BURNINGSHIP				: return new FractalCalculatorBurningShip();
		case TYPE_PERPENDICULAR_BURNINGSHIP	: return new FractalCalculatorPerpendicularBurningShip();
		case TYPE_PERPENDICULAR_MANDELBROT	: return new FractalCalculatorPerpendicularMandelbrot();
		}
		
		return getInstance(TYPE_MANDELBROT);
	}
}
