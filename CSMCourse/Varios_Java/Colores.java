import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class Colores extends Applet implements ActionListener
{
	public Button b=new Button("ROJO");
	public Button b2=new Button("BLANCO");
	public Button b3=new Button("AMARILLO");
	public Button b4=new Button("AZUL");
	public Button b5=new Button("NEGRO");
	public Button b6=new Button("VERDE");
	public Panel p=new Panel();
	public void init()
	{
		this.setLayout(new BorderLayout());
		p.setLayout(new GridLayout(3,3));
		p.add(b);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		p.add(b5);
		p.add(b6);
		b.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		this.add("South",p);
	}
	public void actionPerformed(ActionEvent ev)
	{
		Object culpable=ev.getSource();
		if(culpable==b){this.setBackground(Color.red);}
		if(culpable==b2){this.setBackground(Color.white);}
		if(culpable==b3){this.setBackground(Color.yellow);}
		if(culpable==b4){this.setBackground(Color.blue);}
		if(culpable==b5){this.setBackground(Color.black);}
		if(culpable==b6){this.setBackground(Color.green);}
	}
}