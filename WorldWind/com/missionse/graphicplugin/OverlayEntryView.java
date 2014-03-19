package com.missionse.graphicplugin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import java.util.Observable;

//Implements a filter to confirm operator entries are doubles.
//This is really just a double filter for now.  No range checks.
class LatLongFilter extends DocumentFilter
{
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
	{
		System.out.println("Check");
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString()))
		{
			super.insertString(fb, offset, string, attr);
		}
		else
		{
			// warn the user and don't allow the insert
		}
	}

	private boolean test(String text)
	{
		try
		{
			Double.parseDouble(text);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException
	{

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString()))
		{
			super.replace(fb, offset, length, text, attrs);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
	{
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (test(sb.toString()))
		{
			super.remove(fb, offset, length);
		}
	}
}

public class OverlayEntryView implements java.util.Observer
{
	private JFrame frame;
	private JTextField textLatitude;
	private JTextField textLongitude;
	private JTextField textRadius;
	// private JTextField textField;
	private JButton btnOK;

	public OverlayEntryView()
	{
		initialize();
		this.frame.setVisible(true);
	}

	private void initialize()
	{
		frame = new JFrame("Overlay Entry");
		frame.setBounds(100, 100, 674, 613);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnOK = new JButton("Create");
		btnOK.setBounds(91, 510, 118, 25);
		frame.getContentPane().add(btnOK);

		// JButton btnNewButton_2 = new JButton("Apply");
		// btnNewButton_2.addActionListener(new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// }
		// });
		// btnNewButton_2.setBounds(263, 510, 118, 25);
		// frame.getContentPane().add(btnNewButton_2);

		// JButton btnNewButton_3 = new JButton("Cancel");
		// btnNewButton_3.addActionListener(new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// }
		// });
		// btnNewButton_3.setBounds(424, 510, 118, 25);
		// frame.getContentPane().add(btnNewButton_3);

		textLatitude = new JTextField();
		PlainDocument doc = (PlainDocument) textLatitude.getDocument();
		doc.setDocumentFilter(new LatLongFilter());
		textLatitude.setBounds(293, 170, 114, 19);
		frame.getContentPane().add(textLatitude);
		textLatitude.setColumns(10);

		textLongitude = new JTextField();
		PlainDocument doc2 = (PlainDocument) textLatitude.getDocument();
		doc2.setDocumentFilter(new LatLongFilter());
		textLongitude.setBounds(293, 235, 114, 19);
		frame.getContentPane().add(textLongitude);
		textLongitude.setColumns(10);

		textRadius = new JTextField();
		PlainDocument doc3 = (PlainDocument) textLatitude.getDocument();
		doc3.setDocumentFilter(new LatLongFilter());
		textRadius.setBounds(293, 293, 114, 19);
		frame.getContentPane().add(textRadius);
		textRadius.setColumns(10);

		JLabel lblNewLabel = new JLabel("Latitude");
		lblNewLabel.setBounds(135, 172, 69, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Longitude");
		lblNewLabel_1.setBounds(135, 235, 118, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Radius");
		lblNewLabel_2.setBounds(135, 295, 69, 15);
		frame.getContentPane().add(lblNewLabel_2);

		// JRadioButton rdbtnNewRadioButton = new JRadioButton("Circle");
		// rdbtnNewRadioButton.addActionListener(new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {

		// }
		// });
		// rdbtnNewRadioButton.setBounds(111, 76, 151, 23);
		// frame.getContentPane().add(rdbtnNewRadioButton);

		// JRadioButton rdbtnSquare = new JRadioButton("Square");
		// rdbtnSquare.addActionListener(new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// }
		// });
		// rdbtnSquare.setBounds(278, 76, 151, 23);
		// frame.getContentPane().add(rdbtnSquare);

		// JRadioButton rdbtnText = new JRadioButton("Text");
		// rdbtnText.setBounds(425, 76, 151, 23);
		// frame.getContentPane().add(rdbtnText);

		// JMenu mnNewMenu = new JMenu("File");
		// mnNewMenu.setBounds(12, 0, 93, 19);
		// frame.getContentPane().add(mnNewMenu);

		// JComboBox<?> comboBox = new JComboBox<Object>();
		// comboBox.setBounds(216, 26, 253, 24);
		// frame.getContentPane().add(comboBox);

		// JLabel lblNewLabel_3 = new JLabel("Select Shape");
		// lblNewLabel_3.setBounds(91, 31, 125, 15);
		// frame.getContentPane().add(lblNewLabel_3);

		// textField = new JTextField();
		// textField.setBounds(252, 29, 114, 19);
		// frame.getContentPane().add(textField);
		// textField.setColumns(10);
	}

	public void update(Observable observable, Object object)
	{
		System.out.println("OverlayEntryView::update");
		
		//Look into doing this without casting to OverlayEntryModel
		//Can Object be a key-value pair (probably)?
		if (observable instanceof OverlayEntryModel)
		{
			OverlayEntryModel model = (OverlayEntryModel)observable;
			this.textLatitude.setText(Double.toString(model.getLatitude()));
			this.textLongitude.setText(Double.toString(model.getLongitude()));
			this.textRadius.setText(Double.toString(model.getRadius()));
		}
	}

	public void addController(ActionListener controller)
	{
		this.btnOK.addActionListener(controller);
	}

	public double getEnteredLatitude()
	{
		return Double.parseDouble(this.textLatitude.getText());
	}
	
	public double getEnteredLongitude()
	{
		return Double.parseDouble(this.textLongitude.getText());
	}
	
	public double getEnteredRadius()
	{
		return Double.parseDouble(this.textRadius.getText());
	}
}
