package objects.singletons;

import java.io.File;
import java.util.ArrayList;

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
import objects.filters.grayscale.GrayscaleToBinaryFilter;
import objects.filters.grayscale.WeightedGrayscaleFilter;
import objects.filters.histogram.AbstractHistogramFilter;
import objects.filters.histogram.BlueLevelHistogram;
import objects.filters.histogram.GrayLevelHistogram;
import objects.filters.histogram.GreenLevelHistogram;
import objects.filters.histogram.RedLevelHistogram;
import objects.filters.invert.NegativeFilter;
import objects.filters.mirror.MirrorFilter;
import objects.filters.normalize.NormalizationFilter;
import objects.filters.rotate.RotateFilter;
import objects.filters.translate.TranslateFilter;
import objects.filters.zoom.AbstractZoomFilter;
import objects.filters.zoom.KTimesZoomFilter;
import objects.filters.zoom.PixelReplicationZoomFilter;
import objects.filters.zoom.ZeroOrderHoldZoomFilter;
import objects.image.Image;
import objects.image.Pixel;

public class FilterBuilder extends GenericJob {
	// Singleton Instance
	private static FilterBuilder instance = null;

	// An instance of the Prompter singleton
	private Prompter prompter;

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
	private FilterBuilder() {
		prompter = Prompter.getInstance();
	} // private constructor, meant to hide the default, public one

	// Fields
	private ArrayList<GenericFilter> filters = new ArrayList<GenericFilter>();

	/** Getter for the list of filters */
	public ArrayList<GenericFilter> getFilters() { return this.filters; }

	// Custom filter builders
	/** Interactive shell prompt to create a binary filter */
	private AbstractBinaryFilter createBinaryFilter() throws Exception {
		AbstractBinaryFilter filter = null;
		System.out.println("The available binary filters are:");
		System.out.println("1. AND filter");
		System.out.println("2. OR filter");
		System.out.println("3. XOR filter");
		int selection = prompter.getBoundedInt("selection: ", 1, 3);

		switch (selection) { 
			case 1: filter = new ANDFilter(); break;
			case 2: filter = new ORFilter(); break;
			case 3: filter = new XORFilter(); break;
		}

		File testFile;
		do {
			System.out.println("Binary filters require a second input image for the operation to take place.");
			System.out.println("Please enter the path to the second image: ");
			String path = prompter.getString("path: ");
			testFile = new File(path);
		} while (!testFile.exists() || testFile.isDirectory() || !testFile.canRead());

		filter.loadImage(new Image(testFile.getAbsolutePath()));
		return filter;
	}

	/** Interactive shell prompt to create a brightness filter */
	private BrightnessFilter createBrightnessFilter() {
		System.out.println("Please enter the brightness adjustment value (-255, 255):");
		int brightness = prompter.getBoundedInt("brightness: ", -255, 255);
		return new BrightnessFilter(brightness);
	}

	/** Interactive shell prompt to create a color depth reduction filter */
	private ColorDepthReductionFilter createColorDepthReductionFilter() {
		System.out.println("Please enter the number of bits you wish to keep: (0-7)");
		int bits = prompter.getBoundedInt("bits: ", 0, 7);
		
		System.out.println("Please choose the type of truncation: ");
		System.out.println("1. Truncate starting from MSB");
		System.out.println("2. Truncate starting from LSB");
		int msb = prompter.getBoundedInt("selection: ", 1, 2);

		return new ColorDepthReductionFilter(bits, msb == 1);
	}

	/** Interactive shell prompt to create a contrast filter */
	private ContrastFilter createContrastFilter() {
		System.out.println("Please enter the desired contrast level: (0, 255):");
		int contrast = prompter.getBoundedInt("contrast level: ", 0, 255);
		return new ContrastFilter(contrast);
	}

	/** Interactive shell prompt to create a convolution filter */
	private ConvolutionFilter createConvolutionFilter() {
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
		int selection = prompter.getBoundedInt("selection: ", 1, 12);
		
		ConvolutionFilter filter = null;
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

		return filter;
	}

