package nu.nmmm.android.mandelbrot;


public class FractalCalculatorMandelbrot implements FractalCalculator{
	public final static int TYPE_CLASSIC					= 0;
	public final static int TYPE_BURNINGSHIP				= 1;
	public final static int TYPE_PERPENDICULAR_BURNINGSHIP	= 2;
	public final static int TYPE_PERPENDICULAR_MANDELBROT	= 3;
	
	private final static double ESCAPE2 = 4;
	
	private int _type;
	private int _iter;
	
	public FractalCalculatorMandelbrot(int type, int iter){
		setType(type);
		setIterations(iter);
	}
	
	@Override
	public void setIterations(int iter){
		this._iter = iter;
	}
	
	@Override
	public int getIterations(){
		return _iter;
	}


	@Override
	public void setType(int type) {
		this._type = type;
	}

	@Override
	public int getType() {
		return _type;
	}
	
	@Override
	public int Z(double x, double y){
		double zx = 0;
		double zy = 0;

		int i;
		for(i = 0; i < this._iter; ++i){
			switch(this._type){
			case TYPE_CLASSIC:
				// none
				break;

			case TYPE_BURNINGSHIP:
				if (zx < 0)
					zx = -zx;
				
				if (zy < 0)
					zy = -zy;
				
				break;

			case TYPE_PERPENDICULAR_BURNINGSHIP:
				if (zy < 0)
					zy = -zy;

				break;

			case TYPE_PERPENDICULAR_MANDELBROT:
				if (zx > 0)
					zx = -zx;
				
				break;

			default:
				// none
				break;
			}

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

}
