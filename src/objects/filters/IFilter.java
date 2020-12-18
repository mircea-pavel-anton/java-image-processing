package objects.filters;

import objects.image.Image;

public interface IFilter {
	public Image apply(Image image);
}
