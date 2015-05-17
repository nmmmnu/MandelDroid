package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import nu.nmmm.android.mandelbrot.*;

class MDView extends View implements Runnable, FractalManagerPlot, GestureDetectorConsumer{
	final private static String TAG = "MDView";
	
	final private static boolean PIXEL_DEBUG_PREVIEW = ! true;
	final private static int PIXEL_DEBUG_PREVIEW_COLOR = Color.RED;
	
	final private static int COUNTER_REFRESH = 1024;
		
	private static int _counterRefresh = 0;

	FractalManager _fractalManager;
	private FColor _fractalColor;

	private Bitmap _image;
	private Canvas _imageCanvas;
	private Paint _paint;
	
	// dummy used onDraw()
	private Paint _paint2;
	
	private int _height;
	private int _width;
	
	private Thread _thread = null;
	private boolean _threadRunning = false;
	
	private GestureDetector _gd;

	@SuppressLint("ClickableViewAccessibility")
	public MDView(Context context, int width, int height) {
		super(context);
		
		this._gd = new GestureDetector(this);
		this.setOnTouchListener(this._gd);
		
		this._width = width;
		this._height = height;
		
		this._image = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_8888);
		this._imageCanvas = new Canvas(this._image);
	
	    this._paint = new Paint();
	   	this._paint.setAntiAlias(false);
	    
	    this._paint2 = new Paint();
	    
	    if (PIXEL_DEBUG_PREVIEW){
		    this._paint2.setAntiAlias(false);
	    	this._paint2.setColor(PIXEL_DEBUG_PREVIEW_COLOR);
	    }
	    
		this._fractalColor = new FColorStandard();
		//this._fractalColor = new FColorCosmosNew();

		MandelbrotMementoFactory memento = MandelbrotMementoFactory.BIG_PICTURE;
		
		FractalCalculator fractalCalc = new FractalCalculatorMandelbrot(FractalCalculatorMandelbrot.TYPE_CLASSIC, 64);
		this._fractalManager = new FractalManager(fractalCalc, _width, _height);
		this._fractalManager.setMemento( memento.getInstance() );
	}
	
	
	// FractalManagerPlot
	
	
	@Override
	public boolean putPixel(int x, int y, int color, int maxcolor, int squareSize) {
		int a = _fractalColor.convertColor(color, maxcolor);
		
		_paint.setARGB(0xFF, a, a, a);
		
		if (squareSize <= 1){
			_imageCanvas.drawPoint(x, y, _paint);
		}else{
			if (! PIXEL_DEBUG_PREVIEW){
				// this is default situation
				_imageCanvas.drawRect(x, y, x + squareSize, y + squareSize, _paint);
			}else{
				_imageCanvas.drawRect(x, y, x + squareSize, y + squareSize, _paint2);
				_imageCanvas.drawPoint(x, y, _paint);
			}
		}
		
		_counterRefresh += squareSize * squareSize;

		if (_counterRefresh > COUNTER_REFRESH){
			_counterRefresh = 0;

			this.postInvalidate();
		}
		
		return _threadRunning;
	}
	
	
	// Runnable
	
	
	public void startThread(){
		if (_threadRunning )
			return;

		_threadRunning = true;

		_thread = new Thread(this);
		_thread.start();	
		
		Log.v(TAG, "thread started"); 
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

		Log.v(TAG, "thread stopped");
	}
	
	public void restartThread(){
		stopThread();
		startThread();
	}
	
	@Override
	public void run() {
		_fractalManager.generateSmooth(this);
		
		this.postInvalidate();

		_threadRunning = false;
	}

	
	// GestureDetector
	
	private float _gdX, _gdY;
	private float _gdScale = 1;
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.scale(_gdScale, _gdScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
		canvas.drawBitmap(_image, _gdX, _gdY, _paint2);
	}
	
	@Override
	public void moveStart(float x, float y){
		_gdX = 0;
		_gdY = 0;
	}
	
	@Override
	public void moveTo(float xxx, float yyy, float dx, float dy, float scale) {
		_gdX = dx;
		_gdY = dy;
		
		_gdScale = scale;
				
		if (scale != 1){
			Log.i(TAG, " dx=" + _gdX + " dy=" + _gdY + " scale=" + scale); 
		}
		
		postInvalidate();
	}

	@Override
	public void moveEnd(float xxx, float yyy, float dx, float dy, float scale) {
		_gdX = 0;
		_gdY = 0;
		_gdScale = 1;
			
		if (dx != 0 || dy != 0 || scale != 1){
			_fractalManager.setCenterRelativeToScreen(-dx, -dy, 1 / scale);			
			restartThread();
		}
	}
}
