package objects.filters.convolution;

public interface LaplacianMasks {
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
