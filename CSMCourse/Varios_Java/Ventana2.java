import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics.*;
class MiVentana extends JFrame
{
	public MiVentana(String s)
	{
		super(s);
		this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				});
	}
	public void paint(Graphics g)
	{
		g.drawString("Mi Ventana",70,100);
	}
}
public class Ventana2
{
	public static MiVentana v=new MiVentana("Ventana Java");
	public static void main(String []args)
	{
		v.setSize(200,200);
		v.show();
	}
}