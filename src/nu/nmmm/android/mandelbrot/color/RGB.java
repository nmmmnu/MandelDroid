package nu.nmmm.android.mandelbrot.color;

public class RGB {
	final public static int MAX_COLOR = 0xFF;
	
	public int r;
	public int g;
	public int b;
	
	public RGB(int r, int g, int b){
		setColor(r, g, b);
	}
	
	public RGB(int a){
		this(a, a, a);
	}
	
	public RGB() {
		this(0);
	}

	public void setColor(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void setColor(int a){
		setColor(a, a, a);
	}
	
	public void setColor(RGB rgb){
		setColor(rgb.r, rgb.g, rgb.b);
	}

}
