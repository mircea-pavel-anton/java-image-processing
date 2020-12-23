package objects.image;

public class BitmapImage extends Image{
	private class BmpHeader {
		private String fileType;
		private int fileSize;
		private String reserved01;
		private String reserved02;
		private int pixelDataOffset;
	}
	private class DibHeader {
		private int headerSize;
		private int imageWidth;
		private int imageHeight;
		private int planes;
		private int bitsPerPixel;
		private int compression;
		private int imageSize;
		private int XpixelsPerMeter;
		private int YpixelsPerMeter;
		private int totalColors;
		protected int importantColors;
	}
	
	private BmpHeader bmpHeader;
	private DibHeader dibHeader;

	/** Constructor */
	public BitmapImage() {
		bmpHeader = new BmpHeader();
		dibHeader = new DibHeader();
		pixels = null;
	}

	// BMP Header Setters
	public void setFileType(String value) { bmpHeader.fileType = value; }
	public void setFileSize(int value) { bmpHeader.fileSize = value; }
	public void setReserved1(String value) { bmpHeader.reserved01 = value; }
	public void setReserved2(String value) { bmpHeader.reserved02 = value; }
	public void setPixelDataOffset(int value) { bmpHeader.pixelDataOffset = value; }

	// DIB Header Setters
	public void setHeaderSize(int value) { dibHeader.headerSize = value; }
	public void setImageWidth(int value) { 
		dibHeader.imageWidth = value;
		this.width = value;
	}
	public void setImageHeight(int value) {
		dibHeader.imageHeight = value;
		this.height = value;
	}
	public void setPlanes(int value) { dibHeader.planes = value; }
	public void setBitsPerPixel(int value) { dibHeader.bitsPerPixel = value; }
	public void setCompression(int value) { dibHeader.compression = value; }
	public void setImageSize(int value) { dibHeader.imageSize = value; }
	public void setXpixelsPerMeter(int value) { dibHeader.XpixelsPerMeter = value; }
	public void setYpixelsPerMeter(int value) { dibHeader.YpixelsPerMeter = value; }
	public void setTotalColors(int value) { dibHeader.totalColors = value; }
	public void setImportantColors(int value) { dibHeader.importantColors = value; }


	// BMP Header Getters
	public String getFileType() { return bmpHeader.fileType; }
	public int getFileSize() { return bmpHeader.fileSize; }
	public String getReserved1() { return bmpHeader.reserved01; }
	public String getReserved2() { return bmpHeader.reserved02; }
	public int getPixelDataOffset() { return bmpHeader.pixelDataOffset; }
	
	// DIB Header Getters
	public int getHeaderSize() { return dibHeader.headerSize; }
	public int getImageWidth() { return dibHeader.imageWidth; }
	public int getImageHeight() { return dibHeader.imageHeight; }
	public int getPlanes() { return dibHeader.planes; }
	public int getBitsPerPixel() { return dibHeader.bitsPerPixel; }
	public int getBytesPerPixel() { return dibHeader.bitsPerPixel / 8; }
	public int getCompression() { return dibHeader.compression; }
	public int getImageSize() { return dibHeader.imageSize; }
	public int getXpixelsPerMeter() { return dibHeader.XpixelsPerMeter; }
	public int getYpixelsPerMeter() { return dibHeader.YpixelsPerMeter; }
	public int getTotalColors() { return dibHeader.totalColors; }
	public int getImportantColors() { return dibHeader.importantColors; }

	// "setter?"
	public void initPixels() {
		if (dibHeader.imageWidth > 0 && dibHeader.imageHeight > 0) {
			pixels = new Pixel[dibHeader.imageWidth][dibHeader.imageHeight];
		}
	}
}
