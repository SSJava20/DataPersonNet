package org.courses.core;

import java.io.FileOutputStream;
import java.io.IOException;

import org.courses.core.domain.Person;

public class FileCopier {
	private static String SEPARATOR = "/";
	private static String RESOURCE_DIR = "." + SEPARATOR + "resources"
			+ SEPARATOR;

	public static boolean copyFilesToResources(Person person) {
		try {
			String imagePath = person.getImgFilePath();
			String filePath = person.getFilePath();
			if (imagePath != null && !imagePath.equals("")) {
				person.setImgFilePath(copy(person, imagePath));
			}
			if (filePath != null && !filePath.equals("")) {
				person.setFilePath(copy(person, filePath));
			}
			return true;
		}

		catch (Exception e) {
			System.err.println("Fail to copy data " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private static String copy(Person p, String initPath) throws IOException {
		initPath = initPath.substring(initPath.lastIndexOf("\\") + 1);

		String newPath = RESOURCE_DIR + p.getId() + initPath;

		FileOutputStream out = new FileOutputStream(newPath);
		out.write(p.getImgData());
		out.close();
		return newPath;

	}
}
