package objects.filters.convolution.masks;

public interface SobelMasks {
	// vertical and horizontal kernels for the sobel operator
	public static final String VERTICAL_SOBEL_NAME = "3x3 Vertical Sobel Mask";
	public static final double[][] VERTICAL_SOBEL_KERNEL = {
		{-1, 0, 1},
		{-2, 0, 2},
		{-1, 0, 1}
	};

	public static final String HORIZONTAL_SOBEL_NAME = "3x3 Horizontal Sobel Mask";
	public static final double[][] HORIZONTAL_SOBEL_KERNEL = {
		{-1, -2, -1},
		{ 0,  0,  0},
		{ 1,  2,  1}
	};
}
