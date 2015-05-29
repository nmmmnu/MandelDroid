package nu.nmmm.android.mandelbrot.color;

public class RGB {
	final private static int MAX_COLOR = 0xFF;
	
	public int r;
	public int g;
	public int b;
	
	public RGB(int r, int g, int b, int max){
		setColor(r, g, b, max);
	}
	
	public RGB(int r, int g, int b){
		setColor(r, g, b, MAX_COLOR);
	}

	public RGB(int a, int max){
		setColor(a, max);
	}
	
	public RGB() {
		setColorZero();
	}

	public void setColor(int r, int g, int b, int max){
		if (max == 0){
			setColorZero();
			return;
		}

		this.r = _calcMax(r, max);
		this.g = _calcMax(g, max);
		this.b = _calcMax(b, max);
	}

	public void setColor(int a, int max){
		if (max == 0){
			setColorZero();
			return;
		}
		
		a = _calcMax(a, max);
		
		this.r = a;
		this.g = a;
		this.b = a;
	}
	
	public void setColorZero(){
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
	
	public void setColorFromRGB(RGB rgb){
		this.r = rgb.r;
		this.g = rgb.g;
		this.b = rgb.b;
	}
	
	private int _calcMax(int a, int max){		
		return (int) (a / (double) max * MAX_COLOR);
	}
}
