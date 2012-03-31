package org.courses.desktopclient.view;

import java.util.ArrayList;

import org.courses.core.domain.Person;
import org.courses.desktopclient.model.Model;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;



public class MainWindow
{
	public Shell shell;
	private Table table;
	private TableEditor editor;
	private Button btnLoad;
	private Button btnEdit;
	public Display display;
	private Button btnDelRow;
	private Button btnAddRow;
	private Canvas cvsAvatar1;
	private Model model;
	private Composite composite_3;
	private Canvas cvsAvatar2;

	public MainWindow(Model model)
	{
		this.model = model;
	}

	/**
	 * Open the window.
	 */
	public void open()
	{
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents()
	{
		shell = new Shell();
		shell.setSize(1043, 539);
		shell.setText("SWT Application");
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.VERTICAL));
		composite.setLayoutData(new RowData(195, 346));

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(new RowData(191, 60));
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));

		btnLoad = new Button(composite_2, SWT.NONE);
		btnLoad.setText("Load");

		btnEdit = new Button(composite_2, SWT.NONE);
		btnEdit.setText("Edit");

		cvsAvatar1 = new Canvas(composite, SWT.NONE);
		cvsAvatar1.setLayoutData(new RowData(190, 276));

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new RowData(588, 377));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn IDColumn = new TableColumn(table, SWT.NONE);
		IDColumn.setWidth(100);
		IDColumn.setText("ID");
		TableColumn NameColumn = new TableColumn(table, SWT.NONE);
		NameColumn.setWidth(100);
		NameColumn.setText("First Name");
		TableColumn SNamelumn = new TableColumn(table, SWT.NONE);
		SNamelumn.setWidth(100);
		SNamelumn.setText("Last Name");
		TableColumn phoneColumn = new TableColumn(table, SWT.NONE);
		phoneColumn.setWidth(100);
		phoneColumn.setText("Phone");
		TableColumn emailColumn = new TableColumn(table, SWT.NONE);
		emailColumn.setWidth(100);
		emailColumn.setText("e-mail");

		editor = new TableEditor(table);

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(new RowLayout(SWT.VERTICAL));
		composite_1.setLayoutData(new RowData(211, 342));

		composite_3 = new Composite(composite_1, SWT.NONE);
		composite_3.setLayoutData(new RowData(203, 64));
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));

		btnAddRow = new Button(composite_3, SWT.NONE);
		btnAddRow.setText("Add row");

		btnDelRow = new Button(composite_3, SWT.NONE);
		btnDelRow.setText("Delete row");

		cvsAvatar2 = new Canvas(composite_1, SWT.NONE);
		cvsAvatar2.setLayoutData(new RowData(205, 270));

		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;

		table.addMouseListener(new TableMouseListener());
	}

	/**
	 * Show AddPersonDialog
	 * 
	 * @return created person or null
	 */
	public Person showAddPersonDialog()
	{
		AddPersonDialog dialog = new AddPersonDialog(shell, SWT.TITLE | SWT.MENU);
		return dialog.open();
	}

	public Person showEditPersonDialog()
	{
		int id = getSelectedPersonId();
		if (id == -1)
			return null;
		Person p = model.getPerson(id);
		AddPersonDialog dialog = new AddPersonDialog(shell, SWT.TITLE | SWT.MENU, p);
		return dialog.open();
	}

	/**
	 * Add new item to table from person
	 * 
	 * @param p
	 *            - person
	 */
	public void addPersonInTable(Person p)
	{
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "" + p.getId(), p.getFirstName(), p.getLastName(), 	p.getPhone(), p.getEmail() });
	}

	public void editPersonInTable(Person p)
	{

		TableItem item = table.getItem(table.getSelectionIndex());
		item.setText(new String[] { "" + p.getId(), p.getFirstName(), p.getLastName(),p.getPhone(), p.getEmail() });
	}

	/**
	 * Delete selected item from table
	 * 
	 * @return id of selected person in table
	 */
	public int delSelectedPerson()
	{
		int id = getSelectedPersonId();
		if (id == -1)
			return id;
		table.remove(id);
		return id;
	}
	public int getSelectedPersonId()
	{
		if (table.getItemCount() != 0 && table.getSelectionIndex() != -1)
		{
			int i = table.getSelectionIndex();
			return Integer.parseInt(table.getItem(i).getText(0));
		}
		return -1;
	}

	/**
	 * Fill table with persons
	 * 
	 * @param persons
	 *            - ArrayList of persons
	 */
	public void setPersons(ArrayList<Person> persons)
	{
		table.removeAll();
		for (Person p : persons)
		{
			addPersonInTable(p);
		}
	}

	class TableMouseListener extends MouseAdapter
	{
		@Override
		public void mouseDown(MouseEvent e)
		{
			int selectedIndex = table.getSelectionIndex();
			if (selectedIndex != -1)
				changeImages(table.getItem(selectedIndex).getText(0));
		}
	}

	/**
	 * Display current person image
	 * 
	 * @param personID
	 */
	private void changeImages(String personID)
	{
		// get first image
		Image img = null;
		ImageData data = model.getImageData(Integer.parseInt(personID));
		if (data != null)
		{
			img = new Image(display, data);
		}
		// get second image
		changeImage(cvsAvatar1, img);
	}
	private void changeImage(Canvas c, Image img)
	{
		c.setBackgroundImage(null);
		if (img != null)
		{
			img = new Image(display, img.getImageData().scaledTo(c.getSize().x, c.getSize().y));
			c.setBackgroundImage(img);
		}

	}
	// add listeners methods for controller
	public void addBtnEditListener(SelectionListener listener)
	{
		btnEdit.addSelectionListener(listener);
	}

	public void addBtnLoadListener(SelectionListener listener)
	{
		btnLoad.addSelectionListener(listener);
	}

	public void addBtnAddRowListener(SelectionListener listener)
	{
		btnAddRow.addSelectionListener(listener);
	}

	public void addBtnDelRowListener(SelectionListener listener)
	{
		btnDelRow.addSelectionListener(listener);
	}
}
