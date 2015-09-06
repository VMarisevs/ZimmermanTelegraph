package gmit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JCheckBox;
import javax.swing.JRadioButtonMenuItem;


public class EncodeDecodeGUI extends JFrame {
	private JTextField txtFilePath;
	private final ButtonGroup bgSettingsEncodeDecode = new ButtonGroup();
	JRadioButton rdbtnDecode;
	JRadioButton rdbtnEncode;
	JTextArea txtEntered;
	JTextArea txtGenerated;
	JCheckBox chckbxZerosAtThe;
	JRadioButtonMenuItem rdbtnmntmEncode;
	JRadioButtonMenuItem rdbtnmntmDecode;
	JRadioButtonMenuItem rdbtnmntmAscendingEncoder;
	JRadioButtonMenuItem rdbtnmntmDescendingEncoder;
	
	
	Dictionary dictionary = new Dictionary();
	private final ButtonGroup bgFileSettingsEncodeDecode = new ButtonGroup();
	private final ButtonGroup bgAscDescEncoder = new ButtonGroup();
	
	public EncodeDecodeGUI() {
		setTitle("Zimmerman Telegraph by Vladislavs Marisevs");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panelSettings = new JPanel();
		panelSettings.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSettings.setBounds(350, 57, 100, 80);
		panel.add(panelSettings);
		
		rdbtnEncode = new JRadioButton("Encode");
		rdbtnEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				rdbtnmntmEncode.setSelected(true);
			}
		});
		bgSettingsEncodeDecode.add(rdbtnEncode);
		panelSettings.add(rdbtnEncode);
		
		
		rdbtnDecode = new JRadioButton("Decode");
		rdbtnDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				rdbtnmntmDecode.setSelected(true);
			}
		});
		bgSettingsEncodeDecode.add(rdbtnDecode);
		panelSettings.add(rdbtnDecode);
		
		JPanel panelDictionary = new JPanel();
		panelDictionary.setBorder(new TitledBorder(null, "Dictionary", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDictionary.setBounds(10, 57, 330, 80);
		panel.add(panelDictionary);
		
		txtFilePath = new JTextField();
		txtFilePath.setEditable(false);
		txtFilePath.setColumns(20);
		panelDictionary.add(txtFilePath);
		
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBrowseActionPerformed(evt);
			}
		});
		panelDictionary.add(btnLoad);
		
		JPanel panelEncodeDecode = new JPanel();
		panelEncodeDecode.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEncodeDecode.setBounds(10, 148, 980, 313);
		panel.add(panelEncodeDecode);
		panelEncodeDecode.setLayout(null);
		
		txtEntered = new JTextArea();
		txtEntered.setBounds(1, 68, 742, 171);
		txtEntered.setLineWrap(true);
		txtEntered.setTabSize(1);
		txtEntered.setRows(15);
		txtEntered.setColumns(60);
		panelEncodeDecode.add(txtEntered);
		
		JScrollPane scrollPaneTxtEntered = new JScrollPane(txtEntered);
		scrollPaneTxtEntered.setBounds(10, 68, 475, 240);
		scrollPaneTxtEntered.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelEncodeDecode.add(scrollPaneTxtEntered);
		
		
		
		txtGenerated = new JTextArea();
		txtGenerated.setLineWrap(true);
		txtGenerated.setBounds(475, 102, 455, 184);
		txtGenerated.setTabSize(1);
		txtGenerated.setBackground(Color.WHITE);
		txtGenerated.setEditable(false);
		txtGenerated.setRows(15);
		txtGenerated.setColumns(60);
		panelEncodeDecode.add(txtGenerated);
		
		JScrollPane scrollPaneTxtGenerated = new JScrollPane(txtGenerated);
		scrollPaneTxtGenerated.setBounds(495, 68, 475, 240);
		//scrollPaneTxtEntered.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneTxtGenerated.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelEncodeDecode.add(scrollPaneTxtGenerated);


		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(430, 11, 90, 46);
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGenerateActionPerformed(evt);
			}
		});
		
		
		panelEncodeDecode.add(btnGenerate);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(10, 34, 89, 23);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnOpenActionPerformed(evt);
			}			
		});
		panelEncodeDecode.add(btnOpen);
		
		JButton btnSaveas = new JButton("SaveAs");
		btnSaveas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSaveasActionPerformed(evt);
			}			
		});
		btnSaveas.setBounds(845, 34, 89, 23);
		panelEncodeDecode.add(btnSaveas);
		
		chckbxZerosAtThe = new JCheckBox("Zeros at the front");
		chckbxZerosAtThe.setSelected(true);
		chckbxZerosAtThe.setBounds(264, 38, 160, 23);
		panelEncodeDecode.add(chckbxZerosAtThe);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 990, 21);
		panel.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenu mnDictionary = new JMenu("Dictionary");
		mnNewMenu.add(mnDictionary);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBrowseActionPerformed(evt);
			}
		});
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NewDictionary nd = new NewDictionary();
				nd.start();
			}
		});
		mnDictionary.add(mntmNew);
		mnDictionary.add(mntmLoad);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		JMenu mnNewMenu_1 = new JMenu("Settings");
		mnNewMenu.add(mnNewMenu_1);
		
		rdbtnmntmEncode = new JRadioButtonMenuItem("Encode");
		rdbtnmntmEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				rdbtnEncode.setSelected(true);
			}
		});
		bgFileSettingsEncodeDecode.add(rdbtnmntmEncode);
		mnNewMenu_1.add(rdbtnmntmEncode);
		
		rdbtnmntmDecode = new JRadioButtonMenuItem("Decode");
		rdbtnmntmDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				rdbtnDecode.setSelected(true);
			}
		});
		bgFileSettingsEncodeDecode.add(rdbtnmntmDecode);
		mnNewMenu_1.add(rdbtnmntmDecode);
		
		rdbtnmntmAscendingEncoder = new JRadioButtonMenuItem("Ascending Encoder");
		bgAscDescEncoder.add(rdbtnmntmAscendingEncoder);
		rdbtnmntmAscendingEncoder.setSelected(true);
		mnNewMenu_1.add(rdbtnmntmAscendingEncoder);
		
		rdbtnmntmDescendingEncoder = new JRadioButtonMenuItem("Descending Encoder");
		bgAscDescEncoder.add(rdbtnmntmDescendingEncoder);
		mnNewMenu_1.add(rdbtnmntmDescendingEncoder);
		mnNewMenu.add(mntmExit);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Icon icon = new Icon("javaicon.png");
				 final ImageIcon icon = new ImageIcon("javaicon.png");
				JOptionPane.showMessageDialog(null,"" +
						"Prepared by: Vladislavs Marisevs\n" +
						"For the attention of: John Healy\n" +
						"Class: Software Development Year 2\n" +
						"College: Galway Mayo Institute of Technology\n" +
						"Submission Date: 1th of April 2014\n" , 
						"Zimmerman Telegraph By Vladislavs Marisevs 2014",
					    JOptionPane.INFORMATION_MESSAGE, icon);
				
			}
		});
		mnAbout.add(mntmAbout);
	

	}

	private void btnBrowseActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

		JFileChooser fileopen = new JFileChooser();
		
		String workingDir = System.getProperty("user.dir");
		fileopen.setCurrentDirectory(new File(workingDir+"\\dictionary"));
		int ret = fileopen.showDialog(null, "Load");               
		if (ret == JFileChooser.APPROVE_OPTION) {
		    File file = fileopen.getSelectedFile();
		    
		    txtFilePath.setText(file.getName());
		    
		    dictionary.loadXML(file.getAbsolutePath());
		}
	}


	private void btnGenerateActionPerformed(ActionEvent evt) {
		if (!txtFilePath.getText().isEmpty()){
			
			//can do some stuff with text
			
			if(rdbtnEncode.isSelected()){
				String encoded = "";
				if (rdbtnmntmAscendingEncoder.isSelected()){
					encoded = dictionary.encodeMsgFast(txtEntered.getText(),chckbxZerosAtThe.isSelected());
				} else if (rdbtnmntmDescendingEncoder.isSelected()){
					encoded = dictionary.encodeMsg(txtEntered.getText(),chckbxZerosAtThe.isSelected());
				}
				
				txtGenerated.setText(encoded);
				
			} else if (rdbtnDecode.isSelected()){
				
				String decoded = dictionary.decodeMsg(txtEntered.getText());
				
				txtGenerated.setText(decoded);
				
			}
			
		
		}
	}
	
	private void btnOpenActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		JFileChooser fileopen = new JFileChooser();
		
		String workingDir = System.getProperty("user.dir");
		fileopen.setCurrentDirectory(new File(workingDir+"\\messages"));
		int ret = fileopen.showDialog(null, "Open");               
		if (ret == JFileChooser.APPROVE_OPTION) {
		    File file = fileopen.getSelectedFile();
		    
		    try {
				txtEntered.setText(EditFiles.openTXT(file.getAbsolutePath()));
			} catch (Exception e) {}
		}
	}
	
	private void btnSaveasActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub\
		
		if (!txtGenerated.getText().isEmpty()){
			JFileChooser filesave = new JFileChooser();
			
			String workingDir = System.getProperty("user.dir");
			filesave.setCurrentDirectory(new File(workingDir+"\\messages"));
			int ret = filesave.showDialog(null, "Save");               
			if (ret == JFileChooser.APPROVE_OPTION) {
			    File file = filesave.getSelectedFile();
			    
			    try {
			    	EditFiles.saveAsTXT(txtGenerated.getText().toString(), filesave.getSelectedFile()+".txt");
				} catch (Exception e) {}
			}
		}
	}
	
	public void start(){
		
		this.setSize(1000, 500);
		this.setVisible(true);
	}
}
