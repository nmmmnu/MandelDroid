package nu.nmmm.android.fractal.color;

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

	public RGB setColor(int r, int g, int b, int max){
		if (max == 0){
			setColorZero();
			return this;
		}

		this.r = _calcMax(r, max);
		this.g = _calcMax(g, max);
		this.b = _calcMax(b, max);

		return this;
	}

	public RGB setColor(int a, int max){
		if (max == 0){
			setColorZero();
			return this;
		}

		a = _calcMax(a, max);

		this.r = a;
		this.g = a;
		this.b = a;

		return this;
	}

	public RGB setColorZero(){
		this.r = 0;
		this.g = 0;
		this.b = 0;

		return this;
	}

	public RGB setColorFromRGB(RGB rgb){
		this.r = rgb.r;
		this.g = rgb.g;
		this.b = rgb.b;

		return this;
	}

	private int _calcMax(int a, int max){
		return (int) (a / (double) max * MAX_COLOR);
	}
}
