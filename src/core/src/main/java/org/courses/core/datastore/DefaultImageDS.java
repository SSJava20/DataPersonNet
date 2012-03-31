package org.courses.core.datastore;

import java.io.ByteArrayOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class DefaultImageDS implements IImageDS {

	public DefaultImageDS(String path, int type) {
		filePath = path;
		fileType = type;
	}

	public byte[] loadImageData() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ImageLoader il = new ImageLoader();
		il.data = new ImageData[] { new ImageData(filePath) };
		il.save(stream, SWT.IMAGE_JPEG);
		return stream.toByteArray();
	}

	public void saveImage(Image img) {
		ImageLoader il = new ImageLoader();
		il.data = new ImageData[] { img.getImageData() };

		il.save(filePath, fileType);
	}

	private String filePath;
	private int fileType;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.courses.core.datastore.IImageDS#saveImage(java.awt.Image)
	 */
	public void saveImage(java.awt.Image img) {
		// TODO Auto-generated method stub

	}
}
