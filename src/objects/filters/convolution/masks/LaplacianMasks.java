package objects.filters.convolution.masks;

public interface LaplacianMasks {
	// The discrete approximations for the laplacian kernels
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
}
