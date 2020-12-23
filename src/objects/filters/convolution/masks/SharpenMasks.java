package objects.filters.convolution.masks;

public interface SharpenMasks {
	// Some common kernels for image sharpening
	public static final String SHARPEN_LOW_NAME = "3x3 Low intensity Sharpening Mask";
	public static final double[][] SHARPEN_KERNEL_LOW = {
		{ 0, -1,  0},
		{-1,  5, -1},
		{ 0, -1,  0}
	};

	public static final String SHARPEN_HIGH_NAME = "3x3 High intensity Sharpening Mask";
	public static final double[][] SHARPEN_KERNEL_HIGH = {
		{-1, -1, -1},
		{-1,  9, -1},
		{-1, -1, -1}
	};
}
