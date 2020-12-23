package objects.filters.convolution.masks;

public interface LaplacianMasks {
	// The discrete approximations for the laplacian kernels
	public static final String LAPLACIAN_1_NAME = "3x3 Negative Laplacian Mask 1";
	public static final double[][] LAPLACIAN_1_KERNEL = {
		{ 0, -1 ,  0},
		{-1,  4 , -1},
		{ 0, -1 ,  0}
	};
	public static final String LAPLACIAN_2_NAME = "3x3 Negative Laplacian Mask 2";
	public static final double[][] LAPLACIAN_2_KERNEL = {
		{-1, -1 , -1},
		{-1,  8 , -1},
		{-1, -1 , -1}
	};
}
