package nu.nmmm.android.mandelbrot;

public interface FractalManagerPlot {
	int getPlotWidth();
	int getPlotHeight();
	boolean putPlotPixel(float x, float y, int color, int maxcolor);
}
