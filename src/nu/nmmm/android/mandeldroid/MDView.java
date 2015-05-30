package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import nu.nmmm.android.fractal.*;
import nu.nmmm.android.fractal.color.FColor;
import nu.nmmm.android.fractal.color.FColorFactory;
import nu.nmmm.android.fractal.color.RGB;

class MDView extends View implements Runnable, FManagerPlot, MyGestureDetectorConsumer {
	@SuppressWarnings("unused")
	final private static String TAG = "MDView";
	
	private int PIXEL_DEBUG_PREVIEW_COLOR = Color.RED;
	
	final private static int COUNTER_REFRESH = 1024;
	
	final private static float SCALE_MIN = 0;
	final private static float SCALE_MAX = 5;
		
	private static int _counterRefresh = 0;

	private MyGestureDetector _mgd;
	
	FManager _fractalManager;
	private FColor _fractalColor;

	private Bitmap _image;
	private Canvas _imageCanvas;
	private Paint _paint;
	private RGB _rgb = new RGB();
	
	// dummy used onDraw()
	private Paint _paint2;
	
	private int _height;
	private int _width;
	
	private boolean _pixelDebugPreview =  false;
		
	@SuppressLint("ClickableViewAccessibility")
	public MDView(Context context, int width, int height, int fractalType, int fractalColor) {
		super(context);
		
		_mgd = new MyGestureDetector(context, this, SCALE_MIN, SCALE_MAX);
		this.setOnTouchListener(_mgd);
		
		this._width = width;
		this._height = height;
		
		this._image = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_8888);
		this._imageCanvas = new Canvas(this._image);
	
	    this._paint = new Paint();
	   	this._paint.setAntiAlias(false);
	    
	    this._paint2 = new Paint();
	    this._paint2.setAntiAlias(false);
    	this._paint2.setColor(PIXEL_DEBUG_PREVIEW_COLOR);
	    
    	this.setFractalColor(fractalColor);

		this._fractalManager = new FManager(fractalType, _width, _height);
	}
	
	public void setPixelDebugPreview(boolean pixelDebugPreview){
		this._pixelDebugPreview = pixelDebugPreview;
	}
	
	public void setFractalMemento(Memento memento){
		this._fractalManager.setMemento( memento );	
	}
	
	public void setFractalIterations(int iterations){
		this._fractalManager.setIterations(iterations); 
	}

	public void setFractalType(int type){
		this._fractalManager.setType(type); 
	}
	
	public void setFractalColor(int fractalColor){
		this._fractalColor = FColorFactory.getInstance(fractalColor);;
	}
	
	// FractalManagerPlot
	
	private Thread _thread = null;
	private boolean _threadRunning = false;

	@Override
	public boolean putPixel(int x, int y, int color, int maxcolor, int squareSize) {
		_fractalColor.convertColor(color, maxcolor, _rgb);
		
		_paint.setARGB(0xFF, _rgb.r, _rgb.g, _rgb.b);
		
		if (squareSize <= 1){
			_imageCanvas.drawPoint(x, y, _paint);
		}else{
			if (! _pixelDebugPreview){
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
	
	
	void startThread(){
		if (_threadRunning )
			return;

		_threadRunning = true;

		_thread = new Thread(this);
		_thread.start();	
	}

	void stopThread(){
		if (! _threadRunning )
			return;
		
		_threadRunning = false;
		
		try {
			_thread.join(); 
		} catch (InterruptedException e) {
			// none
		}
		
		_thread = null;
	}
	
	@Override
	public void run() {
		_fractalManager.generateSmooth(this);
		
		this.postInvalidate();

		_threadRunning = false;
	}

	public void refresh(){
		stopThread();
		startThread();
	}
	
	// MyGestureDetectorConsumer
	
	float _gScale = 1;
	float _gDeltaX = 0;
	float _gDeltaY = 0;
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.scale(_gScale, _gScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
		canvas.drawBitmap(_image, _gDeltaX, _gDeltaY, _paint2);
		canvas.restore();
	}
	
	@Override
	public void touchMove(float x, float y, float dx, float dy, float scale){
		_gDeltaX = dx;
		_gDeltaY = dy;
		_gScale = scale;
		
		postInvalidate();
	}
	
	@Override
	public void touchMoveBegin(float x, float y){
	}
	
	@Override
	public void touchMoveEnd(float x, float y, float dx, float dy, float scale){				
		_gScale = 1;
		_gDeltaX = 0;
		_gDeltaY = 0;
		
		_fractalManager.setCenterRelativeToScreen(-dx, -dy, 1 / scale);	
		refresh();
	}

	// Menu
	
	public void resetCoordinates() {
		_fractalManager.setCenter(0, 0, 2.2);
	}

	public double[] getCoordinate() {
		return _fractalManager.getCenter();
	}

	public Bitmap getImage(){
		return _image;
	}
}
