package nu.nmmm.android.mandelbrot;

public class MandelbrotMementoFactory {
	public final static int MB_BIG_PICTURE								= 101;
	public final static int MB_BULB_MANDELBROT							= 102;
	public final static int MB_ELEPHANT_VALLEY							= 103;
	public final static int MB_SEAHORSE_VALLEY							= 104;
	public final static int MB_TRIPPLE_SPIRAL							= 105;
	public final static int MB_IMPERIAL_ORB_VALLEY						= 106;
	
	public final static int BS_BIG_PICTURE								= 201;
	public final static int BS_SHIP_IN_ARMADA							= 202;
	public final static int BS_MYSTERIOUS_LADY							= 203;
	public final static int BS_HIDDEN_TREASURE1							= 204;
	public final static int BS_HIDDEN_FOREST							= 205;
	public final static int BS_HIDDEN_TREASURE2							= 206;
	public final static int BS_OVALS									= 207;
	
	public final static int PBS_BIG_PICTURE								= 301;
	public final static int PBS_FIRST_IFS_TREE_CARDIOUD					= 302;
	public final static int PBS_RHOMBUS_INSIDE_FIRST_IFS_TREE_CARDIOUD	= 303;
	public final static int PBS_COMMON_IFS_TREE_CARDIOUD				= 304;
	public final static int PBS_SPIRAL_GALAXY							= 305;
	public final static int PBS_HUMANOID_CREATURE_THE_ALIEN				= 306;
	public final static int PBS_HUMANOID_CREATURE_THE_MINOTAUR			= 307;
	public final static int PBS_HUMANOID_CREATURE_THE_SKULL				= 308;
	public final static int PBS_BUTTERFLIES_BIG_PICTURE					= 309;
	public final static int PBS_BUTTERFLY								= 310;
	public final static int PBS_APOLONIAN_GASKET						= 311;
	public final static int PBS_SIERPINSKI								= 312;
	public final static int PBS_IFS_SQUARE								= 313;
	public final static int PBS_BAOBAB									= 314;

	private final static int TYPE_MB		=	FractalCalculatorMandelbrot.TYPE_CLASSIC;
	private final static int TYPE_BS		=	FractalCalculatorMandelbrot.TYPE_BURNINGSHIP;
	private final static int TYPE_PP		=	FractalCalculatorMandelbrot.TYPE_PERPENDICULAR;

	private final static int ITER_LO	=	64;
	private final static int ITER_ST	=	256;
	private final static int ITER_MD	=	1024;
	private final static int ITER_HI	=	2048;
	
	public static Memento getInstance(int type){
		switch(type){
			case MB_BIG_PICTURE								: return _m(TYPE_MB,	ITER_ST,	-0.500000000, +0.000000000, 1.800000000);
			case MB_BULB_MANDELBROT							: return _m(TYPE_MB,	ITER_ST,	-1.770000000, +0.000000000, 0.060000000);
			case MB_ELEPHANT_VALLEY							: return _m(TYPE_MB,	ITER_ST,	+0.336000000, +0.052000000, 0.012000000);
			case MB_SEAHORSE_VALLEY							: return _m(TYPE_MB,	ITER_ST,	-0.747000000, +0.102000000, 0.005000000);
			case MB_TRIPPLE_SPIRAL							: return _m(TYPE_MB,	ITER_ST,	-0.041000000, +0.682000000, 0.008000000);
			case MB_IMPERIAL_ORB_VALLEY						: return _m(TYPE_MB,	ITER_ST,	-1.370000000, +0.040000000, 0.018000000);

			case BS_BIG_PICTURE								: return _m(TYPE_BS,	ITER_ST,	-0.500000000, -0.500000000, 1.800000000);
			case BS_SHIP_IN_ARMADA							: return _m(TYPE_BS,	ITER_LO,	-1.770000000, +0.000000000, 0.060000000);
			case BS_MYSTERIOUS_LADY							: return _m(TYPE_BS,	ITER_LO,	+0.000000000, -1.015000000, 0.015000000);
			case BS_HIDDEN_TREASURE1						: return _m(TYPE_BS,	ITER_HI,	-1.737238910, -0.028338253, 0.000000005);
			case BS_HIDDEN_FOREST							: return _m(TYPE_BS,	ITER_HI,	-1.737230000, -0.028248000, 0.000010000);
			case BS_HIDDEN_TREASURE2						: return _m(TYPE_BS,	ITER_HI,	-1.737234800, -0.028248000, 0.000000500);
			case BS_OVALS									: return _m(TYPE_BS,	ITER_ST,	-1.736440300, -0.028015600, 0.000003000);
			
			case PBS_BIG_PICTURE							: return _m(TYPE_PP,	ITER_ST,	-0.500000000, -0.500000000, 1.800000000);
			case PBS_FIRST_IFS_TREE_CARDIOUD				: return _m(TYPE_PP,	ITER_ST,	-0.064150000, +0.649680000, 0.000150000);
			case PBS_RHOMBUS_INSIDE_FIRST_IFS_TREE_CARDIOUD	: return _m(TYPE_PP,	ITER_ST,	-0.064154000, +0.649703000, 0.000010000);
			case PBS_COMMON_IFS_TREE_CARDIOUD				: return _m(TYPE_PP,	ITER_ST,	-0.069900000, +0.649800000, 0.000400000);
			case PBS_SPIRAL_GALAXY							: return _m(TYPE_PP,	ITER_ST,	-0.074000000, +0.650710000, 0.000300000);
			case PBS_HUMANOID_CREATURE_THE_ALIEN			: return _m(TYPE_PP,	ITER_ST,	-0.079500000, +0.659000000, 0.000500000);
			case PBS_HUMANOID_CREATURE_THE_MINOTAUR			: return _m(TYPE_PP,	ITER_ST,	-0.081830000, +0.649450000, 0.000050000);
			case PBS_HUMANOID_CREATURE_THE_SKULL			: return _m(TYPE_PP,	ITER_ST,	-0.096750000, +0.652460000, 0.000050000);
			case PBS_BUTTERFLIES_BIG_PICTURE				: return _m(TYPE_PP,	ITER_ST,	-0.232180000, +0.708370000, 0.001000000);
			case PBS_BUTTERFLY								: return _m(TYPE_PP,	ITER_ST,	-0.232180000, +0.708370000, 0.000050000);
			case PBS_APOLONIAN_GASKET						: return _m(TYPE_PP,	ITER_MD,	+0.121920000, +0.593450000, 0.000020000);
			case PBS_SIERPINSKI								: return _m(TYPE_PP,	ITER_MD,	+0.122671000, +0.593330000, 0.000002000);
			case PBS_IFS_SQUARE								: return _m(TYPE_PP,	ITER_ST,	-0.262200000, +0.788550000, 0.000010000);
			case PBS_BAOBAB									: return _m(TYPE_PP,	ITER_MD,	-1.476556408, +0.002558000, 0.000000100);
		
			default: return _m(TYPE_MB, ITER_ST, 0, 0, 2.2);
		}
	}

	private static Memento _m(int type, int iter, double x, double y, double hwx){
		return new Memento(type, iter, x, y, hwx);
	}
}
