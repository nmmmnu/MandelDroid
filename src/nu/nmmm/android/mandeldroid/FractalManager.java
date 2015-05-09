package nu.nmmm.android.mandeldroid;

public class FractalManager {
	private Fractal _mandelbrot;
	
	FractalManager(Fractal mandelbrot){
		this._mandelbrot = mandelbrot;
		
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

	void generate(FractalManagerPlot plot, float centerx, float centery, float half_widthx){
		// prepare calculations
		
		int scrx = plot.getPlotWidth();
		int scry = plot.getPlotHeight();		
		
		float half_widthy = half_widthx * scry / scrx;
		
		float startx = centerx - half_widthx;
		float starty = centery - half_widthy;

		float resx = _getResolution(scrx, half_widthx * 2);
		float resy = resx;
		
		// iteration calculations
		
		int maxcolor = this._mandelbrot.getIterations();
		
		int x, y;

		for(y = 0; y < scry; ++y){
			float yr = _convertY(starty, resy, y);
			for(x = 0; x < scrx; ++x){
				float xr = _convertX(startx, resx, x);
				
				int color = _mandelbrot.Z(xr, yr);
				
				boolean ok = plot.plot(x, y, color, maxcolor);
				
				if (!ok)
					return;
			}
		}
	}
}
