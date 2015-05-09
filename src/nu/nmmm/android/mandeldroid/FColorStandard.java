package nu.nmmm.android.mandeldroid;

public class FColorStandard implements FColor{
	private int _transIt(int color, int it){
		return (int) (color / (float) it * 0xFF);
	}
	
	@Override
	public int convertColor(int color, int maxcolor) {
		int a = _transIt(color, maxcolor);
		
		return a;
	}
}