	/** Interactive shell prompt to create a gray level filter */
	private AbstractGrayLevelFilter createGrayLevelFilter() {
		System.out.println("The available gray level transforms are:");
		System.out.println(" 1. Linear (negative, not identity)");
		System.out.println(" 2. Logarithmic");
		System.out.println(" 3. Power Law");
		int selection = prompter.getBoundedInt("selection: ", 1, 3);
		
		AbstractGrayLevelFilter filter = null;
		switch (selection) { 
			case  1: filter = new LinearGrayLevelFilter(); break;
			case  2:
				System.out.println("The logarithmic filter requires you to input the value for 'c' to use in the formula: (1,255)");
				System.out.println("s = c * log(r+1), where:");
				System.out.println("s = the new color");
				System.out.println("r = the old color");
				double c = prompter.getDouble("c: ");
				filter = new LogarithmicGrayLevelFilter(c);
				break;
			case  3:
				System.out.println("The power law filter requires you to input the values for 'y' and 'c' to use in the formula:");
				System.out.println("s = c * r^y, where:");
				System.out.println("s = the new color");
				System.out.println("r = the old color");
				double y = prompter.getDouble("y: ");
				double k = prompter.getDouble("c: ");
				filter = new PowerLawGrayLevelFilter(y, k);
				break;
			default: System.out.println("Invalid selection"); break;
		}

		return filter;
	}

	/** Interactive shell prompt to create a grayscale filter */
	private AbstractGrayscaleFilter createGrayscaleFilter() {
		System.out.println("The available grayscale methods are:");
		System.out.println(" 1. Average method");
		System.out.println(" 2. Weighted (Luminosity method)");
		int selection = prompter.getBoundedInt("selection: ", 1, 2);
		
		AbstractGrayscaleFilter filter = null;
		switch (selection) { 
			case  1: filter = new AverageGrayscaleFilter(); break;
			case  2: filter = new WeightedGrayscaleFilter(); break;
			default: System.out.println("Invalid selection"); break;
		}
		
		return filter;
	}

	/** Interactive shell prompt to create a grayscale-to-binary filter */
	private GrayscaleToBinaryFilter createGrayscaleToBinaryFilter() {
		System.out.println("Please enter the threshold: (0, 255):");
		int threshold = prompter.getBoundedInt("threshold: ", 0, 255);

		System.out.println("Is the image grayscaled already? [Y=1/N=0]");
		int answer = prompter.getBoundedInt("answer: ", 0, 1);
		if (answer == 0) filters.add( new AverageGrayscaleFilter() );

		return new GrayscaleToBinaryFilter(threshold);
	}

	/** Interactive shell prompt to create a histogram filter */
	private AbstractHistogramFilter createHistogramFilter() {
		System.out.println("The available hsitograms are:");
		System.out.println(" 1. Red level histogram");
		System.out.println(" 2. Green level histogram");
		System.out.println(" 3. Blue level histogram");
		System.out.println(" 4. Gray Level Histogram");
		int selection = prompter.getBoundedInt("selection: ", 1, 4);
		
		System.out.println("Please enter the number of samples: (1, 255):");
		int samples = prompter.getBoundedInt("samples: ", 1, 255);

		System.out.println("Please enter the output image width in pixels");
		int width = prompter.getBoundedInt("width: ", 1, 10000);
		
		System.out.println("Please enter the output image height in pixels");
		int height = prompter.getBoundedInt("height: ", 1, 10000);

		AbstractHistogramFilter filter = null;
		switch (selection) { 
			case  1: filter = new RedLevelHistogram(width, height, samples); break;
			case  2: filter = new GreenLevelHistogram(width, height, samples); break;
			case  3: filter = new BlueLevelHistogram(width, height, samples); break;
			case  4: 
				System.out.println("Is the input image already grayscaled? [Y=1/N=0]");
				boolean isGrayscale = prompter.getBoundedInt("is grayscale: ", 0, 1) == 1;
				if (!isGrayscale) // make image grayscale if it is not already
					filters.add(new AverageGrayscaleFilter());
				filter = new GrayLevelHistogram(width, height, samples);
				break;
		}

		return filter;

	}

	/** "Interactive" (lol) shell prompt to create a negative filter */
	private NegativeFilter createNegativeFilter() { return new NegativeFilter(); }

	/** Interactive shell prompt to create a mirror filter */
	private MirrorFilter createMirrorFilter() {
		System.out.println("The available mirror axis are:");
		System.out.println(" 1. Ox");
		System.out.println(" 2. Oy");
		System.out.println(" 3. Both");
		int selection = prompter.getBoundedInt("selection: ", 1, 3);
		return new MirrorFilter(selection);
	}

