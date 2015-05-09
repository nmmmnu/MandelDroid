package nu.nmmm.android.mandeldroid;

public interface FractalManagerPlot {
	int getPlotWidth();
	int getPlotHeight();
	boolean plot(float x, float y, int color, int maxcolor);
}
