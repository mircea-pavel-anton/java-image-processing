package objects.singletons;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import objects.GenericJob;
import objects.filters.GenericFilter;
import objects.filters.binary.ANDFilter;
import objects.filters.binary.AbstractBinaryFilter;
import objects.filters.binary.ORFilter;
import objects.filters.binary.XORFilter;
import objects.filters.brightness.BrightnessFilter;
import objects.filters.color_depth_reduction.ColorDepthReductionFilter;
import objects.filters.contrast.ContrastFilter;
import objects.filters.convolution.ConvolutionFilter;
import objects.filters.convolution.masks.BlurMasks;
import objects.filters.convolution.masks.LaplacianMasks;
import objects.filters.convolution.masks.PrewittMasks;
import objects.filters.convolution.masks.SharpenMasks;
import objects.filters.convolution.masks.SobelMasks;
import objects.filters.gray_level.AbstractGrayLevelFilter;
import objects.filters.gray_level.LinearGrayLevelFilter;
import objects.filters.gray_level.LogarithmicGrayLevelFilter;
import objects.filters.gray_level.PowerLawGrayLevelFilter;
import objects.filters.grayscale.AbstractGrayscaleFilter;
import objects.filters.grayscale.AverageGrayscaleFilter;
import objects.filters.grayscale.WeightedGrayscaleFilter;
import objects.image.Image;

public class FilterBuilder extends GenericJob {
	// Singleton Instance
	private static FilterBuilder instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of ArgParser
	 */
	public static FilterBuilder getInstance() {
		if (instance == null) {
			instance = new FilterBuilder();
		}
		return instance;
	}

	// Constructor
	private FilterBuilder() {} // private constructor, meant to hide the default, public one

	// Fields
	private ArrayList<GenericFilter> filters = new ArrayList<GenericFilter>();

	/** Getter for the list of filters */
	public ArrayList<GenericFilter> getFilters() { return this.filters; }

	// Custom filter builders
	/** Interactive shell prompt to create a binary filter */
	private AbstractBinaryFilter createBinaryFilter() throws Exception {
		AbstractBinaryFilter filter = null;
		int selection = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("The available binary filters are:");
			System.out.println("1. AND filter");
			System.out.println("2. OR filter");
			System.out.println("3. XOR filter");
			selection = sc.nextInt();

			switch (selection) { 
				case 1: filter = new ANDFilter(); break;
				case 2: filter = new ORFilter(); break;
				case 3: filter = new XORFilter(); break;
				default: System.out.println("Invalid selection"); break;
			}
		} while (filter == null);

