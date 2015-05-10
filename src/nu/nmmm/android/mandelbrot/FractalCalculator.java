package nu.nmmm.android.mandelbrot;

public interface FractalCalculator {
	public int Z(double x, double y);

	void setIterations(int iter);
	public int getIterations();

	void setType(int type);
	public int getType();

}
