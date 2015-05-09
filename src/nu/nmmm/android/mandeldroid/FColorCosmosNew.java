package nu.nmmm.android.mandeldroid;

public class FColorCosmosNew extends FColorCosmos{
	@Override
	protected int _transIt(int color, int maxcolor){
		return (int) (0xFF - color / (float) maxcolor * 0xFF);
	}
}
