package nu.nmmm.android.mandelbrot;

public enum MandelbrotMementoFactory {
	BIG_PICTURE 								(FractalCalculatorMandelbrot.TYPE_CLASSIC,         64, -0.500000000, +0.000000000, 1.800000000),
	BULB_MANDELBROT 							(FractalCalculatorMandelbrot.TYPE_CLASSIC,         64, -1.770000000, +0.000000000, 0.060000000),
	ELEPHANT_VALLEY								(FractalCalculatorMandelbrot.TYPE_CLASSIC,        256, +0.336000000, +0.052000000, 0.012000000),
	SEAHORSE_VALLEY								(FractalCalculatorMandelbrot.TYPE_CLASSIC,        256, -0.747000000, +0.102000000, 0.005000000),
	TRIPPLE_SPIRAL								(FractalCalculatorMandelbrot.TYPE_CLASSIC,        256, -0.041000000, +0.682000000, 0.008000000),
	IMPERIAL_ORB_VALLEY							(FractalCalculatorMandelbrot.TYPE_CLASSIC,        256, -1.370000000, +0.040000000, 0.018000000),
	
	BS_BIG_PICTURE								(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP,     64, -0.500000000, -0.500000000, 1.800000000),
	BS_SHIP_IN_ARMADA							(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP,     64, -1.770000000, +0.000000000, 0.060000000),
	BS_MYSTERIOUS_LADY							(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP,     64, +0.000000000, -1.015000000, 0.015000000),
//	BS_HIDDEN_TREASURE1							(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP,   4096, -1.737238910, -0.028338253,  0.000000005),
//	BS_HIDDEN_FOREST							(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP,   2048, -1.737230000, -0.028248000,  0.000010000),
//	BS_HIDDEN_TREASURE2							(FractalCalculatorMandelbrot.TYPE_BURNINGSHIP,   2048, -1.737234800, -0.028248000,  0.000000500),

	PBS_BIG_PICTURE								(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.500000000, -0.500000000, 1.800000000),
	PBS_FIRST_IFS_TREE_CARDIOUD					(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.064150000, +0.649680000, 0.000150000),
	PBS_RHOMBUS_INSIDE_FIRST_IFS_TREE_CARDIOUD	(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.064154000, +0.649703000, 0.000010000),
	PBS_COMMON_IFS_TREE_CARDIOUD				(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.069900000, +0.649800000, 0.000400000),
	PBS_SPIRAL_GALAXY							(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR, 1024, -0.074000000, +0.650710000, 0.000300000),
	PBS_HUMANOID_CREATURE_THE_ALIEN				(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.079500000, +0.659000000, 0.000500000),
	PBS_HUMANOID_CREATURE_THE_MINOTAUR			(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.081830000, +0.649450000, 0.000050000),
	PBS_HUMANOID_CREATURE_THE_SKULL				(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.096750000, +0.652460000, 0.000050000),
	PBS_BUTTERFLIES_BIG_PICTURE					(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.232180000, +0.708370000, 0.001000000),
	PBS_BUTTERFLY								(FractalCalculatorMandelbrot.TYPE_PERPENDICULAR,  256, -0.232180000, +0.708370000, 0.000050000);
	
	private Memento _m;

	MandelbrotMementoFactory(int type, int iter, float x, float y, float hw){
		this._m = new Memento(type, iter, x, y, hw);
	}
	
	MandelbrotMementoFactory(int type, int iter, double x, double y, double hw){
		this(type, iter, (float) x, (float) y, (float) hw);
	}
	
	public Memento getInstance(){
		return _m;
	}
}