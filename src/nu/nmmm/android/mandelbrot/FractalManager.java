package nu.nmmm.android.mandelbrot;


public class FractalManager {
	final static int PREVIEW_SQUARE_MAX = 64;
	
	private FractalCalculator _calc;
	
	private float _centerX;
	private float _centerY;
	private float _halfWidthX;
	
	private int _screenWidth = 0;
	private int _screenHeight = 0;
	private float _screenRes = 0;
		
	public FractalManager(FractalCalculator calc){
		this._calc = calc;
		
		// this generally includes every possible fractal
		this.setCenter((float) 0.0, (float) 0.0, (float) 2.0);
	}
	
	public FractalManager(FractalCalculator calc, int screenWidth, int screenHeight){
		this(calc);
		
		setScreenSize(screenWidth, screenHeight);
	}
	
	public void setScreenSize(int screenWidth, int screenHeight) {
		this._screenWidth = screenWidth;
		this._screenHeight = screenHeight;
		this._screenRes = _getRes();
	}

	public void setCenter(float centerX, float centerY, float halfWidthX){
		this._centerX = centerX;
		this._centerY = centerY;
		this._halfWidthX = halfWidthX;
		
		this._screenRes = _getRes();
	}
	
	public void setCenterRelativeToScreen(float deltaX, float deltaY, float halfWidthX){
		setCenter(_centerX + deltaX * _screenRes, _centerY + deltaY * _screenRes, _halfWidthX);
	}

	private boolean _generate(FractalManagerPlot plot, int step){		
		// prepare calculations
		
		float half_widthy = _halfWidthX * _screenHeight / _screenWidth;
		
		float startx = _centerX - _halfWidthX;
		float starty = _centerY - half_widthy;
		
		int step2 = step << 1;
		
		// iteration calculations
		
		int maxcolor = this._calc.getIterations();
		
		int x, y;

		for(y = 0; y < _screenHeight; y += step){
			float yr = starty + _screenRes * y;
			for(x = 0; x < _screenWidth; x += step){
				if (x % step2 == 0 && y % step2 == 0 && step != PREVIEW_SQUARE_MAX)
					continue;
				
				float xr = startx + _screenRes * x;
				
				int color = _calc.Z(xr, yr);
				
				boolean ok = plot.putPixel(x, y, color, maxcolor, step);
				
				if (!ok)
					return false;
			}
		}
		
		return true;
	}
	
	public boolean generateSmooth(FractalManagerPlot plot){
		if (_screenWidth == 0 || _screenHeight == 0 || _screenRes == 0)
			return false;
		
		int step = PREVIEW_SQUARE_MAX;
		
		while(step > 1){
			boolean ok = _generate(plot, step);
			
			if (!ok)
				return false;
			
			step = step >> 1;
		}
		
		// render final fractal
		return generate(plot);
	}
	
	public boolean generate(FractalManagerPlot plot){
		return _generate(plot, 1);
	}
	
	
	public Memento getMemento(){
		return new Memento(_calc.getType(), _calc.getIterations(), _centerX, _centerY, _halfWidthX);
	}

	public boolean setMemento(Memento mem){
		this._calc.setType(mem.type);
		this._calc.setIterations(mem.iterations);
		
		this.setCenter(mem.x, mem.y, mem.hw);

		return true;
	}

	
	
	private float _getRes() {
		if (_screenWidth < PREVIEW_SQUARE_MAX)
			return 0;
		
		float fractalWidth = _halfWidthX * 2;
		
		return fractalWidth / (_screenWidth - 1);
	}
}
