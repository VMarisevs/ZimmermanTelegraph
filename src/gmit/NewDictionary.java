package gmit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class NewDictionary extends JFrame {
	JTextArea txtAnalyser;
	private JTextField txtMin;
	private JTextField txtMax;
	
	public NewDictionary() {
		setType(Type.POPUP);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Populate common words", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 421, 296);
		panel.add(panel_1);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(61, 32, 90, 36);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnOpenActionPerformed(evt);
			}
		});
		panel_1.setLayout(null);
		panel_1.add(btnOpen);
		
		JButton btnAnalyse = new JButton("Analyse");
		btnAnalyse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAnalyseActionPerformed(evt);
			}
		});
		btnAnalyse.setBounds(156, 32, 90, 36);
		panel_1.add(btnAnalyse);
		
		
		txtAnalyser = new JTextArea();
		txtAnalyser.setWrapStyleWord(true);
		txtAnalyser.setBounds(10, 67, 400, 170);
		txtAnalyser.setTabSize(1);
		txtAnalyser.setRows(15);
		txtAnalyser.setColumns(60);
		panel_1.add(txtAnalyser);
		
		JScrollPane scrollPane = new JScrollPane(txtAnalyser);
		scrollPane.setBounds(10, 94, 400, 170);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Generate dictionary", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(441, 11, 226, 296);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		txtMin = new JTextField();
		txtMin.setText("1");
		txtMin.setBounds(130, 28, 86, 20);
		panel_2.add(txtMin);
		txtMin.setColumns(10);
		
		txtMax = new JTextField();
		txtMax.setText("99999");
		txtMax.setBounds(130, 59, 86, 20);
		panel_2.add(txtMax);
		txtMax.setColumns(10);
		
		JButton btnGenerateDictionary = new JButton("Generate Dictionary");
		btnGenerateDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				btnGenerateDictionaryActionPerformed(evt);
				
			}
		});
		btnGenerateDictionary.setBounds(56, 90, 160, 40);
		panel_2.add(btnGenerateDictionary);
		
		JLabel lblMin = new JLabel("Min:");
		lblMin.setBounds(74, 31, 46, 14);
		panel_2.add(lblMin);
		
		JLabel lblMax = new JLabel("Max:");
		lblMax.setBounds(74, 62, 46, 14);
		panel_2.add(lblMax);
	}
	protected void btnGenerateDictionaryActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		Dictionary dc = new Dictionary();
		try {
			dc.generateNewDictionary(Integer.parseInt(txtMin.getText()), Integer.parseInt(txtMax.getText()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Please, check the number format.");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot generate dictionary\n"+e.getMessage());
		}
	}
	protected void btnAnalyseActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String msg = txtAnalyser.getText().replaceAll("\n", " ");
		int commonWordsCount, range;
		msg = msg.replaceAll("\t", " ");
		
		try {
			commonWordsCount = EditFiles.populateCommonEnglishWords(msg);
			System.out.println("Common english words: " + commonWordsCount);
			
			range = (commonWordsCount - 899)*43 + 74407 + 1;
			System.out.println("For this amount of words, I would recommend range between 1 and " + range);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	protected void btnOpenActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		JFileChooser fileopen = new JFileChooser();
		
		String workingDir = System.getProperty("user.dir");
		fileopen.setCurrentDirectory(new File(workingDir+"\\messages"));
		int ret = fileopen.showDialog(null, "Open");               
		if (ret == JFileChooser.APPROVE_OPTION) {
		    File file = fileopen.getSelectedFile();
		    
		    try {
		    	txtAnalyser.setText(EditFiles.openTXT(file.getAbsolutePath()));
			} catch (Exception e) {}
		}
	}
	public void start(){
		
		this.setSize(700, 400);
		this.setVisible(true);
	}
}
