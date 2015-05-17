package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class GestureDetector implements OnTouchListener{
	final static String TAG = "GestureDetector";
		
	private GestureDetectorConsumer _gdc;
	
	private float _px0, _py0;

	private float _plen;
	private float _plena;
	
	private float _scale_max;

	public GestureDetector(GestureDetectorConsumer gdc, float scale_max){
		this._gdc = gdc;
		this._scale_max = scale_max;
		
		_px0 = 0;	
		_py0 = 0;
	
		_plen = 0;
		_plena = 0;
	}
	


	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:			
	    case MotionEvent.ACTION_POINTER_DOWN:
			_px0 = event.getX(0);
			_py0 = event.getY(0);

	    	_plen = _calcLen(event);
	    	_plena = 0;
			
			_gdc.moveStart(_px0, _py0);
			
			log("ACTION_DOWN");
			
			return true;

			
		case MotionEvent.ACTION_MOVE:
			_plena = _calcLen(event);
			
			_gdc.moveTo(_px0, _py0, event.getX() - _px0, event.getY() - _py0, _calcScale());

			return true;

			
		case MotionEvent.ACTION_UP:
	    case MotionEvent.ACTION_POINTER_UP:
			_gdc.moveEnd(_px0, _py0, event.getX() - _px0, event.getY() - _py0, _calcScale());
			
			return true;
		}

		return false;
	
	}
	
	
	
	private float _calcLen(MotionEvent event) {
		if (event.getPointerCount() < 2)
			return 0;
		
		float x1 = event.getX(0);
		float y1 = event.getY(0);
		
		float x2 = event.getX(1);
		float y2 = event.getY(1);
		
	    float x = x1 - x2;
	    float y = y1 - y2 ;
	    
	    return (float) Math.sqrt(x * x + y * y);
	}
	
	private float _calcScale(){
		if (_plen == 0 || _plena == 0)
			return 1;
		
		float scale = _plena / _plen;
		
		if (scale == 0)
			return 1;
		
		if (scale > _scale_max)
			return _scale_max;
		
		if (scale < -_scale_max)
			return - _scale_max;

		return scale;
	}
	

	
	private void log(String prefix){
		final String msg = prefix + ": x=" + _px0 + ", y=" + _py0 + ", len=" + _plen;	
		Log.v(TAG, msg);
	}
}

