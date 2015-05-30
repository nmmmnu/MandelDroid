package nu.nmmm.android.fractal;

import nu.nmmm.android.fractal.calculator.FCalculatorFactory;

public class MementoFactory {
	public final static int MB_BIG_PICTURE						= 101;
	public final static int MB_BULB_MANDELBROT					= 102;
	public final static int MB_ELEPHANT_VALLEY					= 103;
	public final static int MB_SEAHORSE_VALLEY					= 104;
	public final static int MB_TRIPPLE_SPIRAL					= 105;
	public final static int MB_IMPERIAL_ORB_VALLEY				= 106;

	public final static int MB_MICRO_MANDELBROT					= 107;
	public final static int MB_MICRO_MANDELBROT_WITH_ANTENNA	= 108;


	
	public final static int BS_BIG_PICTURE						= 201;
	public final static int BS_SHIP_IN_ARMADA					= 202;
	public final static int BS_MYSTERIOUS_LADY					= 203;
	public final static int BS_HIDDEN_TREASURE1					= 204;
	public final static int BS_HIDDEN_FOREST1					= 205;
	public final static int BS_HIDDEN_TREASURE2					= 206;
	public final static int BS_OVALS							= 207;
	
	public final static int BS_HIDDEN_FOREST2					= 208;

	public final static int BS_FLOWER1							= 209;
	public final static int BS_FLOWER2							= 210;
	public final static int BS_FLOWER3							= 211;
	public final static int BS_FLOWER4							= 212;
	public final static int BS_MICROSHIP						= 213;
	

	
	public final static int PBS_BIG_PICTURE						= 301;
	public final static int PBS_FIRST_IFS_TREE_CARDIOUD			= 302;
	public final static int PBS_RHOMBUS_INSIDE_FIRST_CARDIOUD	= 303;
	public final static int PBS_COMMON_IFS_TREE_CARDIOUD		= 304;
	public final static int PBS_SPIRAL_GALAXY					= 305;
	public final static int PBS_HUMANOID_CREATURE_THE_ALIEN		= 306;
	public final static int PBS_HUMANOID_CREATURE_THE_MINOTAUR	= 307;
	public final static int PBS_HUMANOID_CREATURE_THE_SKULL		= 308;
	public final static int PBS_BUTTERFLIES_BIG_PICTURE			= 309;
	public final static int PBS_BUTTERFLY						= 310;
	public final static int PBS_APOLONIAN_GASKET				= 311;
	public final static int PBS_SIERPINSKI						= 312;
	public final static int PBS_IFS_SQUARE						= 313;
	public final static int PBS_BAOBAB							= 314;

	public final static int PBS_MICRO_PBS						= 315;
	public final static int PBS_HIDDEN_FLAMES					= 316;
	public final static int PBS_GATE							= 317;
	public final static int PBS_ARMADA							= 318;
	public final static int PBS_MIRROR							= 319;
	public final static int PBS_PENDANT							= 320;
	
	public final static int PBS_SWIRL							= 321;
	public final static int PBS_MINI_PM							= 322;
	public final static int PBS_CARDIOID_PM						= 323;
	public final static int PBS_GRID_PM							= 324;
	public final static int PBS_MASQUERADE						= 325;
	public final static int PBS_LE_ROI_SOLEIL					= 326;


	
	public final static int PM_BIG_PICTURE						= 401;
	public final static int PM_DRAKULA							= 402;
	public final static int PM_SQUID							= 403;
	public final static int PM_GROTESQUE						= 404;
	public final static int PM_RANDOM_SHAPE						= 405;
	public final static int PM_SKULL_VALLEY						= 406;

	public final static int PM_BLOB								= 407;
	public final static int PM_CHAINS							= 408;
	public final static int PM_VISAGE_OF_WAR					= 409;
	public final static int PM_FIRE								= 410;
	public final static int PM_CARDIOID							= 411;
	public final static int PM_SWIRL							= 412;

	// ====================================
	
	private final static int T_MB	=	FCalculatorFactory.TYPE_MANDELBROT;
	private final static int T_BS	=	FCalculatorFactory.TYPE_BURNINGSHIP;
	private final static int T_PBS	=	FCalculatorFactory.TYPE_PERPENDICULAR_BURNINGSHIP;
	private final static int T_PM	=	FCalculatorFactory.TYPE_PERPENDICULAR_MANDELBROT;