		File testFile;
		do {
			System.out.println("Binary filters require a second input image for the operation to take place.");
			System.out.println("Please enter the path to the second image: ");
			String path = sc.nextLine();
			testFile = new File(path);
		} while (testFile.exists() && testFile.isFile() && testFile.canRead());
		filter.loadImage(new Image(testFile.getAbsolutePath()));
		sc.close();
		return filter;
	}

	/** Interactive shell prompt to create a brightness filter */
	private BrightnessFilter createBrightnessFilter() {
		int brightness = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Please enter the brightness adjustment value (-255, 255):");
			brightness = sc.nextInt();
			
			if (brightness > 255 || brightness < -255) {
				System.out.println("Invalid value!");
			}
		} while (brightness > 255 || brightness < -255);
		sc.close();

		return new BrightnessFilter(brightness);
	}

	/** Interactive shell prompt to create a color depth reduction filter */
	private ColorDepthReductionFilter createColorDepthReductionFilter() {
		int bits = 0;
		int msb = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Please enter the number of bits you wish to keep: (0-7)");
			bits = sc.nextInt();
			
			if (bits > 7 || bits < 0) {
				System.out.println("Invalid value!");
			}
		} while (bits > 7 || bits < 0);
		
		do {
			System.out.println("Please choose the type of truncation: ");
			System.out.println("1. Truncate starting from MSB");
			System.out.println("2. Truncate starting from LSB");
			msb = sc.nextInt();
			
			if (msb != 1 && msb != 2) {
				System.out.println("Invalid value!");
			}
		} while (msb != 1 && msb != 2);
		sc.close();

		return new ColorDepthReductionFilter(bits, msb == 1);
	}

	/** Interactive shell prompt to create a contrast filter */
	private ContrastFilter createContrastFilter() {
		int contrast = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Please enter the desired contrast level: (0, 255):");
			contrast = sc.nextInt();
			
			if (contrast > 255 || contrast < 0) {
				System.out.println("Invalid value!");
			}
		} while (contrast > 255 || contrast < 0);
		sc.close();

		return new ContrastFilter(contrast);
	}

	/** Interactive shell prompt to create a convolution filter */
	private ConvolutionFilter createConvolutionFilter() {
		ConvolutionFilter filter = null;
		int selection = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("The available convolution masks are:");
			System.out.println(" 1. 3x3 Box Blur Mask");
			System.out.println(" 2. 5x5 Box Blur Mask");
			System.out.println(" 3. 3x3 Gaussian Blur Mask");
			System.out.println(" 4. 5x5 Gaussian Blur Mask");
			System.out.println(" 5. 3x3 Sharpening Mask (low intensity)");
			System.out.println(" 6. 3x3 Sharpening Mask (high intensity)");
			System.out.println(" 7. 3x3 Discrete Approx. of Laplacian filter 1");
			System.out.println(" 8. 3x3 Discrete Approx. of Laplacian filter 2");
			System.out.println(" 9. 3x3 Vertical Prewitt Mask");
			System.out.println("10. 3x3 Horizontal Prewitt Mask");
			System.out.println("11. 3x3 Vertical Sobel Mask");
			System.out.println("12. 3x3 Horizontal Soble Mask");
			selection = sc.nextInt();

			switch (selection) { 
				case  1: filter = new ConvolutionFilter(BlurMasks.BOX_BLUR_3); break;
				case  2: filter = new ConvolutionFilter(BlurMasks.BOX_BLUR_5); break;
				case  3: filter = new ConvolutionFilter(BlurMasks.GAUSSIAN_BLUR_3); break;
				case  4: filter = new ConvolutionFilter(BlurMasks.GAUSSIAN_BLUR_5); break;
				case  5: filter = new ConvolutionFilter(SharpenMasks.SHARPEN_KERNEL_3_LOW); break;
				case  6: filter = new ConvolutionFilter(SharpenMasks.SHARPEN_KERNEL_3_HIGH); break;
				case  7: filter = new ConvolutionFilter(LaplacianMasks.LAPLACIAN_KERNEL1); break;
				case  8: filter = new ConvolutionFilter(LaplacianMasks.LAPLACIAN_KERNEL2); break;
				case  9: filter = new ConvolutionFilter(PrewittMasks.VERTICAL_PREWITT_KERNEL); break;
				case 10: filter = new ConvolutionFilter(PrewittMasks.HORIZONTAL_PREWITT_KERNEL); break;
				case 11: filter = new ConvolutionFilter(SobelMasks.VERTICAL_SOBEL_KERNEL); break;
				case 12: filter = new ConvolutionFilter(SobelMasks.HORIZONTAL_SOBEL_KERNEL); break;
				default: System.out.println("Invalid selection"); break;
			}
		} while (filter == null);
		sc.close();

		return filter;
	}

	/** Interactive shell prompt to create a gray level filter */
	private AbstractGrayLevelFilter createGrayLevelFilter() {
		AbstractGrayLevelFilter filter = null;
		int selection = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("The available gray level transforms are:");
			System.out.println(" 1. Linear (negative, not identity)");
			System.out.println(" 2. Logarithmic");
			System.out.println(" 3. Power Law");
			selection = sc.nextInt();

			switch (selection) { 
				case  1: filter = new LinearGrayLevelFilter(); break;
				case  2:
					System.out.println("The logarithmic filter requires you to input the value for 'c' to use in the formula:");
					System.out.println("s = c * log(r+1), where:");
					System.out.println("s = the new color");
					System.out.println("r = the old color");
					int c = sc.nextInt();
					filter = new LogarithmicGrayLevelFilter(c);
					break;
				case  3:
					System.out.println("The power law filter requires you to input the values for 'y' and 'c' to use in the formula:");
					System.out.println("s = c * r^y, where:");
					System.out.println("s = the new color");
					System.out.println("r = the old color");
					System.out.println("y = "); int y = sc.nextInt();
					System.out.println("c = "); int c = sc.nextInt();

					filter = new PowerLawGrayLevelFilter(y, c);
					break;
				default: System.out.println("Invalid selection"); break;
			}
		} while (filter == null);
		
		sc.close();
		return filter;
	}

	/** Interactive shell prompt to create a grayscale filter */
	private AbstractGrayscaleFilter createGrayscaleFilter() {
		AbstractGrayscaleFilter filter = null;
		int selection = 0;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("The available grayscale methods are:");
			System.out.println(" 1. Average method");
			System.out.println(" 2. Weighted (Luminosity method)");
			selection = sc.nextInt();

			switch (selection) { 
				case  1: filter = new AverageGrayscaleFilter(); break;
				case  2: filter = new WeightedGrayscaleFilter(); break;
				default: System.out.println("Invalid selection"); break;
			}
		} while (filter == null);
		
		sc.close();
		return filter;
	}

	/** Interactive shell sequence that prompts the user to build up filters */
	private void build() {
		int selection = 0;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Please select the operation you wish to apply: ");
			System.out.println("1.  Binary operation between two images");
			System.out.println("2.  Brightness adjustment");
			System.out.println("3.  Color Depth Reduction");
			System.out.println("4.  Contrast Adjustment");
			System.out.println("5.  Convolution Masks");
			System.out.println("6.  Gray Level Adjustment");
			System.out.println("7.  Color to Grayscale conversion");
			System.out.println("8.  Grayscale to binary image conversion");
			System.out.println("9.  Histogram generation");
			System.out.println("10. Color inversion");
			System.out.println("11. Image mirroring");
			System.out.println("12. Color normalization");
			System.out.println("13. Image rotation");
			System.out.println("14. Image Translation");
			System.out.println("15. Image Zooming");
			System.out.println("16. Exit.");
			selection = sc.nextInt();

			switch (selection) {
				case  1: filters.add( createBinaryFilter() ); break;
				case  2: filters.add( createBrightnessFilter() ); break;
				case  3: filters.add( createColorDepthReductionFilter() ); break;
				case  4: filters.add( createContrastFilter() ); break;
				case  5: filters.add( createConvolutionFilter() ); break;
				case  6: filters.add( createGrayLevelFilter() ); break;
				case  7: filters.add( createGrayscaleFilter() ); break;
				case  8: filters.add( createeGrayscaletoBinaryFilter() ); break;
				case  9: filters.add( createHistogramFilter() ); break;
				case 10: filters.add( createNegativeFilter() ); break;
				case 11: filters.add( createMirrorFilter() ); break;
				case 12: filters.add( createNormalizationFilter() ); break;
				case 13: filters.add( createRotationFilter() ); break;
				case 14: filters.add( createTranslationFilter() ); break;
				case 15: filters.add( createZoomFilter() ); break;
				case 16: break;
				default: 
					System.out.println("Invalid selection.");
					break;
			}

		} while (selection != 16);
		sc.close();
	}

	/** A wrapper for the @build function that implements the Timer singleton to track execution
	 * duration
	 * 
	 * @throws Exception -> rethrows all exceptions
	 */
	public void run() throws Exception {
		Timer timer = Timer.getInstance();
		timer.startJob(String.valueOf(uID));
		build();
		duration = timer.stopJob(String.valueOf(uID));
	}
}
