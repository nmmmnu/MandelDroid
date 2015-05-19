package nu.nmmm.android.mandeldroid;

public interface MyGestureDetectorConsumer {
	public void touchMove(float x, float y, float dx, float dy, float scale);
	public void touchMoveBegin(float x, float y);
	public void touchMoveEnd(float x, float y, float dx, float dy, float scale);
}