	private final static int IT_LO	=	64;
	private final static int IT_ST	=	256;
	private final static int IT_MD	=	1024;
	private final static int IT_HI	=	2048;
	
	public static Memento getInstance(int type){
		switch(type){

		case MB_BIG_PICTURE						: return _m(T_MB,	IT_ST,	-0.5,			+0.0,			1.8			);
		case MB_BULB_MANDELBROT					: return _m(T_MB,	IT_ST,	-1.77,			+0.0,			0.06		);
		case MB_ELEPHANT_VALLEY					: return _m(T_MB,	IT_ST,	+0.336,			+0.052,			0.012		);
		case MB_SEAHORSE_VALLEY					: return _m(T_MB,	IT_ST,	-0.747,			+0.102,			0.005		);
		case MB_TRIPPLE_SPIRAL					: return _m(T_MB,	IT_ST,	-0.041,			+0.682,			0.008		);
		case MB_IMPERIAL_ORB_VALLEY				: return _m(T_MB,	IT_ST,	-1.37,			+0.04,			0.018		);

		case MB_MICRO_MANDELBROT				: return _m(IT_MD,	IT_ST,	-1.9449855,		+0.0,			0.000001	);
		case MB_MICRO_MANDELBROT_WITH_ANTENNA	: return _m(IT_MD,	IT_ST,	-1.9449855,		+0.0,			0.0001		);

		// ====================================

		case BS_BIG_PICTURE						: return _m(T_BS,	IT_ST,	-0.5,			-0.5,			1.8			);
		case BS_SHIP_IN_ARMADA					: return _m(T_BS,	IT_LO,	-1.77,			+0.0,			0.06		);
		case BS_MYSTERIOUS_LADY					: return _m(T_BS,	IT_LO,	+0.0,			-1.015,			0.015		);
		case BS_HIDDEN_TREASURE1				: return _m(T_BS,	IT_HI,	-1.73723891,	-0.028338253,	0.000000005	);
		case BS_HIDDEN_FOREST1					: return _m(T_BS,	IT_HI,	-1.73723,		-0.028248,		0.00001		);
		case BS_HIDDEN_TREASURE2				: return _m(T_BS,	IT_HI,	-1.7372348,		-0.028248,		0.0000005	);
		case BS_OVALS							: return _m(T_BS,	IT_ST,	-1.7364403,		-0.0280156,		0.000003	);

		case BS_HIDDEN_FOREST2					: return _m(T_BS,	IT_MD,	-1.939935,		-0.00124,		0.000006	);

		case BS_FLOWER1							: return _m(T_BS,	IT_MD,	-1.7397,		0,				0.0005		);
		case BS_FLOWER2							: return _m(T_BS,	IT_MD,	-1.749319,		0.0000593,		0.000002	);
		case BS_FLOWER3							: return _m(T_BS,	IT_MD,	-1.748764,		0.0000057,		0.00001		);
		case BS_FLOWER4							: return _m(T_BS,	IT_MD,	-1.7433355,		0.000038,		0.00001		);
		case BS_MICROSHIP						: return _m(T_BS,	IT_MD,	-1.4730195,		0.000006,		0.0000008	);

		// ====================================

		case PBS_BIG_PICTURE					: return _m(T_PBS,	IT_ST,	-0.5,			-0.5,			1.8			);
		case PBS_FIRST_IFS_TREE_CARDIOUD		: return _m(T_PBS,	IT_ST,	-0.06415,		+0.64968,		0.00015		);
		case PBS_RHOMBUS_INSIDE_FIRST_CARDIOUD	: return _m(T_PBS,	IT_ST,	-0.064154,		+0.649703,		0.00001		);
		case PBS_COMMON_IFS_TREE_CARDIOUD		: return _m(T_PBS,	IT_ST,	-0.0699,		+0.6498,		0.0004		);
		case PBS_SPIRAL_GALAXY					: return _m(T_PBS,	IT_ST,	-0.074,			+0.65071,		0.0003		);
		case PBS_HUMANOID_CREATURE_THE_ALIEN	: return _m(T_PBS,	IT_ST,	-0.0795,		+0.659,			0.0005		);
		case PBS_HUMANOID_CREATURE_THE_MINOTAUR	: return _m(T_PBS,	IT_ST,	-0.08183,		+0.64945,		0.00005		);
		case PBS_HUMANOID_CREATURE_THE_SKULL	: return _m(T_PBS,	IT_ST,	-0.09675,		+0.65246,		0.00005		);
		case PBS_BUTTERFLIES_BIG_PICTURE		: return _m(T_PBS,	IT_ST,	-0.23218,		+0.70837,		0.001		);
		case PBS_BUTTERFLY						: return _m(T_PBS,	IT_ST,	-0.23218,		+0.70837,		0.00005		);
		case PBS_APOLONIAN_GASKET				: return _m(T_PBS,	IT_MD,	+0.12192,		+0.59345,		0.00002		);
		case PBS_SIERPINSKI						: return _m(T_PBS,	IT_MD,	+0.122671,		+0.59333,		0.000002	);
		case PBS_IFS_SQUARE						: return _m(T_PBS,	IT_ST,	-0.2622,		+0.78855,		0.00001		);
		case PBS_BAOBAB							: return _m(T_PBS,	IT_MD,	-1.476556408,	+0.002558,		0.0000001	);

		case PBS_MICRO_PBS						: return _m(T_PBS,	IT_MD,	-1.9449855,		+0.0,			0.000001	);
		case PBS_HIDDEN_FLAMES					: return _m(T_PBS,	IT_MD,	-1.502,			+0.002,			0.01		);
		case PBS_GATE							: return _m(T_PBS,	IT_MD,	-1.7589025,		-0.006431,		0.0000025	);
		case PBS_ARMADA							: return _m(T_PBS,	IT_MD,	-1.758361,		-0.006285,		0.000016	);
		case PBS_MIRROR							: return _m(T_PBS,	IT_MD,	-1.758406,		-0.006294,		0.000004	);
		case PBS_PENDANT						: return _m(T_PBS,	IT_MD,	-1.62617405,	-0.00123832,	0.000001	);

		case PBS_SWIRL							: return _m(T_PBS,	IT_MD,	0.231664,		0.534623,		0.0003		);
		case PBS_MINI_PM						: return _m(T_PBS,	IT_MD,	-1.7357397,		-0.0001173,		0.000005	);
		case PBS_CARDIOID_PM					: return _m(T_PBS,	IT_MD,	-1.735739470,	-0.000117546,	0.00000001	);
		case PBS_GRID_PM						: return _m(T_PBS,	IT_MD,	-1.721900635,	-0.00042253,	0.00000002	);
		case PBS_MASQUERADE						: return _m(T_PBS,	IT_MD,	0.35635,		0.34722,		0.00005		);
		case PBS_LE_ROI_SOLEIL					: return _m(T_PBS,	IT_MD,	0.122117,		0.592448,		0.0001		);

		// ====================================

		case PM_BIG_PICTURE						: return _m(T_PM,	IT_MD,	-0.5,			+0.0,			1.8				);
		case PM_DRAKULA							: return _m(T_PM,	IT_MD,	-0.456126,		+0.784448,		0.0004			);
		case PM_SQUID							: return _m(T_PM,	IT_MD,	-0.798464,		+0.178716,		0.0015			);
		case PM_GROTESQUE						: return _m(T_PM,	IT_MD,	-0.760193,		+0.084931,		0.0003			);
		case PM_RANDOM_SHAPE					: return _m(T_PM,	IT_MD,	-0.348205,		-0.860702,		0.0005			);
		case PM_SKULL_VALLEY					: return _m(T_PM,	IT_MD,	+0.706316,		+0.851956,		0.0001			);

		case PM_BLOB							: return _m(T_PM,	IT_MD,	-0.748880,		0.122827,		0.0003			);
		case PM_CHAINS							: return _m(T_PM,	IT_ST,	 0.655084,		0.913451,		0.000005		);
		case PM_VISAGE_OF_WAR					: return _m(T_PM,	IT_MD,	-1.78508988223,	0.00003814529,	0.00000000005	);
		case PM_FIRE							: return _m(T_PM,	IT_MD,	-1.762839,		-0.012836,		0.00001			);
		case PM_CARDIOID						: return _m(T_PM,	IT_MD,	-1.7628397869,	-0.0128296552,	0.0000000005	);
		case PM_SWIRL							: return _m(T_PM,	IT_MD,	-0.46006,		0.77812,		0.00005			);

		// ====================================

		default: return _m2();
		}
	}

	private static Memento _m(int type, int iter, double x, double y, double hwx){
		return new Memento(type, iter, x, y, hwx);
	}

	private static Memento _m2() {
		return _m(T_MB,	IT_ST, 0, 0, 2.2);
	}
}
