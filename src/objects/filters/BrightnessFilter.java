package objects.filters;

import objects.image.Image;

public class BrightnessFilter extends GenericFilter {
	private int brightness_delta = 0;

	public BrightnessFilter(int brightness_delta) {
		if (brightness_delta < 256 && brightness_delta > -256) {
			this.brightness_delta = brightness_delta;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Image filter(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return BRIGHTNESS_FILTER; }
}
