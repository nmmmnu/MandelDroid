package nu.nmmm.android.mandelbrot.color;

import java.util.List;

public class FPalette {
	private RGB[] _colors;
	private int _size;
	
	public FPalette(RGB[] colors, int size){
		this._colors = colors;
		this._size = size;
	}
	
	public List<RGB> calc(List<RGB> list){
		list.clear();
		
		int len = _colors.length - 1;
		
		int s = _size / len;
		
		for(int i = 0; i < len; ++i){
			double r = _colors[i].r;
			double g = _colors[i].g;
			double b = _colors[i].b;
			
			double rs = (_colors[i + 1].r - r) / s;
			double gs = (_colors[i + 1].g - g) / s;
			double bs = (_colors[i + 1].b - b) / s;
			
			for(int j = 0; j < s; ++j){
				list.add(new RGB((int) r, (int) g, (int) b));
				r += rs;
				g += gs;
				b += bs;
			}
			
			//list.add(new RGB((int) r, (int) g, (int) b));
		}
		
		return list;
	}
}
