package nu.nmmm.android.mandelbrot;

public class Memento {
	public int type;
	public int iterations;

	public double x;
	public double y;
	public double hw;

	public Memento(int type, int iterations, double x, double y, double hw){
		this.type = type;
		this.iterations = iterations;
		this.x = x;
		this.y = y;
		this.hw = hw;
	}
}
