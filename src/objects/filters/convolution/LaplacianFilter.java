package objects.filters.convolution;

public class LaplacianFilter extends AbstractConvolutionFilter {
	public static final double[][] LAPLACIAN_KERNEL1 = {
		{ 0, -1 ,  0},
		{-1,  4 , -1},
		{ 0, -1 ,  0}
	};
	public static final double[][] LAPLACIAN_KERNEL2 = {
		{-1, -1 , -1},
		{-1,  8 , -1},
		{-1, -1 , -1}
	};


	public LaplacianFilter(double[][] kernel) {
		this.kernel = kernel;
	}
}
