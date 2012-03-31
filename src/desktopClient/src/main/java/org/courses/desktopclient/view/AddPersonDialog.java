package org.courses.desktopclient.view;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;

import org.courses.core.domain.Person;
import org.courses.core.util.DateConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class AddPersonDialog extends Dialog
{
	protected Person result = null;
	protected Person person;
	protected Shell shell;
	private Text textFName;
	private Text textLName;
	private Label lbl1ImagePath;
	private Label lbl2ImagePath;
	private Text textPhone;
	private Text textComment;
	private Text textEmail;
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AddPersonDialog(Shell parent, int style)
	{
		super(parent, style);
		setText("Add person");
	}

	public AddPersonDialog(Shell parent, int style, Person p)
	{
		super(parent, style);
		person = p;
		setText("Add person");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Person open()
	{
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		return result;
	}
	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell(getParent(), getStyle());
		shell.setSize(719, 445);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);

		textFName = new Text(composite, SWT.BORDER);
		textFName.setText("name");
		textFName.setBounds(108, 13, 160, 19);

		textLName = new Text(composite, SWT.BORDER);
		textLName.setText("lastname");
		textLName.setBounds(108, 51, 160, 19);

		lbl1ImagePath = new Label(composite, SWT.NONE);
		lbl1ImagePath.setBounds(108, 129, 221, 19);

		lbl2ImagePath = new Label(composite, SWT.NONE);
		lbl2ImagePath.setBounds(108, 165, 221, 19);

		Button btnOk = new Button(composite, SWT.NONE);

		btnOk.setBounds(597, 384, 68, 23);
		btnOk.setText("Ok");

		Label lblFirstName = new Label(composite, SWT.NONE);
		lblFirstName.setBounds(10, 16, 68, 13);
		lblFirstName.setText("First name");

		Label lblLastName = new Label(composite, SWT.NONE);
		lblLastName.setBounds(10, 54, 61, 13);
		lblLastName.setText("Last name");

		Label lblBirthDay = new Label(composite, SWT.NONE);
		lblBirthDay.setBounds(10, 89, 49, 16);
		lblBirthDay.setText("Birthday");

		Label lblFirstImage = new Label(composite, SWT.NONE);
		lblFirstImage.setBounds(10, 130, 76, 18);
		lblFirstImage.setText("1image path");

		Label lblSecondImage = new Label(composite, SWT.NONE);
		lblSecondImage.setBounds(10, 165, 68, 16);
		lblSecondImage.setText("2image path");

		Button btnSecondImage = new Button(composite, SWT.NONE);
		btnSecondImage.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				String[] filterExt = { "*.jpg", "*.gif", "*.*" };
				fd.setFilterExtensions(filterExt);
				lbl2ImagePath.setText(fd.open());
			}
		});
		btnSecondImage.setBounds(108, 229, 76, 25);
		btnSecondImage.setText("Set 2 image");

		Button btnFirstImage = new Button(composite, SWT.NONE);
		btnFirstImage.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				String[] filterExt = { "*.jpg", "*.gif", "*.*" };
				fd.setFilterExtensions(filterExt);
				lbl1ImagePath.setText(fd.open());
			}
		});
		btnFirstImage.setBounds(10, 230, 76, 23);
		btnFirstImage.setText("Set 1 image");

		final org.eclipse.swt.widgets.List listError = new org.eclipse.swt.widgets.List(composite, SWT.BORDER);
		listError.setBounds(10, 260, 338, 147);
		listError.setVisible(false);

		Label lblPhone = new Label(composite, SWT.NONE);
		lblPhone.setBounds(332, 18, 41, 15);
		lblPhone.setText("Phone");

		textPhone = new Text(composite, SWT.BORDER);
		textPhone.setBounds(398, 13, 187, 19);
		textPhone.setText("89261234567");

		textComment = new Text(composite, SWT.BORDER | SWT.MULTI);
		textComment.setBounds(398, 91, 267, 264);

		Label lblComment = new Label(composite, SWT.NONE);
		lblComment.setText("Comment");
		lblComment.setBounds(328, 94, 64, 15);

		Label lblFile = new Label(composite, SWT.NONE);
		lblFile.setText("File path");
		lblFile.setBounds(10, 204, 61, 16);

		final Label lblFilepath = new Label(composite, SWT.NONE);
		lblFilepath.setBounds(108, 198, 221, 19);

		Button btnAddFile = new Button(composite, SWT.NONE);
		btnAddFile.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				String[] filterExt = { "*.*" };
				fd.setFilterExtensions(filterExt);
				lblFilepath.setText(fd.open());
			}
		});
		btnAddFile.setBounds(203, 229, 75, 25);
		btnAddFile.setText("Add file");

		final Label lblEmail = new Label(composite, SWT.NONE);
		lblEmail.setText("e-mail");
		lblEmail.setBounds(332, 53, 41, 15);

		textEmail = new Text(composite, SWT.BORDER);
		textEmail.setBounds(398, 53, 187, 19);
		textEmail.setText("qwe@qwe.ru");
		
		final DateTime dateTime = new DateTime(composite, SWT.BORDER);
		dateTime.setBounds(108, 89, 76, 34);

		if (person != null)
		{
			Calendar c = new GregorianCalendar();
			c.setTime(person.getDate());
			dateTime.setDay(c.get(Calendar.DAY_OF_MONTH));
			dateTime.setYear(c.get(Calendar.YEAR));
			dateTime.setMonth(c.get(Calendar.MONTH));
			textEmail.setText("" + person.getEmail());
			textFName.setText("" + person.getFirstName());
			textLName.setText("" + person.getLastName());
			textPhone.setText("" + person.getPhone());
			lblFilepath.setText("" + person.getFilePath());
			lbl1ImagePath.setText("" + person.getImgFilePath());
			textComment.setText("" + person.getComment());
		}

		btnOk.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				try
				{
					result = new Person();
					result.setDate(DateConverter.swtDateTimeToDate(dateTime));
					result.setComment(textComment.getText());
					result.setEmail(textEmail.getText());
					result.setFilePath(lblFilepath.getText());
					result.setFirstName(textFName.getText());
					result.setLastName(textLName.getText());
					result.setPhone(textPhone.getText());
					if (person != null)
					{
						result.setId(person.getId());
						result.setImgFilePath(lbl1ImagePath.getText());
					}
					else
					{
						result.setImgFilePath(lbl1ImagePath.getText());
					}
					Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
					Set<String> errors = Person.validate(result, validator);

					if (errors.size() == 0)
					{
						shell.dispose();
					}
					else
					{
						result = null;
						listError.removeAll();
						listError.setVisible(true);
						for (String error : errors)
						{
							listError.add(error);
						}
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					result = null;
				}
			}
		});

	}
	public Person getResult()
	{
		return result;
	}
}
