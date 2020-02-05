import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MiWin2 extends JFrame implements ActionListener
{
	public String[] data = {"Cifrar", "Descifrar"};
	public String[] data2 = {"1", "2","3","4","5","6","7","8","9","10","11","12","13"};
	public BorderLayout border=new BorderLayout();
	public JPanel panel=new JPanel();
	public JPanel panel1=new JPanel();
	public JPanel panel2=new JPanel();
	public FlowLayout flow=new FlowLayout();
	public FlowLayout flow1=new FlowLayout();
	public BorderLayout flow2=new BorderLayout();
	public JMenuBar menu=new JMenuBar();
	public JMenu menu1=new JMenu("Ayuda");
	public JMenuItem item=new JMenuItem("About");
	public JLabel label1=new JLabel("OPCION");
	public JComboBox lista1=new JComboBox(data);
	public JLabel label2=new JLabel("INDICE DE CIFRADO");
	public JComboBox campo1=new JComboBox(data2);
	public JLabel label3=new JLabel("FRASE:");
	public JTextField campo2=new JTextField("",20);
	public JButton boton=new JButton("Aceptar");
	public JTextField campo3=new JTextField("");
	public MiWin2(String s)
	{
		super(s);
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
		this.setResizable(false);
		this.setJMenuBar(menu);
		menu.add(menu1);
		menu1.add(item);
		item.addActionListener(this);
		this.getContentPane().setLayout(border);
		panel.setLayout(flow);
		panel.add(label1);
		panel.add(lista1);
		panel1.setLayout(flow1);
		panel1.add(label2);
		panel1.add(campo1);
		panel2.setLayout(flow2);
		panel2.add("North",label3);
		panel2.add("West",campo2);
		panel2.add("East",boton);
		boton.addActionListener(this);
		panel2.add("South",campo3);
		this.getContentPane().add("North",panel);
		this.getContentPane().add(panel1);
		this.getContentPane().add("South",panel2);
		this.pack();
	}
	public void actionPerformed(ActionEvent e)
	{
		Object culpable=e.getSource();
		Object cif=lista1.getSelectedItem();
		String cif1=cif.toString();
		if (culpable==boton && cif1.equals("Cifrar"))
		cifradoCesar();
		if (culpable==boton && cif1.equals("Descifrar"))
		descifradoCesar();
	}
	public void cifradoCesar()
	{
		String fraseCifrar=campo2.getText();
		char []frasecifrar=fraseCifrar.toCharArray();
		Object indice=campo1.getSelectedItem();
		int indiceF=Integer.parseInt(indice.toString());
		for(int i=0;i<frasecifrar.length;i++)
		{
			if('a'<=frasecifrar[i] && 'z'>=frasecifrar[i])
			{
				frasecifrar[i]+=indiceF;
				if(frasecifrar[i]>'z')
				frasecifrar[i]-=26;
			}
			if('A'<=frasecifrar[i] && 'Z'>=frasecifrar[i]) 
			{
				frasecifrar[i]+=indiceF;
				if(frasecifrar[i]>'Z')
				frasecifrar[i]-=26;
			}
		}
		String finalS=new String(frasecifrar);
		campo3.setText(finalS);
	}
	public void descifradoCesar()
	{
		String fraseCifrar=campo2.getText();
		char []frasecifrar=fraseCifrar.toCharArray();
		Object indice=campo1.getSelectedItem();
		int indiceF=Integer.parseInt(indice.toString());
		for(int i=0;i<frasecifrar.length;i++)
		{
			if('a'<=frasecifrar[i] && 'z'>=frasecifrar[i])
			{
				frasecifrar[i]-=indiceF;
				if(frasecifrar[i]<'a')
				frasecifrar[i]+=26;
			}
			if('A'<=frasecifrar[i] && 'Z'>=frasecifrar[i]) 
			{
				frasecifrar[i]-=indiceF;
				if(frasecifrar[i]<'A')
				frasecifrar[i]+=26;
			}
		}
		String finalS=new String(frasecifrar);
		campo3.setText(finalS);
	}
}
public class NewWin2
{
	public static MiWin2 v=new MiWin2("Ventana Java");
	public static void main(String []args)
	{
		v.show();
	}
}