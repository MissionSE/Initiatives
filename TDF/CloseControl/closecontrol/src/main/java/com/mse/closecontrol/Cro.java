package com.mse.closecontrol;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Cro extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CroPane croPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cro frame = new Cro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 300, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		croPane = new CroPane();
		setContentPane(croPane);
	}

}
