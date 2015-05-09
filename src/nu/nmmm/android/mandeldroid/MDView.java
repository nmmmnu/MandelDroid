package nu.nmmm.android.mandeldroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class MDView extends View implements Runnable, FractalManagerPlot{
	private FractalManager _fractalManager;
	private FColor _color;

	private Bitmap _image;
	private Canvas _imageCanvas;
	private Paint _paint;
	
	private Paint _paint2 = new Paint();
	
	final float FX  = (float) 0.336;
	final float FY  = (float) 0.052;
	final float FHW = (float) 0.012;

	private int __counter = 0;
	final private static int COUNTER_MAX = 1024;
	
	private int _height;
	private int _width;
	
	public MDView(Context context, Point size) {
		super(context);
		
		this._width = size.x;
		this._height = size.y;
				
		Log.v("bla", "x = " + Integer.toString(_width) );
		Log.v("bla", "y = " + Integer.toString(_height) );

		this._image = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_8888);
		this._imageCanvas = new Canvas(this._image);
	
	    this._paint = new Paint();
	    this._paint.setAntiAlias(false);
	    
		this._color = new FColorStandard();
		//_color = new FColorCosmosNew();

		Fractal fractal = new FractalMandelbrot(FractalMandelbrot.TYPE_CLASSIC, 128);
		
		this._fractalManager = new FractalManager(fractal);
	}

	@Override
	public boolean plot(float x, float y, int color, int maxcolor) {
		int a = _color.convertColor(color, maxcolor);
		
		_paint.setARGB(0xFF, a, a, a);
				
		_imageCanvas.drawPoint(x, y, _paint);
		
		if (__counter++ > COUNTER_MAX){
			__counter = 0;

			this.postInvalidate();
		}
		
		return true;
	}
	
	@Override
	public void run() {
		Log.v("bla", "begin");
		_fractalManager.generate(this, FX, FY, FHW);
		Log.v("bla", "finished");
		
		this.postInvalidate();
	}

	/*
	public void pause(){
		if (!_running)
			return;

		_running = false;
		
		try {
			_thread.join();
		} catch (InterruptedException e) {
		}
		
		this.postInvalidate();

		_thread = null;
	}
	
	public void resume(){
		if (_running)
			return;
		
		_running = true;
		
		_thread = new Thread(this);
		_thread.start();
	}
	*/
	
    protected void onDraw(Canvas canvas) {
           canvas.drawBitmap(_image, 0, 0, _paint2);
    }

	@Override
	public int getPlotWidth() {
		return _width;
	}

	@Override
	public int getPlotHeight() {
		return _height;
	}

}
