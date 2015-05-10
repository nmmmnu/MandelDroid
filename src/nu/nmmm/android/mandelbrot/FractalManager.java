package nu.nmmm.android.mandelbrot;


public class FractalManager {
	private FractalCalculator _calc;
	
	float centerx;
	float centery;
	float half_widthx;
	
	public FractalManager(FractalCalculator calc){
		this._calc = calc;
		
		this.centerx = 0;
		this.centery = 0;
		this.half_widthx = (float) 2.2;
	}
	
	private static float _getResolution(int screen, float width) {
		return width / (screen - 1);
	}

	private static float _convertX(float startx, float resx, int x){
		return startx + resx * x;
	}

	private static float _convertY(float starty, float resy, int y){
		return starty + resy * y;
	}

	public void generate(FractalManagerPlot plot){
		// prepare calculations
		
		int scrx = plot.getPlotWidth();
		int scry = plot.getPlotHeight();		
		
		float half_widthy = half_widthx * scry / scrx;
		
		float startx = centerx - half_widthx;
		float starty = centery - half_widthy;

		float resx = _getResolution(scrx, half_widthx * 2);
		float resy = resx;
		
		// iteration calculations
		
		int maxcolor = this._calc.getIterations();
		
		int x, y;

		for(y = 0; y < scry; ++y){
			float yr = _convertY(starty, resy, y);
			for(x = 0; x < scrx; ++x){
				float xr = _convertX(startx, resx, x);
				
				int color = _calc.Z(xr, yr);
				
				boolean ok = plot.putPlotPixel(x, y, color, maxcolor);
				
				if (!ok)
					return;
			}
		}
	}
	
	public Memento getMemento(){
		return new Memento(_calc.getType(), _calc.getIterations(), centerx, centery, half_widthx);
	}

	public boolean setMemento(Memento mem){
		this._calc.setType(mem.type);
		this._calc.setIterations(mem.iterations);
		
		this.centerx     = mem.x;
		this.centery     = mem.y;
		this.half_widthx = mem.hw;

		return true;
	}
}
