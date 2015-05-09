package nu.nmmm.android.mandeldroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MDView extends SurfaceView implements Runnable, FractalManagerPlot{
	private FractalManager _fractalManager;
	private FColor _color;

	private SurfaceHolder _holder;
	private Paint _paint;
	
	private boolean _running = false;
	private Thread _thread = null;
	private Canvas _canvas = null;
	
	final float FX  = (float) 0.336;
	final float FY  = (float) 0.052;
	final float FHW = (float) 0.012;

	private static int __counter = 0;

	public MDView(Context context) {
		super(context);

		this._holder = getHolder();
		this._holder.setKeepScreenOn(true);
		
		this._color = new FColorStandard();
		//_color = new FColorCosmosNew();

		Fractal fractal = new FractalMandelbrot(FractalMandelbrot.TYPE_CLASSIC, 128);
		
		this._fractalManager = new FractalManager(fractal);
		
	    this._paint = new Paint();
	    this._paint.setAntiAlias(false);
	}

	
	@Override
	public void plot(float x, float y, int color, int maxcolor) {
		if (!_running)
			return;

		int a = _color.convertColor(color, maxcolor);
		
		_paint.setARGB(0xFF, a, a, a);
		
		_canvas.drawPoint(x, y, _paint);
		
		if (++__counter > 10000){
			__counter = 0;

		//	_holder.unlockCanvasAndPost(_canvas);
		//	_canvas = _holder.lockCanvas();
		}
		
	}
	
	@Override
	public void run() {
		while(_running){
			if (!_holder.getSurface().isValid())
				continue;

			_canvas = _holder.lockCanvas();
			
			_fractalManager.generate(this, FX, FY, FHW);
		
			_holder.unlockCanvasAndPost(_canvas);
			
			break;
		}
		
		_canvas = null;
	}

	public void pause(){
		if (!_running)
			return;

		_running = false;
		
		try {
			_thread.join();
		} catch (InterruptedException e) {
		}
		
		_thread = null;
	}
	
	public void resume(){
		if (_running)
			return;
		
		_running = true;
		
		_thread = new Thread(this);
		_thread.start();
	}


}
