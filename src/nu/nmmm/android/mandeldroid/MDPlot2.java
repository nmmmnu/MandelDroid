package nu.nmmm.android.mandeldroid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MDPlot2 implements FractalManagerPlot{
	private Canvas _canvas = null;
	private Paint _paint;
	private FColor _color;
	
	MDPlot2(FColor color){
		this._color = color;

	    _paint = new Paint();
	    _paint.setAntiAlias(false);
	}
	
	public void setCanvas(Canvas canvas){
		this._canvas = canvas;
	}
	
	@Override
	public void plot(float x, float y, int color, int maxcolor) {
		int a = _color.convertColor(color, maxcolor);
		
		_paint.setARGB(0xFF, a, a, a);
		
		_canvas.drawPoint(x, y, _paint);
	}

	@Override
	public int getWidth() {
		return _canvas.getWidth();
	}

	@Override
	public int getHeight() {
		return _canvas.getHeight();
	}
}
