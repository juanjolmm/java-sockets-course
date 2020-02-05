import java.net.*;
import java.io.*;
import csm.addressbook.*;
public class HiloLector extends Thread
{
	private BufferedReader tec;
	private Socket s;
	private InputStream is;
	private OutputStream os;
	private PrintStream pw;
	private StringBuffer sb;
	private CharArrayWriter caw;
	private String usuario;
	private int validar;
	private String docFinal;
	private CharArrayReader car;
	private String fin;
	private BufferedReader buff;
	public HiloLector(String host,String user)throws Exception
	{
			s=new Socket(host,950);
			usuario=user; 
			is=s.getInputStream();
			os=s.getOutputStream();
			pw=new PrintStream(os);
			tec=new BufferedReader(new InputStreamReader(System.in));
			buff=new BufferedReader(new InputStreamReader(is));
			sb=new StringBuffer();
	}
	public void run()
	{
		try
		{
			pw.println("USER "+usuario);
			String vuelta=escucha("Se requiere password.","Quiere generar una nueva?SI/NO","+OK Sin Contraseña.");
			if (vuelta.indexOf("Se requiere password.")!=-1)
			{
				System.out.println("+OK Se requiere password:");
				String pass=tec.readLine();
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
					ClienteLee.principio();
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
				System.out.println("-ERR Usuario desconocido...");
				caw.close();
				s.close();
				ClienteLee.principio();
			}
			else
			{
				System.out.println("-ERR Indica la agenda .sab");
				caw.close();
				s.close();
				ClienteLee.principio();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error en el documento XML: "+ex.toString());
			ClienteLee.principio();
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
		caw.close();
		car.close();
		s.close();
		ClienteLee.setAddressBook(ab);
	}
}