package objects.filters.convolution.masks;

public interface BlurMasks {
	// Some common kernels for the blur effect
	public static final String BOX_BLUR_3_NAME = "3x3 Box Blur Mask";
	public static final double[][] BOX_BLUR_3_KERNEL = {
		{ 1/9.0, 1/9.0, 1/9.0},
		{ 1/9.0, 1/9.0, 1/9.0},
		{ 1/9.0, 1/9.0, 1/9.0}
	};

	public static final String BOX_BLUR_5_NAME = "5x5 Box Blur Mask";
	public static final double[][] BOX_BLUR_5_KERNEL = {
		{ 1/25.0, 1/25.0, 1/25.0, 1/25.0, 1/25.0},
		{ 1/25.0, 1/25.0, 1/25.0, 1/25.0, 1/25.0},
		{ 1/25.0, 1/25.0, 1/25.0, 1/25.0, 1/25.0},
		{ 1/25.0, 1/25.0, 1/25.0, 1/25.0, 1/25.0},
		{ 1/25.0, 1/25.0, 1/25.0, 1/25.0, 1/25.0}
	};

	public static final String GAUSSIAN_BLUR_3_NAME = "3x3 Gaussian Blur Mask";
	public static final double[][] GAUSSIAN_BLUR_3_KERNEL = {
		{ 1/16.0, 2/16.0, 1/16.0},
		{ 2/16.0, 4/16.0, 2/16.0},
		{ 1/16.0, 2/16.0, 1/16.0}
	};

	public static final String GAUSSIAN_BLUR_5_NAME = "5x5 Gaussian Blur Mask";
	public static final double[][] GAUSSIAN_BLUR_5_KERNEL = {
		{1/256.0,  4/256.0,  6/256.0,  4/256.0, 1/256.0},
		{4/256.0, 16/256.0, 24/256.0, 16/256.0, 4/256.0},
		{6/256.0, 24/256.0, 36/256.0, 24/256.0, 6/256.0},
		{4/256.0, 16/256.0, 24/256.0, 16/256.0, 4/256.0},
		{1/256.0,  4/256.0,  6/256.0,  4/256.0, 1/256.0}
	};
}
