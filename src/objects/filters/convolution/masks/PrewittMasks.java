package objects.filters.convolution.masks;

public interface PrewittMasks {
	// The horizontal and vertical kernels for the prewitt operator
	public static final double[][] HORIZONTAL_PREWITT_KERNEL = {
		{ 1,  1,   1},
		{ 0,  0 ,  0},
		{-1, -1 , -1}
	};
	public static final double[][] VERTICAL_PREWITT_KERNEL = {
		{1, 0, -1},
		{1, 0, -1},
		{1, 0, -1}
	};
}
