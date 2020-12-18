package objects.filters;

import objects.image.Image;

public class TranslateFilter extends GenericFilter {
	private int xDelta = 0; // number of pixels to translate horizontally
	private int yDelta = 0; // number of pixels to translate vertically


	@Override
	public Image filter(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return TRANSLATE_FILTER; }
}
