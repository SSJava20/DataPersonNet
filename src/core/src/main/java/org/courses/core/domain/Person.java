package org.courses.core.domain;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
	public Person() {
	}

	public Person(String aFirstName, String aLastName, Date date, String phone,
			String email, String imgPath1, String filePath, String comment) {
		setFirstName(aFirstName);
		setLastName(aLastName);
		setEmail(email);
		setDate(date);
		setPhone(phone);
		setImgFilePath(filePath);
		setFilePath(filePath);
		setComment(comment);
	}

	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void loadFileData() {
		try {
			File file = new File(filePath);
			FileInputStream fileInputStream = new FileInputStream(file);

			fileData = new byte[(int) file.length()];
			fileInputStream.read(fileData);
		} catch (Exception e) {
			// System.err.println("Fail to load file data " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void loadImageData() {
		try {
			File file = new File(imgFilePath);
			FileInputStream fileInputStream = new FileInputStream(file);

			imageData = new byte[(int) file.length()];
			fileInputStream.read(imageData);
		} catch (Exception e) {
			// System.err.println("Fail to load image data " + e.getMessage());
			e.printStackTrace();
		}
	}

	public byte[] getImgData() {
		return imageData;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setImgData(byte[] imgArr) {
		imageData = imgArr;
	}

	private int id;

	@NotNull(message = "Name is required")
	@Size(min = 3, message = "Name size must be more than 3")
	private String firstName;

	@NotNull(message = "Last name is requires")
	@Size(min = 3, message = "Last name size must be more than 3")
	private String lastName;

	@NotNull(message = "Phone is required")
	@Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "phone is invalid")
	private String phone;

	@NotNull(message = "Email is required")
	@Pattern(regexp = "^([a-zA-Z0-9]+[\\w.]*[a-zA-Z0-9]+@[a-zA-Z0-9]+[\\w.]*[a-zA-Z0-9]+\u002E[a-zA-Z0-9]{1,4})$", message = "email is invalid")
	private String email;

	private String comment;
	private byte imageData[];
	private byte fileData[];
	private String imgFilePath;
	private String filePath;
	private Date date;

	public static Set<String> validate(Object object, Validator validator) {
		HashSet<String> errors = new HashSet<String>();
		Set<ConstraintViolation<Object>> constraintViolations = validator
				.validate(object);
		for (ConstraintViolation<Object> cv : constraintViolations) {
			errors.add(cv.getMessage());
		}
		return errors;
	}

	public int getId() {
		return id;
	}

	public void setId(int iD) {
		id = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImgFilePath() {
		imageData = getImgData();
		return imgFilePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
