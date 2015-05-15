package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import nu.nmmm.android.mandelbrot.*;

public class MDView extends View implements Runnable, FractalManagerPlot{
	final private static boolean PIXEL_PREVIEW = ! true;
	final private static int PIXEL_PREVIEW_COLOR = Color.RED;
	
	final private static int COUNTER_REFRESH = 1024;
	
	private static int _counterRefresh = 0;

	private FractalManager _fractalManager;
	private FColor _fractalColor;

	private Bitmap _image;
	private Canvas _imageCanvas;
	private Paint _paint;
	
	// dummy used onDraw()
	private Paint _paint2 = new Paint();
	
	// gesture
	private float _gestureX = 0;
	private float _gestureY = 0;
	private float _gestureDrawX = 0;
	private float _gestureDrawY = 0;
	
	private int _height;
	private int _width;
	
	private Thread _thread = null;
	private boolean _threadRunning = false;

	public MDView(Context context, int width, int height) {
		super(context);
		
		this._width = width;
		this._height = height;
		
		this._image = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_8888);
		this._imageCanvas = new Canvas(this._image);
	
	    this._paint = new Paint();
	    this._paint.setAntiAlias(false);
	    
		this._fractalColor = new FColorStandard();
		//this._fractalColor = new FColorCosmosNew();

		MandelbrotMementoFactory memento = MandelbrotMementoFactory.PBS_HUMANOID_CREATURE_THE_ALIEN;
		
		FractalCalculator fractalCalc = new FractalCalculatorMandelbrot(FractalCalculatorMandelbrot.TYPE_CLASSIC, 64);
		this._fractalManager = new FractalManager(fractalCalc, _width, _height);
		this._fractalManager.setMemento( memento.getInstance() );
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

	//	this._changeSize(getWidth(), getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(_image, - _gestureDrawX, - _gestureDrawY, _paint2);
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		float rx = _gestureX - x;
		float ry = _gestureY - y;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			_gestureX = x;
			_gestureY = y;
			
			Log.v("touch", "ACTION_DOWN: " + Float.toString(x) + " / " + Float.toString(y));
			
			return true;

		case MotionEvent.ACTION_MOVE:
			_gestureDrawX = rx;
			_gestureDrawY = ry;
			
			postInvalidate();
			
			return true;

		case MotionEvent.ACTION_UP:
			Log.v("touch", "ACTION_UP: " + Float.toString(x) + " / " + Float.toString(y));
			Log.v("touch", "ACTION_UP: " + Float.toString(rx) + " / " + Float.toString(ry));

			_fractalManager.setCenterRelativeToScreen(rx, ry, 0);
			
			Bitmap image = _image.copy(_image.getConfig(), true);
			_imageCanvas.drawColor(Color.WHITE);
			_imageCanvas.drawBitmap(image, - _gestureDrawX, - _gestureDrawY, _paint2);
			
			restartThread();
			
			return true;
		}

		return false;
	}

	@Override
	public boolean putPixel(int x, int y, int color, int maxcolor, int squareSize) {
		int a = _fractalColor.convertColor(color, maxcolor);
		
		_paint.setARGB(0xFF, a, a, a);
		
		if (squareSize <= 1){
			_imageCanvas.drawPoint(x, y, _paint);
		}else{
			if (PIXEL_PREVIEW)
				_imageCanvas.drawPoint(x, y, _paint);
			else
				_imageCanvas.drawRect(x, y, x + squareSize, y + squareSize, _paint);
		}
		
		_counterRefresh += squareSize * squareSize;

		if (_counterRefresh > COUNTER_REFRESH){
			_counterRefresh = 0;

			this.postInvalidate();
		}
		
		return _threadRunning;
	}
	
	public void startThread(){
		if (_threadRunning )
			return;

		_threadRunning = true;

		_gestureDrawX = 0;
		_gestureDrawY = 0;

		if (PIXEL_PREVIEW)
			this._imageCanvas.drawColor(PIXEL_PREVIEW_COLOR);

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
}
