public class ThreadAccept extends Thread
{
	private boolean continuar;
	public ThreadAccept()
	{
		continuar=true;
	}
	public void run()
	{
		try
		{
			System.out.println("SERVIDOR ACTIVO...");
			while(continuar)
			{
				Servidor.start();
			}
		}
		catch(Exception ex)
		{
			System.out.println("SE PARARON LOS SERVICIOS...");
		}
	}
}