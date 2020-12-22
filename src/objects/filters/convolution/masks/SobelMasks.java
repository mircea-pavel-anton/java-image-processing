package objects.filters.convolution.masks;

public interface SobelMasks {
	// vertical and horizontal kernels for the sobel operator
	public static final double[][] VERTICAL_SOBEL_KERNEL = {
		{-1, 0, 1},
		{-2, 0, 2},
		{-1, 0, 1}
	};
	public static final double[][] HORIZONTAL_SOBEL_KERNEL = {
		{-1, -2, -1},
		{ 0,  0,  0},
		{ 1,  2,  1}
	};
}
