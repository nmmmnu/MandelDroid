package nu.nmmm.android.fractal.color;

class FColorCosmos implements FColor{
	private boolean _neo;

	public FColorCosmos(boolean neo){
		this._neo = neo;
	}

	@Override
	public RGB convertColor(int color, int maxcolor, RGB rgb) {
		if ((color & 1) == 0)
			return rgb.setColorZero();

		int c = _neo ? maxcolor - color : maxcolor;

		return rgb.setColor(c, maxcolor);
	}
}
