package objects.filters.convolution;

public class SobelFilter extends AbstractConvolutionFilter {
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

	public SobelFilter(double[][] kernel) { this.kernel = kernel; }
}
