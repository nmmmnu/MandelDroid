package nu.nmmm.android.mandelbrot;

public class Memento {
	public int type;
	public int iterations;
	
	public float x;
	public float y;
	public float hw;

	public Memento(int type, int iterations, float x, float y, float hw){
		this.type = type;
		this.iterations = iterations;
		this.x = x;
		this.y = y;
		this.hw = hw;
	}

	public Memento(int type, int iterations, double x, double y, double hw){
		this(type, iterations, (float) x, (float) y, (float) hw);
	}

}
