package org.courses.core.datastore;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;

public class ImageDSFactory
{

	protected static Map<String, Integer> extension = initExtMap();

	public static IImageDS getInstance(String filePath)
	{
		if (filePath != null && !filePath.equals(""))
		{
			String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
			int fileType = extension.get(ext);
			if (fileType == SWT.IMAGE_JPEG) { return new DefaultImageDS(filePath, fileType); }
		}
		return null;
	}
	protected static Map<String, Integer> initExtMap()
	{
		Map<String, Integer> ext = new HashMap<String, Integer>();
		ext.put("jpg", SWT.IMAGE_JPEG);
		ext.put("png", SWT.IMAGE_PNG);
		ext.put("gif", SWT.IMAGE_GIF);
		ext.put("bmp", SWT.IMAGE_BMP);
		ext.put("tiff", SWT.IMAGE_TIFF);
		ext.put("psd", 10);

		return ext;
	}
}
