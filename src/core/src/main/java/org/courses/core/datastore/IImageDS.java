package org.courses.core.datastore;

import java.awt.Image;

public interface IImageDS
{
	void saveImage(Image img);
	byte[] loadImageData();
}
