package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import search.SearchWeb;

public class GUI implements ActionListener{
	
	//JButton button;
	JFrame frame;
	JTextArea area;
	
	
	
	public static void main(String[] args){
		GUI gui=new GUI();
		gui.go();
	}
	
	public void go(){
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton button=new JButton("GO!");
		button.addActionListener(this);
		
		area=new JTextArea(1,20);
		
		JPanel panel=new JPanel();
		panel.add(area);
		JPanel buttonPanel=new JPanel();
		buttonPanel.add(BorderLayout.CENTER,button);
		buttonPanel.setSize(30, 30);
		JPanel drawPanel=new JPanel();
		drawPanel.setSize(100, 100);
		
		frame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		frame.getContentPane().add(BorderLayout.NORTH,drawPanel);
		frame.getContentPane().add(BorderLayout.CENTER,panel);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event){
		String query=area.getText();
		SearchWeb search=new SearchWeb();
		try {
			search.searchQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
