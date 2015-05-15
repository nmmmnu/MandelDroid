package nu.nmmm.android.mandelbrot;

public interface FractalManagerPlot {
	boolean putPixel(int x, int y, int color, int maxcolor, int previewSize);
}
