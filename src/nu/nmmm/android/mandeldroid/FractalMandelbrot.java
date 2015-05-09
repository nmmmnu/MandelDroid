package nu.nmmm.android.mandeldroid;

public class FractalMandelbrot implements Fractal{
	public final static int TYPE_CLASSIC		= 0;
	public final static int TYPE_BURNINGSHIP	= 1;
	public final static int TYPE_PERPENDICULAR	= 2;
	
	private final static float ESCAPE2 = 4;
	
	private int _type;
	private int _iter;
	
	FractalMandelbrot(int type, int iter){
		this._type = type;
		this._iter = iter;
	}
	
	@Override
	public int getIterations(){
		return _iter;
	}
	
	@Override
	public int Z(float x, float y){
		float zx = 0;
		float zy = 0;

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
					zy = -zx;
				
				break;

			case TYPE_PERPENDICULAR:
				if (zy < 0)
					zy = -zx;

				break;
			}

			float zx2 = zx * zx;
			float zy2 = zy * zy;

			if (zx2 + zy2 > ESCAPE2)
				return i;

			// z = z*z + c

			zy = 2 * zx * zy + y;
			zx = zx2 - zy2 + x;
		}

		return i;
	}
}
