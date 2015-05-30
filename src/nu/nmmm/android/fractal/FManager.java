package nu.nmmm.android.fractal;

import nu.nmmm.android.fractal.calculator.FCalculator;
import nu.nmmm.android.fractal.calculator.FCalculatorFactory;

public class FManager {
	// must be power of 2
	// 64 is better for most cases
	final static int PREVIEW_SQUARE_MAX = 1 << 6;

	private FCalculator _fc;

	private int _type;
	private int _iterations;

	private double _centerX;
	private double _centerY;
	private double _halfWidthX;

	private int _screenWidth = 0;
	private int _screenHeight = 0;
	private double _screenRes = 0;

	public FManager(int type, int screenWidth, int screenHeight){
		this.setType(type);
		this.setIterations(32);
		this.setCenter(0, 0, 2.2);

		setScreenSize(screenWidth, screenHeight);
	}

	public void setScreenSize(int screenWidth, int screenHeight) {
		this._screenWidth = screenWidth;
		this._screenHeight = screenHeight;
		this._screenRes = _getRes();
	}

	public void setCenter(double centerX, double centerY, double halfWidthX){
		this._centerX = centerX;
		this._centerY = centerY;
		this._halfWidthX = halfWidthX;

		this._screenRes = _getRes();
	}

	public double[] getCenter(){
		double x[] = { _centerX, _centerY, _halfWidthX };
		return x;
	}

	public void setCenterRelativeToScreen(double deltaX, double deltaY, double scale){
		double centerX = _centerX + deltaX * _screenRes;
		double centerY = _centerY + deltaY * _screenRes;

		double halfWidthX =  _halfWidthX * scale;

		setCenter(centerX, centerY, halfWidthX);
	}

	public void setIterations(int iterations){
		this._iterations = iterations;
	}

	public void setType(int type){
		this._type = type;
		this._fc = FCalculatorFactory.getInstance(type);
	}

	private boolean _generate(FManagerPlot plot, int step, int stepMAX){
		// prepare calculations

		double half_widthy = _halfWidthX * _screenHeight / _screenWidth;

		double startx = _centerX - _halfWidthX;
		double starty = _centerY - half_widthy;

		int step2 = step << 1;

		// iteration calculations

		int maxcolor = _iterations;

		int x, y;

		for(y = 0; y < _screenHeight; y += step){
			double yr = starty + _screenRes * y;
			for(x = 0; x < _screenWidth; x += step){
				if (x % step2 == 0 && y % step2 == 0 && step != stepMAX)
					continue;

				double xr = startx + _screenRes * x;

				int color = _fc.Z(xr, yr, _iterations);

				boolean ok = plot.putPixel(x, y, color, maxcolor, step);

				if (ok == false)
					return false;
			}
		}

		return true;
	}

	public boolean generateSmooth(FManagerPlot plot){
		if (_screenWidth == 0 || _screenHeight == 0 || _screenRes == 0)
			return false;

		int step = PREVIEW_SQUARE_MAX;

		while(step > 1){
			boolean ok = _generate(plot, step, PREVIEW_SQUARE_MAX);

			if (!ok)
				return false;

			step = step >> 1;
		}

		// render final fractal
		return _generate(plot, 1, PREVIEW_SQUARE_MAX);
	}

	public boolean generate(FManagerPlot plot){
		return _generate(plot, 1, 1);
	}


	public Memento getMemento(){
		return new Memento(_type, _iterations, _centerX, _centerY, _halfWidthX);
	}

	public boolean setMemento(Memento mem){
		this.setType(mem.type);
		this.setIterations(mem.iterations);

		this.setCenter(mem.x, mem.y, mem.hw);

		return true;
	}

	private double _getRes() {
		if (_screenWidth < PREVIEW_SQUARE_MAX)
			return 0;

		double fractalWidth = _halfWidthX * 2;

		return fractalWidth / (_screenWidth - 1);
	}
}
