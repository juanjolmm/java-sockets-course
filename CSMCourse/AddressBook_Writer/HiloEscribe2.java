import java.net.*;
import java.io.*;
public class HiloEscribe2 extends Thread
{
	private Socket s;
	private OutputStream os;
	private PrintStream ps;
	private String texto1;
	private String texto;
	private String usuario;
	private String password;
	public HiloEscribe2(String host,String user,String pass,String text)
	{
		super();
		try
		{
			this.usuario=user;
			this.password=pass;
			this.s=new Socket(host,950);
			this.os=s.getOutputStream();
			this.ps=new PrintStream(os);
			this.texto1=text.substring(22,text.length());
			this.texto=texto1.replace('\n',' ');
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public void run()
	{
		try
		{
			ps.println("USER "+usuario);
			ps.println("PASS "+password);
			ps.println("PUT");
			ps.println(texto);
			ps.println("CLOSE");
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}