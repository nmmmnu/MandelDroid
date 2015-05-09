package nu.nmmm.android.mandeldroid;

public interface FractalManagerPlot {
	int getWidth();
	int getHeight();
	void plot(float x, float y, int color, int maxcolor);
}
