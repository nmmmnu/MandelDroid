package nu.nmmm.android.mandeldroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
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
	
	private Thread _thread = null;
	private boolean _threadRunning = false;

	public MDView(Context context, int width, int height) {
		super(context);
		
		this._changeSize(width, height);
		
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
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

	//	this._changeSize(getWidth(), getHeight());
	}

	private void _changeSize(int width, int height){
		Log.v("mdviev", "Screen size: " + Integer.toString(width) + "x" + Integer.toString(height));
		
		this._width = width;
		this._height = height;
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
		
		return _threadRunning;
	}
	
	@Override
	public int getPlotWidth() {
		return _width;
	}

	@Override
	public int getPlotHeight() {
		return _height;
	}

	public void startThread(){
		if (_threadRunning )
			return;

		_threadRunning = true;

		_thread = new Thread(this);		
		_thread.start();	
		
		Log.v("mdview", "thread started");
	}

	public void stopThread(){
		if (! _threadRunning )
			return;
		
		_threadRunning = false;
		
		try {
			_thread.join(); 
		} catch (InterruptedException e) {
			// none
		}
		
		_thread = null;

		Log.v("mdview", "thread stopped");
	}
	
	@Override
	public void run() {
		MandelbrotMementoFactory memento = MandelbrotMementoFactory.PBS_COMMON_IFS_TREE_CARDIOUD;
		_fractalManager.setMemento( memento.getInstance() );
		_fractalManager.generate(this);
		
		this.postInvalidate();

		_threadRunning = false;
	}
}
