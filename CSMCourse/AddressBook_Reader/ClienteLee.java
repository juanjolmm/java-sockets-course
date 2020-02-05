import java.io.*;
import csm.addressbook.*;
import java.util.*;
public class ClienteLee
{
	private static BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
	private static AddressBook ab;
	private static int opcion;
	private static HiloLector hl;
	public static void main(String[]args)throws Exception
	{
		principio();
	}
	public static void principio()
	{
		opcion=0;
		do
		{
			try
			{
				System.out.println("***ADDRESSBOOK READER***");
				System.out.println();
				System.out.println("1.-LEER DE LOCAL.");
				System.out.println("2.-LEER DE ADDRESSBOOK SERVER.");
				System.out.println("3.-SALIR.");
				String eleccion=teclado.readLine();
				opcion=Integer.parseInt(eleccion);
				switch(opcion)
				{
					case 1:
							local();
							break;
					case 2:
							servidor();
							break;
					case 3:
							System.out.println("HASTA PRONTO...");
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
		while(opcion!=3);
	}
	public static void local()throws Exception
	{
		System.out.println("Indique la ruta de la agenda:");
		String archivo=teclado.readLine();
		try
		{
			File file=new File(archivo);
			FileInputStream fis=new FileInputStream(file);
			Reader r=new InputStreamReader(fis);
			ab=AddressBook.loadAddressBook(r);
			fis.close();
			String pass=ab.getPassword();
			if(pass!=null)
			{
				System.out.println("Necesitas password:");
				String pas=teclado.readLine();
				if(pass.equals(pas))
				{
					System.out.println("+OK Password correcto...!!");
					System.out.println("+OK Se muestra la agenda...!!");
					mostrarAgenda();
				}
				else
				{
					System.out.println("-ERR Password erroneo...!!");
				}
			}
			else
			{
				System.out.println("+OK Se muestra la agenda...!!");
				mostrarAgenda();
			}
		}
		catch(Exception ex)
		{
			System.out.println("-ERR Agenda no encontrada,prueba de nuevo...");
		}
	}
	public static void servidor()
	{
		try
		{
			opcion=3;
			System.out.println("Indica el nombre del SERVIDOR...");
			String host=teclado.readLine();
			System.out.println("Indica usuario:");
			String user=teclado.readLine();
			hl=new HiloLector(host,user);
			hl.start();
		}
		catch(Exception ex)
		{
			System.out.println("Host desconocido...");
			principio();
		}
	}
	public static void mostrarAgenda()
	{
		Enumeration contactos = ab.elements();
		while (contactos.hasMoreElements())
		{
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			Contact c = (Contact) contactos.nextElement();
			System.out.println("CONTACTO: "+c.getFirstName());
			System.out.println("Apellido: "+c.getLastName());
			System.out.println("Puesto: "+c.getPosition());
			System.out.println("Empresa: "+c.getOrganization());
			System.out.println("Observaciones: "+c.getObservations());
			System.out.println("URL: "+c.getWebAddress());
			System.out.println("Nick: "+c.getNickname());
			System.out.println("Esposa: "+c.getSpouse());
			Date d=c.getBirthday();
			System.out.print("Cumpleaños: ");
			System.out.println(d);
			System.out.println("TELEFONOS:");
			try
			{
				Enumeration telefonos=c.getTelephones().elements();
				while(telefonos.hasMoreElements())
				{
					Address add1=(Address)telefonos.nextElement();
					System.out.println(add1.getPlace()+": "+add1.getValue());
				}
			}
			catch(Exception ex)
			{
				System.out.println("Sin telefonos...");
			}
			System.out.println("DIRECCIONES:");
			try
			{
				Enumeration addresses=c.getAddresses().elements();
				while(addresses.hasMoreElements())
				{
					Address add2=(Address)addresses.nextElement();
					System.out.println(add2.getPlace()+": "+add2.getValue());
				}
			}
			catch(Exception ex)
			{
				System.out.println("Sin direcciones...");
			}
			System.out.println("E-MAILS:");
			try
			{
				Enumeration emails=c.getEmails().elements();
				while(emails.hasMoreElements())
				{
					Address add3=(Address)emails.nextElement();
					System.out.println(add3.getPlace()+": "+add3.getValue());
				}
			}
			catch(Exception ex)
			{
				System.out.println("Sin E-Mails...");
			}
		}
		System.out.println();
		opcion=0;
		principio();	
	}
	public static void setAddressBook(AddressBook address)
	{
		ab=address;
		mostrarAgenda(); 
	}
}