import java.io.*;
import java.net.*;
public class HiloLectorServidor extends Thread
{
	private Socket s;
	private StringBuffer buf;
	private boolean put=false;
	private boolean yaPuede=false;
	private String texto;
	private OutputStream os;
	private InputStream is;
	private PrintStream ps;
	private String usuario;
	private int c;
	public HiloLectorServidor(Socket s)
	{
		super();
		this.s=s;
		try
		{
			is=s.getInputStream();
			os=s.getOutputStream();
			ps=new PrintStream(os);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			System.out.println("Se perdio la conexion...");
		}
	}
	public void run()
	{
		try
		{
			imprime("csm.addressbook");
			imprime("*****ADDRESSBOOK SERVER*****");
			Servidor.setUser(0);
			setPut(false);
			String nada="";
			c=0;
			buf=new StringBuffer();
			while(c!=-1)
			{
				c=is.read();
				buf.append((char)c);
				if((char)c=='\n')
				{
					texto=buf.toString();
					if(texto.startsWith("USER"))
					{
						if(texto.substring(0,texto.length()-2).equals("USER"))
						{
							imprime("-ERR Utilice USER nombre de archivo");
						}
						else if(texto.indexOf(' ',3)!=-1)
						{
							usuario=texto.substring(5,texto.length()-2);
							if(usuario.indexOf(".sab")==-1)
							{
								imprime("-ERR Indique el nombre de la agenda .sab");
							}
							else if (Servidor.getUser()==0)
							{
								Servidor.cargaAgenda(usuario,this);
							}
							else
							{
								imprime("+OK Cambio de Usuario");
								imprime("+OK Introduce de nuevo el usuario");
								yaPuede=false;
								Servidor.setP(0);
								Servidor.setUser(0);
								Servidor.setNecesitaPass(false);
							}
						}
						else
						{
							imprime("-ERR Comando desconocido");
						}
						buf=new StringBuffer(nada);
					}
					else if(texto.startsWith("PASS"))
					{
						if(Servidor.necesitaPass())
						{
							String oportunidad="";
							try{
							oportunidad=texto.substring(5,texto.length()-2);
							}
							catch(Exception ex){}
							String pwd=Servidor.tomaPassword();
							if(pwd.equals(oportunidad))
							{
								yaPuede=true;
								imprime("+OK Password autentificado");
								Servidor.setNecesitaPass(false);
								Servidor.setP(2);
							}
							else
							{
								imprime("-ERR Password invalido");
								Servidor.setNecesitaPass(false);
								Servidor.setUser(0);
							}
						}
						else
						{
							if(Servidor.getP()==1)
							{
								imprime("-ERR No necesitas PASSWORD");
							}
							else if(Servidor.getP()==2)
							{
								imprime("-ERR PASSWORD ya autentificado");
							}
							else
							{
								imprime("-ERR Introduce usuario primero");
								Servidor.setUser(0);
							}
						}
						buf=new StringBuffer(nada);
					}
					else if(texto.startsWith("LIST"))
					{
						if(yaPuede)
						{
							imprime("+OK Se muestra el documento");
							Servidor.mostrarDoc(usuario,this);
						}
						else
						{
							imprime("-ERR Autentifique usuario");
							Servidor.setNecesitaPass(false);
						}
						buf=new StringBuffer(nada);
					}
					else if(texto.startsWith("PUT"))
					{
						if (yaPuede)
						{
							imprime("+OK Escriba a continuacion el documento acabandolo con la linea </addressbook>");
							setPut(true);
						}
						else
						{
							imprime("-ERR Autentifique usuario");
							Servidor.setNecesitaPass(false);
						}
						buf=new StringBuffer(nada);
					}
					else if(texto.startsWith("CLOSE"))
					{
						imprime("Hasta pronto...!!");
						s.shutdownInput();
						s.shutdownOutput();
						buf=new StringBuffer(nada);
					}
					else if(texto.startsWith("HELP"))
					{
						imprime("+OK USER,PASS,LIST,PUT,CLOSE");
						buf=new StringBuffer(nada);
					}
					else if(texto.startsWith("<addressbook "))
					{
						if(yaPuede)
						{
							if(put)
							{
								Servidor.modificaAgenda(usuario,texto,this);
								buf=new StringBuffer(nada);
							}
							else
							{
								imprime("-ERR Utilice el comando PUT");
								buf=new StringBuffer(nada);
							}
						}
						else
						{
							imprime("-ERR Autentifique usuario");
							Servidor.setNecesitaPass(false);
							buf=new StringBuffer(nada);
							setPut(false);
						}
					}
					else
					{
						if(Servidor.esGenerar())
						{
							if(texto.substring(0,texto.length()-2).equals("SI"))
							{
								Servidor.generaAgenda(usuario,this);
								Servidor.setGenerar(false);
							}
							else if(texto.substring(0,texto.length()-2).equals("NO"))
							{
								imprime("-ERR Agenda no generada");
								Servidor.setGenerar(false);
							}
							else 
							{
								imprime("-ERR Respuesta invalida");
							}
							buf=new StringBuffer(nada);
						}
						else
						{
							imprime("-ERR Comando desconocido");
							Servidor.setNecesitaPass(false);
							buf=new StringBuffer(nada);
							setPut(false);
						}
					}
				}
			}
		}
		catch(Exception ev)
		{
			System.out.println("Se perdio la conexion...");
		}
	}
	public void imprime(String s)
	{
		ps.println(s);
	}
	public void setYapuede(boolean b)
	{
		yaPuede=b;
	}
	public void setPut(boolean b)
	{
		put=b;
	}
}