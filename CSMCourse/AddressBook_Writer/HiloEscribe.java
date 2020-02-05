import java.io.*;
import java.net.*;
import csm.addressbook.*;
public class HiloEscribe extends Thread
{
	private Socket s;
	private String user;
	private String pass;
	private InputStream is;
	private OutputStream os;
	private BufferedReader tec;
	private PrintStream pw;
	private CharArrayWriter caw;
	private CharArrayReader car;
	private BufferedReader buff;
	private String fin;
	private int validar;
	private String docFinal;
	public HiloEscribe(String host,String usuario)throws Exception
	{
		super();
		this.s=new Socket(host,950);
		this.user=usuario;
		this.is=s.getInputStream();
		this.os=s.getOutputStream();
		this.tec=new BufferedReader(new InputStreamReader(System.in));
		this.pw=new PrintStream(os);
		this.buff=new BufferedReader(new InputStreamReader(is));
	}
	public void run()
	{
		try
		{
			pw.println("USER "+user);
			String vuelta=escucha("Se requiere password.","Quiere generar una nueva?SI/NO","+OK Sin Contraseña.");
			if (vuelta.indexOf("Se requiere password.")!=-1)
			{
				System.out.println("+OK Se requiere password:");
				pass=tec.readLine();
				pw.println("PASS "+pass);
				pw.println("LIST");
				fin=escucha("</addressbook>","-ERR Password invalido");
				if(fin.indexOf("</addressbook>")!=-1)
				{
					validar=1;
					cargaAB();
				}
				else
				{
					System.out.println("-ERR Password invalido");
					caw.close();
					s.close();
					ClienteEscribe.principio();
				}
			}
			else if(vuelta.indexOf("+OK Sin Contraseña.")!=-1)
			{
				pw.println("LIST");
				fin=escucha("</addressbook>");
				validar=0;
				cargaAB();
			}
			else if(vuelta.indexOf("Quiere generar una nueva?SI/NO")!=-1)
			{
				System.out.println("-ERR Usuario no encontrado...");
				System.out.println("+OK Quiere generar una nueva...?(si/no)");
				String ok=tec.readLine();
				if (ok.equalsIgnoreCase("si"))
				{
					pw.println("SI");
					System.out.println("+OK Agenda Generada...");
					caw.close();
					s.close();
					ClienteEscribe.principio();
				}
				else
				{
					System.out.println("-ERR Agenda no generada...");
					caw.close();
					s.close();
					ClienteEscribe.principio();
				}
			}
			else
			{
				System.out.println("-ERR Indica la agenda .sab");
				caw.close();
				s.close();
				ClienteEscribe.principio();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error en el documento XML: "+ex.toString());
		}
	}
	public String escucha(String s,String otro,String otroMas)throws Exception
	{
		caw=new CharArrayWriter();
		String linea;
		do
		{
			linea=buff.readLine();
			caw.write(linea,0,linea.length());
		}
		while(linea.indexOf(s)==-1 && linea.indexOf(otro)==-1 && linea.indexOf(otroMas)==-1 && linea.indexOf(".sab")==-1);
		String server=new String(caw.toCharArray());
		return server;
	}
	public String escucha(String s,String otro)throws Exception
	{
		caw=new CharArrayWriter();
		String linea;
		do
		{
			linea=buff.readLine();
			caw.write(linea,0,linea.length());
		}
		while(linea.indexOf(s)==-1 && linea.indexOf(otro)==-1);
		String server=new String(caw.toCharArray());
		buff.close();
		return server;
	}
	public String escucha(String s)throws Exception
	{
		caw=new CharArrayWriter();
		String linea;
		do
		{
			linea=buff.readLine();
			caw.write(linea,0,linea.length());
		}
		while(linea.indexOf(s)==-1);
		String server=new String(caw.toCharArray());
		buff.close();
		return server;
	}
	public void cargaAB()throws Exception
	{
		if(validar==1)
		{
			docFinal=fin.substring(53,fin.length());
		}
		else if(validar==0)
		{
			docFinal=fin.substring(27,fin.length());
		}
		car=new CharArrayReader(docFinal.toCharArray());
		AddressBook ab=AddressBook.loadAddressBook(car);
		car.close();
		caw.close();
		s.close();
		ClienteEscribe.setAddressBook(ab,user,pass);
	}
}