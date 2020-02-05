import csm.addressbook.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Servidor
{
	private static BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
	private static StringBuffer buffer=new StringBuffer();
	private static boolean necesitaPass=false;
	private static boolean continuar=true;
	private static boolean generar=false;
	private static int p=0;
	private static int user=0;
	private static AddressBook ab;
	private static File archivo;
	private static String consiguePass;
	private static Reader r;
	private static Reader r2;
	private static StringBuffer sb;
	private static int cual;
	private static ThreadAccept ta;
	private static ServerSocket s;
	private static HiloLectorServidor hl;
	public static void main(String []args)
	{
		System.out.println("*****CSM.ADDRESSBOOK SERVER*****");
		System.out.println();
		System.out.println("1.COMENZAR SERVICIOS");
		System.out.println("2.LISTAR CONEXIONES DE SESION");
		System.out.println("3.NO ADMITIR MAS CLIENTES");
		System.out.println("4.AYUDA");
		System.out.println("5.CERRAR");
		do
		{
			try
			{
				int cual=Integer.parseInt(teclado.readLine());
				switch(cual)
				{
					case 1:
						comenzar();
						break;
					case 2:
						listar();
						break;
					case 3:
						close();
						break;
					case 4:
						help();
						break;
					case 5:
						quit();
						break;
					default:
						System.out.println("OPERACION NO VALIDA...");
				}
			}
			catch(Exception ex)
			{
				System.out.println("OPERACION NO VALIDA...");
			}
		}
		while(cual!=5);
	}
	public static void comenzar()
	{
		try
		{
			s=new ServerSocket(950,10);
			ta=new ThreadAccept();
			ta.start();
		}
		catch(Exception ex)
		{
			System.out.println("SERVIDOR YA INICIADO...");
		}
	}
	public static void listar()
	{
		try
		{
			String users=buffer.toString();
			System.out.println("USUARIOS CONECTADOS DURANTE LA SESION:");
			System.out.println(users);
		}
		catch(Exception ex)
		{
			System.out.println("SIN CLIENTES...");
		}
	}
	public static void help()
	{
		System.out.println("1.COMENZAR SERVICIOS");
		System.out.println("2.LISTAR CONEXIONES DE SESION");
		System.out.println("3.NO ADMITIR MAS CLIENTES");
		System.out.println("4.AYUDA");
		System.out.println("5.CERRAR");
	}
	public static void close()
	{
		try{
			s.close();
			s=null;
			}
			catch(Exception ex)
			{
				System.out.println("SERVIDOR NO INICIADO...");
			}
	}
	public static void quit()
	{
		System.exit(0);
	}
	public static void cargaAgenda(String s,HiloLectorServidor hl)
	{
		try
		{
			archivo=new File(s);
			InputStream is=new FileInputStream(archivo);
			r=new InputStreamReader(is);
			ab=AddressBook.loadAddressBook(r);
			hl.imprime("+OK usuario valido.");
			setUser(1);
			consiguePass=ab.getPassword();
			if(consiguePass!=null)
			{
				hl.imprime("+OK Se requiere password.");
				consiguePass=ab.getPassword();
				setNecesitaPass(true);
			}
			else
			{
				hl.imprime("+OK Sin Contraseña.");
				setP(1);
				hl.setYapuede(true);
			}
			is.close();
			r.close();
		}
		catch(FileNotFoundException ex)
		{
			hl.imprime("-ERR Agenda no encontrada");
			hl.imprime("+OK Quiere generar una nueva?SI/NO");
			setGenerar(true);
		}
		catch(IOException ex)
		{
			hl.imprime("IOEXCePTioN");
		}
		catch(org.xml.sax.SAXException ex)
		{
			hl.imprime(ex.toString());
		}
	}
	public static void generaAgenda(String s,HiloLectorServidor hl)
	{
		try
		{
			AddressBook add=new AddressBook(s);
			File nuevoArch=new File(s);
			Writer nuevo=new FileWriter(nuevoArch);
			add.save(nuevo);
			hl.imprime("+OK Agenda generada: "+s);
			hl.imprime("+OK Autentifique usuario...");
			nuevo.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString());
		}
	}
	public static void mostrarDoc(String usuario,HiloLectorServidor hl)throws Exception
	{
		try{
		Reader r=new FileReader(usuario);
		StringBuffer mostrar=new StringBuffer();
		int c=0;
		while(c!=-1)
		{
			c=r.read();
			mostrar.append((char)c);
		}
		String contenido=mostrar.toString();
		int desde=contenido.indexOf("<addressbook");
		hl.imprime(contenido.substring(0,contenido.length()));
		r.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	public static boolean necesitaPass()
	{
		return necesitaPass;
	}
	public static String tomaPassword()
	{
		return consiguePass;
	}
	public static void setNecesitaPass(boolean bool)
	{
		necesitaPass=bool;
	}
	public static boolean esGenerar()
	{
		return generar;
	}
	public static void setGenerar(boolean b)
	{
		generar=b;
	}
	public static void modificaAgenda(String user,String texto,HiloLectorServidor hl)
	{
		try
		{
			CharArrayWriter caw=new CharArrayWriter();
			String textF="<?xml version=\"1.0\"?>"+texto;
			caw.write(textF,0,textF.length());
			CharArrayReader car=new CharArrayReader(caw.toCharArray());
			ab=AddressBook.loadAddressBook(car);
			caw.close();
			car.close();
			Writer modificado2=new FileWriter(user,false);
			String textFs="<?xml version=\"1.0\"?>"+texto;
			char []text2=textFs.toCharArray();
			for(int i=0;i<text2.length;i++)
			{
				modificado2.write(text2[i]);
			}
			modificado2.close();	
			hl.imprime("+OK Archivo modificado!!");
			hl.setPut(false);
		}
		catch(Exception ex)
		{
			hl.imprime("Error en el documento, se muestra la excepcion: "+ex.toString());
			hl.setPut(false);
		}
	}
	public static int getP()
	{
		return p;
	}
	public static int getUser()
	{
		return user;
	}
	public static void setUser(int b)
	{
		user=b;
	}
	public static void setP(int pa)
	{
		p=pa;
	}
	public static void start()throws Exception
	{
		Socket so=s.accept();
		System.out.println("Conexion con "+so);
		hl=new HiloLectorServidor(so);
		hl.start();
		buffer.append(so.toString()+"\n");
	}
}	