	/** Interactive shell prompt to create a rotate filter */
	private RotateFilter createRotationFilter() {
		System.out.println("How many degrees do you want to rotate?:");
		System.out.println(" 1. -270");
		System.out.println(" 2. -180");
		System.out.println(" 3. -90");
		System.out.println(" 4.  90");
		System.out.println(" 5.  180");
		System.out.println(" 6.  270");
		int selection = prompter.getBoundedInt("selection: ", 1, 6);

		RotateFilter filter = null;
		switch (selection) {
			case 1: filter = new RotateFilter(3, false); break;
			case 2: filter = new RotateFilter(2, false); break;
			case 3: filter = new RotateFilter(1, false); break;
			case 4: filter = new RotateFilter(1, true); break;
			case 5: filter = new RotateFilter(2, true); break;
			case 6: filter = new RotateFilter(3, true); break;
		}

		return filter;
	}

	/** "Interactive" (lol) shell prompt to create a normalization filter */
	private NormalizationFilter createNormalizationFilter() { return new NormalizationFilter(); }

	/** Interactive shell prompt to create a translation filter */
	private TranslateFilter createTranslationFilter() {
		System.out.println("How many pixel do you want to translate to the right? (0-inf)");
		int x = prompter.getBoundedInt("delta x: ", 0, 10000);

		System.out.println("How many pixel do you want to translate down? (0-inf)");
		int y = prompter.getBoundedInt("delta x: ", 0, 10000);

		System.out.println("Do you want a custom fill color for the blank pixels? (default is BLACK) [Y=1/N=0]");
		Pixel pixel = null;
		boolean answer = prompter.getBoundedInt("create custom color?: ", 0, 1) == 1;

		if (answer) {
			System.out.println("Enter the value for red intensity: (0-255)");
			int red = prompter.getBoundedInt("red: ", 0, 255);
			System.out.println("Enter the value for green intensity: (0-255)");
			int green = prompter.getBoundedInt("green: ", 0, 255);
			System.out.println("Enter the value for blue intensity: (0-255)");
			int blue = prompter.getBoundedInt("blue: ", 0, 255);
			pixel = new Pixel(red, green, blue).clip();
		} else pixel = new Pixel(0);

		return new TranslateFilter(x, y, pixel);
	}

	/** Interactive shell prompt to create a zoom filter */
	private AbstractZoomFilter createZoomFilter() {
		System.out.println("The available zooming algorithms are: ");
		System.out.println("1. Pixel replication");
		System.out.println("2. Zero order Hold (zooms to 2x size)");
		System.out.println("3. K-Times Zooming");
		int selection = prompter.getBoundedInt("selection: ", 1, 3);
		
		int zoomLevel = 0;
		if (selection == 1 || selection == 3) {
			System.out.println("How many times do you wish to enlarge the image? (2-inf)");
			zoomLevel = prompter.getBoundedInt("zoom level: ", 2, 1000);
		}
		
		AbstractZoomFilter filter = null;
		switch (selection) {
			case 1: filter = new PixelReplicationZoomFilter(zoomLevel); break;
			case 2: filter = new ZeroOrderHoldZoomFilter(); break;
			case 3: filter = new KTimesZoomFilter(zoomLevel); break;
		}

		return filter;
	}

	/** Interactive shell sequence that prompts the user to build up filters */
	private void build() throws Exception {
		int selection = 0;
		do {
			System.out.println("Current filter stack: ");
			for (int i = 0; i < filters.size(); i++) {
				System.out.println("  - " + filters.get(i).getType());
			}

			System.out.println("Please select the operation you wish to add to the stack: ");
			System.out.println(" 1. Binary operation between two images");
			System.out.println(" 2. Brightness adjustment");
			System.out.println(" 3. Color Depth Reduction");
			System.out.println(" 4. Contrast Adjustment");
			System.out.println(" 5. Convolution Masks");
			System.out.println(" 6. Gray Level Adjustment");
			System.out.println(" 7. Color to Grayscale conversion");
			System.out.println(" 8. Grayscale to binary image conversion");
			System.out.println(" 9. Histogram generation");
			System.out.println("10. Color inversion");
			System.out.println("11. Image mirroring");
			System.out.println("12. Color normalization");
			System.out.println("13. Image rotation");
			System.out.println("14. Image Translation");
			System.out.println("15. Image Zooming");
			System.out.println("16. Exit.");
			selection = prompter.getBoundedInt("operation: ", 1, 16);

			switch (selection) {
				case  1: filters.add( createBinaryFilter() ); break;
				case  2: filters.add( createBrightnessFilter() ); break;
				case  3: filters.add( createColorDepthReductionFilter() ); break;
				case  4: filters.add( createContrastFilter() ); break;
				case  5: filters.add( createConvolutionFilter() ); break;
				case  6: filters.add( createGrayLevelFilter() ); break;
				case  7: filters.add( createGrayscaleFilter() ); break;
				case  8: filters.add( createGrayscaleToBinaryFilter() ); break;
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
