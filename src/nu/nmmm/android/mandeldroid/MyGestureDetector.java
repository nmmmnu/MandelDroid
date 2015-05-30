package nu.nmmm.android.mandeldroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

class MyGestureDetector implements OnTouchListener, ScaleGestureDetector.OnScaleGestureListener{
	@SuppressWarnings("unused")
	final private static String TAG = "MyGestureDetector";

	final private static int GD_INVALID_POINTER_ID = -1;

	private ScaleGestureDetector _sgd;

	MyGestureDetectorConsumer _consumer;
	private float _scaleMin;
	private float _scaleMax;

	private int _dragPointerID = GD_INVALID_POINTER_ID;

	float _clickX, _clickY;
	float _deltaX, _deltaY;


	public MyGestureDetector(Context context, MyGestureDetectorConsumer consumer, float scaleMin, float scaleMax){
		this._consumer = consumer;

		this._scaleMin = scaleMin;
		this._scaleMax = scaleMax;

		this._sgd = new ScaleGestureDetector(context, this);

		clear();
	}

	public MyGestureDetector(Context context,  MyGestureDetectorConsumer consumer, float scaleMax){
		this(context, consumer, 0, scaleMax);
	}

	public void clear(){
		_deltaX = 0;
		_deltaY = 0;
		_clickX = 0;
		_clickY = 0;

		_dragPointerID = GD_INVALID_POINTER_ID;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		_sgd.onTouchEvent(event);

		final int pointer;

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			pointer = event.getPointerId(0);

			// fix bug in Android Market
			if (pointer != GD_INVALID_POINTER_ID){
				_dragPointerID = pointer;
		    	_clickX = event.getX(pointer);
		    	_clickY = event.getY(pointer);

		    	_deltaX = 0;
		    	_deltaY = 0;

		    	_consumer.touchMoveBegin(_clickX, _clickY);
			}

	    	return true;

		case MotionEvent.ACTION_MOVE:
			pointer = event.findPointerIndex(_dragPointerID);

			if (pointer != GD_INVALID_POINTER_ID){
				_deltaX = event.getX(pointer) - _clickX;
				_deltaY = event.getY(pointer) - _clickY;

				_consumer.touchMove(_clickX, _clickY, _deltaX, _deltaY, getScale());
			}

			return false;

		case MotionEvent.ACTION_UP:
			pointer = event.findPointerIndex(_dragPointerID);
			if (pointer != GD_INVALID_POINTER_ID){
				_deltaX = event.getX(pointer) - _clickX;
				_deltaY = event.getY(pointer) - _clickY;

				_consumer.touchMoveEnd(_clickX, _clickY, _deltaX, _deltaY, getScale());
			}

			clear();

			return true;
		}

		return false;
	}

	public float getScale(){
		float scale = _sgd.getScaleFactor();

		if (scale > _scaleMax)
			scale = _scaleMax;

		if (scale > 0 && scale < _scaleMin)
			scale = _scaleMin;

		if (scale < -_scaleMax)
			scale = -_scaleMax;

		if (scale < 0 && scale > -_scaleMax)
			scale = -_scaleMax;

		if (scale == 0)
			scale = 1;

		return scale;
	}

	// Android's ScaleGestureDetector

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		_consumer.touchMove(_clickX, _clickY, _deltaX, _deltaY, getScale());

		return false;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		_consumer.touchMoveEnd(_clickX, _clickY, _deltaX, _deltaY, getScale());

		clear();
	}
}

