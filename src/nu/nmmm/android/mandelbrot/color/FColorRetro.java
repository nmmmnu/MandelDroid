package nu.nmmm.android.mandelbrot.color;


public class FColorRetro implements FColor{
	final private static int MAXCOLOR = 8 - 1;
	
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		if (color == maxcolor){
			rgb.setColorZero();
			return rgb;
		}
		
		int a = (color % MAXCOLOR) + 1;
		
		int c = maxcolor - color;
		
		rgb.setColor(
				_bit(a, 0x01, c), 
				_bit(a, 0x02, c), 
				_bit(a, 0x04, c), 
					
				maxcolor
		);

		return rgb;
	}
	
	private int _bit(int a, int b, int one){
		return (a & b) > 0 ? one : 0;
	}
}
