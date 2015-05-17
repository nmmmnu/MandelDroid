package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import nu.nmmm.android.mandelbrot.*;

class MDView extends View implements Runnable, FractalManagerPlot, ScaleGestureDetector.OnScaleGestureListener {
	final private static String TAG = "MDView";
	
	final private static boolean PIXEL_DEBUG_PREVIEW =  true;
	final private static int PIXEL_DEBUG_PREVIEW_COLOR = Color.RED;
	
	final private static int COUNTER_REFRESH = 1024;
	
	final private static float SCALE_MAX = 5;
		
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
	
	private ScaleGestureDetector _sgd;

	@SuppressLint("ClickableViewAccessibility")
	public MDView(Context context, int width, int height) {
		super(context);
		
		this._sgd = new ScaleGestureDetector(context, this);
		//this.setOnTouchListener(this);
		
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

		MandelbrotMementoFactory memento = MandelbrotMementoFactory.PBS_COMMON_IFS_TREE_CARDIOUD;
		
		FractalCalculator fractalCalc = new FractalCalculatorMandelbrot(FractalCalculatorMandelbrot.TYPE_CLASSIC, 256);
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

	
	final private static int GD_INVALID_POINTER_ID = -1;
	
	private float _gdClickX, _gdClickY;
	private float _gdDeltaX, _gdDeltaY;
	private int _gdDragPointerID = GD_INVALID_POINTER_ID;
	
	private float _gdScale = 1;
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.scale(_gdScale, _gdScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
		canvas.drawBitmap(_image, _gdDeltaX, _gdDeltaY, _paint2);
		canvas.restore();
	}

	
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		_sgd.onTouchEvent(event);
		
		int pointer;
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			_gdDragPointerID = event.getPointerId(0);
	    	_gdClickX = event.getX();
	    	_gdClickY = event.getY();
	    	
	    	_gdDeltaX = 0;
	    	_gdDeltaY = 0;
	    	
	    	return true;

		case MotionEvent.ACTION_MOVE:
			pointer = event.findPointerIndex(_gdDragPointerID);
			if (pointer != GD_INVALID_POINTER_ID){
				_gdDeltaX = event.getX(pointer) - _gdClickX;
				_gdDeltaY = event.getY(pointer) - _gdClickY;
	
		    	_touchMove();
			}
			
			return false;

		case MotionEvent.ACTION_UP:
			pointer = event.findPointerIndex(_gdDragPointerID);
			if (pointer != GD_INVALID_POINTER_ID){
				_gdDeltaX = event.getX(pointer) - _gdClickX;
				_gdDeltaY = event.getY(pointer) - _gdClickY;
	
				_touchMoveEnd();
			}
			
			_gdDeltaX = 0;
			_gdDeltaY = 0;
			_gdClickX = 0;
			_gdClickY = 0;
			
			_gdScale = 1;
			
			_gdDragPointerID = GD_INVALID_POINTER_ID;

			return true;
		}
		
	    return false;
	}
	
	private void _touchMove(){
		_gdScale = _getScale();
		
		Log.i(TAG, " dx=" + (_gdClickX + _gdDeltaX) + " dy=" + (_gdClickY + _gdDeltaY) + " scale=" + _gdScale); 
		
		postInvalidate();
	}
	
	private void _touchMoveEnd(){		
		float scale = _getScale();
		
		_fractalManager.setCenterRelativeToScreen(-_gdDeltaX, -_gdDeltaY, 1 / scale);	
		
		restartThread();
	}

	private float _getScale(){
		float scale = _sgd.getScaleFactor();
		
		if (scale > SCALE_MAX)
			scale = SCALE_MAX;

		if (scale < -SCALE_MAX)
			scale = -SCALE_MAX;
		
		if (scale == 0)
			scale = 1;

		return scale;
	}
	
	// ScaleGestureDetector
	
	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}
	
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		_touchMove();
		return false;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		_touchMoveEnd();
	}
}
