import java.io.*;
public class EuroConversor
{
	public static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in) );
        public static int opcion=0;
        public static void main (String []args)
        {
		repetir();
	}
	public static void euros()
	{
		try
		{
			System.out.println("\nINDICA LA CANTIDAD DE EUROS.");
			double euros=Double.parseDouble(teclado.readLine());
			double pesetas=euros*166.386;
			System.out.println("\nSon "+pesetas+" pts.");
		}
		catch(Exception ex)
		{
			System.out.println("No valido.");
			euros();
		}
	}
	public static void pesetas()
	{
		try
		{
			System.out.println("\nINDICA LA CANTIDAD DE PESETAS.");
			int pesetas=Integer.parseInt(teclado.readLine());
			double euros=pesetas/166.386;
			System.out.println("\nSon "+euros+" euros.");
		}
		catch(Exception ex)
		{
			System.out.println("No valido.");
			pesetas();	
		}
	}
	public static void repetir()
	{
		try
		{
			do
			{
				System.out.println("\n1.DE EUROS A PESETAS.");
				System.out.println("2.DE PESETAS A EUROS.");
				System.out.println("3.SALIR.");
				opcion=Integer.parseInt(teclado.readLine());
				switch (opcion)
				{
					case 1:euros();
                                        break;
                            		case 2:pesetas();
                                        break;
                            		case 3:System.out.println("Adios...");
                                        break;
                            		default:System.out.println("No implementado");
                                        break;
				}
			}
			while(opcion!=3);
		}
		catch(Exception ex)
		{
			repetir();
		}
	}
}