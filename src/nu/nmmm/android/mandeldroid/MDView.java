package nu.nmmm.android.mandeldroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import nu.nmmm.android.mandelbrot.*;

public class MDView extends View implements Runnable, FractalManagerPlot{
	private FractalManager _fractalManager;
	private Color _color;

	private Bitmap _image;
	private Canvas _imageCanvas;
	private Paint _paint;
	
	// dummy used onDraw()
	private Paint _paint2 = new Paint();
	
	final float FX  = (float) 0.336;
	final float FY  = (float) 0.052;
	final float FHW = (float) 0.012;

	private int _counter = 0;
	final private static int COUNTER_REFRESH = 1024;
	
	private int _height;
	private int _width;
	
	public MDView(Context context, Point size) {
		super(context);
		
		this._width = size.x;
		this._height = size.y;
				
		//Log.v("bla", "x = " + Integer.toString(_width) );
		//Log.v("bla", "y = " + Integer.toString(_height) );

		this._image = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_8888);
		this._imageCanvas = new Canvas(this._image);
	
	    this._paint = new Paint();
	    this._paint.setAntiAlias(false);
	    
		this._color = new ColorStandard();
		//this._color = new ColorCosmosNew();

		FractalCalculator fractalCalc = new FractalCalculatorMandelbrot(FractalCalculatorMandelbrot.TYPE_CLASSIC, 128);
		this._fractalManager = new FractalManager(fractalCalc);
	}

	
	@Override
	public void run() {
		MandelbrotMementoFactory memento = MandelbrotMementoFactory.PBS_COMMON_IFS_TREE_CARDIOUD;
		_fractalManager.setMemento( memento.getInstance() );
		_fractalManager.generate(this);
		
		this.postInvalidate();
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
           canvas.drawBitmap(_image, 0, 0, _paint2);
    }

    
	@Override
	public boolean putPlotPixel(float x, float y, int color, int maxcolor) {
		int a = _color.convertColor(color, maxcolor);
		
		_paint.setARGB(0xFF, a, a, a);
				
		_imageCanvas.drawPoint(x, y, _paint);
		
		if (_counter++ > COUNTER_REFRESH){
			_counter = 0;

			this.postInvalidate();
		}
		
		return true;
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
