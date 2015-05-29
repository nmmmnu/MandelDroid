package nu.nmmm.android.mandelbrot.color;

class FColorCosmos implements FColor{
	private boolean _neo;
	
	public FColorCosmos(boolean neo){
		this._neo = neo;
	}
	
	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		if ((color & 1) == 0){
			rgb.setColorZero();
		
			return rgb;
		}
		
		int c = _neo ? maxcolor - color : maxcolor;
			
		rgb.setColor(c, maxcolor);
		
		return rgb;
	}
}
