package nu.nmmm.android.mandeldroid;

public interface GestureDetectorConsumer {
	public void moveStart(float x, float y);
	public void moveTo(float x, float y, float dx, float dy, float scale);
	public void moveEnd(float x, float y, float dx, float dy, float scale);

}
