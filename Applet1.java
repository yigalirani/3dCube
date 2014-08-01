/*
	A basic extension of the java.applet.Applet class
 */

import java.awt.*;
import java.applet.*;

public class Applet1 extends Applet
{
  public void init()
	{
		//{{INIT_CONTROLS
		setLayout(new BorderLayout(0,0));
		setSize(600,600);
		add("Center",flatCube1);
		flatCube1.setBounds(0,35,600,565);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		add("North",panel1);
		panel1.setBounds(0,0,600,35);
		zoom_in.setLabel("zoom in");
		panel1.add(zoom_in);
		zoom_in.setBackground(java.awt.Color.lightGray);
		zoom_in.setBounds(93,6,57,23);
		zoom_out.setLabel("zoom out");
		panel1.add(zoom_out);
		zoom_out.setBackground(java.awt.Color.lightGray);
		zoom_out.setBounds(155,6,64,23);
		transparent.setLabel("transparent");
		panel1.add(transparent);
		transparent.setBounds(224,6,93,23);
		choice1.addItem("1");
		choice1.addItem("2");
		choice1.addItem("3");
		choice1.addItem("4");
		choice1.addItem("5");
		choice1.addItem("6");
		choice1.addItem("7");
		choice1.addItem("8");
		choice1.addItem("9");
		choice1.addItem("10");
		try {
			choice1.select(2);
		}
		catch (IllegalArgumentException e) { }
		panel1.add(choice1);
		choice1.setBounds(322,5,42,25);
		label1.setText("x");
		panel1.add(label1);
		label1.setBounds(369,6,19,23);
		panel1.add(choice2);
		choice2.setBounds(393,5,42,25);
		choice2.addItem("1");
		choice2.addItem("2");
		choice2.addItem("3");
		choice2.addItem("4");
		choice2.addItem("5");
		choice2.addItem("6");
		choice2.addItem("7");
		choice2.addItem("8");
		choice2.addItem("9");
		choice2.addItem("10");
		try {
			choice2.select(2);
		}
		catch (IllegalArgumentException e) { }
		
		label2.setText("x");
		panel1.add(label2);
		label2.setBounds(440,6,19,23);
		panel1.add(choice3);
		choice3.setBounds(464,5,42,25);
		choice3.addItem("1");
		choice3.addItem("2");
		choice3.addItem("3");
		choice3.addItem("4");
		choice3.addItem("5");
		choice3.addItem("6");
		choice3.addItem("7");
		choice3.addItem("8");
		choice3.addItem("9");
		choice3.addItem("10");
		try {
			choice3.select(2);
		}
		catch (IllegalArgumentException e) { }
		
		//}}
	}
	public boolean handleEvent(Event evt){
	  if (evt.target == zoom_in){
	    flatCube1.dist/=1.1;
	    return true;
	  }
	  if (evt.target == zoom_out){
	    flatCube1.dist*=1.1;
	    return true;
	  }	
	  if (evt.target instanceof java.awt.Choice){
	    flatCube1.reset(choice1.getSelectedIndex()+1 , choice2.getSelectedIndex()+1, choice3.getSelectedIndex()+1);
	  }
	  flatCube1.transparent.status=transparent.getState();
	  return false;
	}
	
	//{{DECLARE_CONTROLS
	FlatCube flatCube1 = new FlatCube();
	java.awt.Panel panel1 = new java.awt.Panel();
	java.awt.Button zoom_in = new java.awt.Button();
	java.awt.Button zoom_out = new java.awt.Button();
	java.awt.Checkbox transparent = new java.awt.Checkbox();
	java.awt.Choice choice1 = new java.awt.Choice();
	java.awt.Label label1 = new java.awt.Label();
	java.awt.Choice choice2 = new java.awt.Choice();
	java.awt.Label label2 = new java.awt.Label();
	java.awt.Choice choice3 = new java.awt.Choice();
	//}}
}
