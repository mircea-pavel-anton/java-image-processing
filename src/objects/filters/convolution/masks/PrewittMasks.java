package objects.filters.convolution.masks;

public interface PrewittMasks {
	// The horizontal and vertical kernels for the prewitt operator
	public static final String HORIZONTAL_PREWITT_NAME = "3x3 Horizontal Prewitt Mask";
	public static final double[][] HORIZONTAL_PREWITT_KERNEL = {
		{ 1,  1,   1},
		{ 0,  0 ,  0},
		{-1, -1 , -1}
	};

	public static final String VERTICAL_PREWITT_NAME = "3x3 Vertical Prewitt Mask";
	public static final double[][] VERTICAL_PREWITT_KERNEL = {
		{1, 0, -1},
		{1, 0, -1},
		{1, 0, -1}
	};
}
