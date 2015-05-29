package nu.nmmm.android.mandelbrot;


public class FractalCalculator{
	public final static int TYPE_CLASSIC					= 0;
	public final static int TYPE_BURNINGSHIP				= 1;
	public final static int TYPE_PERPENDICULAR_BURNINGSHIP	= 2;
	public final static int TYPE_PERPENDICULAR_MANDELBROT	= 3;
	
	private final static double ESCAPE2 = 4;
	
	private int _type;
	private int _iterations;
	
	public FractalCalculator(int type, int iter){
		setType(type);
		setIterations(iter);
	}
	
	public void setIterations(int iter){
		this._iterations = iter;
	}
	
	public int getIterations(){
		return _iterations;
	}

	public void setType(int type) {
		this._type = type;
	}

	public int getType() {
		return _type;
	}
	
	public int Z(double x, double y){
		switch(this._type){
		case TYPE_CLASSIC:						return Z_Mandelbrot(x, y, _iterations);
		case TYPE_BURNINGSHIP:					return Z_BurningShip(x, y, _iterations);
		case TYPE_PERPENDICULAR_BURNINGSHIP:	return Z_PerpendicularBurningShip(x, y, _iterations);
		case TYPE_PERPENDICULAR_MANDELBROT:		return Z_PerpendicularMandelbrot(x, y, _iterations);					
		}
		
		return 0;
	}
	
	private int Z_Mandelbrot(double x, double y, int iterations){
		double zx = 0;
		double zy = 0;

		int i;
		for(i = 0; i < iterations; ++i){
			double zx2 = zx * zx;
			double zy2 = zy * zy;

			if (zx2 + zy2 > ESCAPE2)
				return i;

			// z = z*z + c

			zy = 2 * zx * zy + y;
			zx = zx2 - zy2 + x;
		}
		
		return i;
	}

	private int Z_BurningShip(double x, double y, int iterations){
		double zx = 0;
		double zy = 0;

		int i;
		for(i = 0; i < iterations; ++i){
			double zx2 = zx * zx;
			double zy2 = zy * zy;

			if (zx < 0)
				zx = -zx;
			
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

	private int Z_PerpendicularBurningShip(double x, double y, int iterations){
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

	private int Z_PerpendicularMandelbrot(double x, double y, int iterations){
		double zx = 0;
		double zy = 0;

		int i;
		for(i = 0; i < iterations; ++i){
			double zx2 = zx * zx;
			double zy2 = zy * zy;

			if (zx > 0)
				zx = -zx;

			if (zx2 + zy2 > ESCAPE2)
				return i;

			// z = z*z + c

			zy = 2 * zx * zy + y;
			zx = zx2 - zy2 + x;
		}
		
		return i;
	}
}
