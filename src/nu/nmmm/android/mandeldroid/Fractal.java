package nu.nmmm.android.mandeldroid;

public interface Fractal {
	int Z(float x, float y);

	int getIterations();
}
