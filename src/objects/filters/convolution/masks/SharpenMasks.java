package objects.filters.convolution.masks;

public interface SharpenMasks {
	// Some common kernels for image sharpening
	public static final double[][] SHARPEN_KERNEL_3_LOW = {
		{ 0, -1,  0},
		{-1,  5, -1},
		{ 0, -1,  0}
	};
	public static final double[][] SHARPEN_KERNEL_3_HIGH = {
		{-1, -1, -1},
		{-1,  9, -1},
		{-1, -1, -1}
	};
}
