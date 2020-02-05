import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MiWin extends JFrame implements ActionListener
{
	public JTextArea t=new JTextArea();
	public JButton boton=new JButton("Abrir");
	public BorderLayout border=new BorderLayout();
	public JPanel panel=new JPanel();
	public FlowLayout flow=new FlowLayout();
	public JMenuBar menu=new JMenuBar();
	public JMenu menu1=new JMenu("Ayuda");
	public JMenuItem item=new JMenuItem("About");
	public MiWin(String s)
	{
		super(s);
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
		this.setResizable(false);
		this.setJMenuBar(menu);
		menu.add(menu1);
		menu1.add(item);
		item.addActionListener(this);
		this.getContentPane().setLayout(border);
		this.getContentPane().add(t);
		panel.setLayout(flow);
		panel.add(boton);
		this.getContentPane().add("South",panel);
	}
	public void actionPerformed(ActionEvent e)
	{
		boton.setText("JUANJO");
	}
}
public class NewWin
{
	public static MiWin v=new MiWin("Ventana Java");
	public static void main(String []args)
	{
		v.setSize(200,200);
		v.show();
	}
}