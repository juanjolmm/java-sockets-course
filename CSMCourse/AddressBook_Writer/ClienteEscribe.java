import java.io.*;
import csm.addressbook.*;
import java.util.*;
import java.text.*;
public class ClienteEscribe
{
	private static BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
	private static int opcion;
	private static AddressBook ab;
	private static File cual;
	private static Address tlf[]=new Address[15];
	private static Address dir[]=new Address[15];
	private static Address mail[]=new Address[15];
	private static Addresses tlfs;
	private static Addresses dirs;
	private static Addresses mails;
	private static int contTlf=0;
	private static int contDir=0;
	private static int contMail=0;
	private static FileWriter fw;
	private static HiloEscribe he;
	private static CharArrayWriter caw;
	private static boolean modServidor=false;
	private static String userServidor;
	private static String host;
	private static String passServidor;
	private static HiloEscribe2 he2;
	private static String mandaAgenda;
	private static Contact[]contactos;
	public static void main(String []args)
	{
		principio();
	}
	public static void principio()
	{
		do
		{
			try
			{
				modServidor=false;
				System.out.println("***ADDRESSBOOK WRITER***");
				System.out.println();
				System.out.println("1.-ESCRIBIR EN LOCAL.");
				System.out.println("2.-ESCRIBIR EN ADDRESSBOOK SERVER.");
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
	public static void local()
	{
		try
		{
			System.out.println("Indique la ruta de la agenda:");
			String agenda=teclado.readLine();
			cual=new File(agenda);
			FileInputStream fis=new FileInputStream(cual);
			ab=AddressBook.loadAddressBook(new InputStreamReader(fis));
			fis.close();
			String pass=ab.getPassword();
			if(pass!=null)
			{
				System.out.println("Necesitas password:");
				String password=teclado.readLine();
				if(password.equals(pass))
				{
					System.out.println("+OK Password correcto...!!");
					modificarAgenda();
				}
				else
				{
					System.out.println("-ERR Password erroneo...!!");
				}
			}
			else
			{
				modificarAgenda();
			}
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("-ERR Agenda no encontrada...");
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public static void modificarAgenda()
	{
		try
		{
			System.out.print("Modificar nombre de usuario?(s/n)");
			String newUser=teclado.readLine();
			if(newUser.equalsIgnoreCase("s"))
			{
				System.out.print("Indica el nombre:");
				String nuevo=teclado.readLine();
				ab.setUser(nuevo);
				System.out.println("+OK Usuario modificado!!");
				if(modServidor)
				{
					caw=new CharArrayWriter();
					salva(caw);
					mandaAgenda=new String(caw.toCharArray());
					he2=new HiloEscribe2(host,userServidor,passServidor,mandaAgenda);
					he2.start();
				}
				else
				{
					fw=new FileWriter(cual);
					salva(fw);
				}
			}
			System.out.print("Modificar password?(s/n)");
			String user=teclado.readLine();
			if(user.equalsIgnoreCase("s"))
			{
				System.out.print("Indica el nuevo password:");
				String nuevoP=teclado.readLine();
				ab.setPassword(nuevoP);
				System.out.println("+OK Password modificado!!");
				if(modServidor)
				{
					caw=new CharArrayWriter();
					salva(caw);
					mandaAgenda=new String(caw.toCharArray());
					he2=new HiloEscribe2(host,userServidor,passServidor,mandaAgenda);
					he2.start();
				}
				else
				{
					fw=new FileWriter(cual);
					salva(fw);
				}
			}
			System.out.println("Se muestran codigos y contactos:");
			Enumeration grupo=ab.elements();
			int cuantos=0;
			while(grupo.hasMoreElements())
			{
				Contact c=(Contact)grupo.nextElement();
				System.out.println("Nombre:"+c.getFirstName()+" Codigo:"+c.getCode());
				cuantos++;
			}
			System.out.println("Indica codigo de contacto, -2 para nuevo contacto o -1 para salir.");
			int keHacer=Integer.parseInt(teclado.readLine());
			Enumeration grupo2=ab.elements();;
			if(cuantos>0)
			{
				contactos=new Contact[cuantos];
			}
			for(int i=0;grupo2.hasMoreElements()||i<1;i++)
			{
				if(cuantos>0)
				{
					contactos[i]=(Contact)grupo2.nextElement();
					int code=contactos[i].getCode();
					if(keHacer==code)
					{
						System.out.println("Modificar o eliminar?(m/e)");
						String mod=teclado.readLine();
						if(mod.equalsIgnoreCase("m"))
						{
							escribe(contactos[i]);
							break;
						}
						else if(mod.equalsIgnoreCase("e"))
						{
							ab.removeElement(contactos[i]);
							System.out.println("+OK Contacto eliminado!!");
							if(modServidor)
							{
								caw=new CharArrayWriter();
								salva(caw);
								mandaAgenda=new String(caw.toCharArray());
								he2=new HiloEscribe2(host,userServidor,passServidor,mandaAgenda);
								he2.start();
								principio();
							}
							else
							{
								fw=new FileWriter(cual);
								salva(fw);
								break;
							}
						}
						else
						{
							System.out.println("-ERR opcion invalida");
						}
					}
					else if(keHacer==-2)
					{
						System.out.println("Se creara nuevo contacto...");
						System.out.println("Indica un codigo para el contacto:");
						int codigo=Integer.parseInt(teclado.readLine());
						if(puede(codigo))
						{
							Contact nuevo=new Contact(codigo);
							ab.addElement(nuevo);
							escribe(nuevo);
							break;
						}
						else
						{
							System.out.println("-ERR Codigo ya utilizado por un contacto");
							break;
						}
					}
					else if (keHacer==-1)
					{
						principio();
					}
				}
				else if(keHacer==-2)
				{
					System.out.println("Se creara nuevo contacto...");
					System.out.println("Indica un codigo para el contacto:");
					int codigo=Integer.parseInt(teclado.readLine());
					if(puede(codigo))
					{
						Contact nuevo=new Contact(codigo);
						ab.addElement(nuevo);
						escribe(nuevo);
						break;
					}
					else
					{
						System.out.println("-ERR Codigo ya utilizado por un contacto");
						break;
					}
				}
				else if (keHacer==-1)
				{
					principio();
				}
				else
				{
					System.out.println("-ERR opcion invalida");
					principio();
					i++;
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("-ERR Opcion incorrecta...");
			principio();
		}
	}
	public static void escribe(Contact contact)
	{
		try
		{
			System.out.print("Indique nombre:");
			String nombre=teclado.readLine();
			if(nombre.length()>0)
			contact.setFirstName(nombre);
			System.out.print("Indique apellido:");
			String apellido=teclado.readLine();
			if(apellido.length()>0)
			contact.setLastName(apellido);
			System.out.print("Indique trabajo:");
			String position=teclado.readLine();
			if(position.length()>0)
			contact.setPosition(position);
			System.out.print("Indique empresa:");
			String organization=teclado.readLine();
			if(organization.length()>0)
			contact.setOrganization(organization);
			System.out.print("Indique observaciones:");
			String observaciones=teclado.readLine();
			if(observaciones.length()>0)
			contact.setObservations(observaciones);
			System.out.print("Indique pagina web:");
			String web=teclado.readLine();
			if(web.length()>0)
			contact.setWebAddress(web);
			System.out.print("Indique nick:");
			String nick=teclado.readLine();
			if(nick.length()>0)
			contact.setNickname(nick);
			System.out.print("Indique nombre de la esposa:");
			String spouse=teclado.readLine();
			if(spouse.length()>0)
			contact.setSpouse(spouse);
			String cual1;
			do
			{
				System.out.print("Modificar telefonos (s/n)");
				cual1=teclado.readLine();
				if(cual1.equalsIgnoreCase("s"))
				{
					System.out.println("Nueva o eliminar?(n/e)");
					String op=teclado.readLine();
					if(op.equalsIgnoreCase("e"))
					{
						tlfs=contact.getTelephones();
						if(tlfs!=null)
						{
							Enumeration enu=tlfs.elements();
							Address c[]=new Address[15];
							int contador=0;
							while(enu.hasMoreElements())
							{
								c[contador]=(Address)enu.nextElement();
								System.out.println(contador+"."+c[contador].toString());
								contador++;
							}
							if(contador>0)
							{
								System.out.println("Indica el numero a eliminar");
								int cual=Integer.parseInt(teclado.readLine());
								tlfs.removeElement(c[cual]);
							}
							else
							{
								System.out.println("-ERR Sin telefonos!!");
							}
						}
						else
						{
							System.out.println("-ERR Sin telefonos!!");
						}
					}
					else if(op.equalsIgnoreCase("n"))
					{
						System.out.print("Indique lugar:");
						String lugar=teclado.readLine();
						System.out.print("Indique telefono:");
						String telefono=teclado.readLine();
						tlf[contTlf]=new Address(lugar,telefono);
						tlfs=new Addresses();
						tlfs.addElement(tlf[contTlf]);
						contact.setTelephones(tlfs);
						contTlf++;
					}
				}
			}
			while(!cual1.equalsIgnoreCase("n"));
			String cual2;
			do
			{
				System.out.print("Modificar direcciones (s/n)");
				cual2=teclado.readLine();
				if(cual2.equalsIgnoreCase("s"))
				{
					System.out.println("Nueva o eliminar?(n/e)");
					String op=teclado.readLine();
					if(op.equalsIgnoreCase("e"))
					{
						dirs=contact.getAddresses();
						if(dirs!=null)
						{
							Enumeration enu=dirs.elements();
							Address c[]=new Address[15];
							int contador=0;
							while(enu.hasMoreElements())
							{
								c[contador]=(Address)enu.nextElement();
								System.out.println(contador+"."+c[contador].toString());
								contador++;
							}
							if (contador>0)
							{
								System.out.println("Indica el numero a eliminar");
								int cual=Integer.parseInt(teclado.readLine());
								dirs.removeElement(c[cual]);
							}
							else
							{
								System.out.println("-ERR Sin direcciones!!");
							}
						}
						else
						{
							System.out.println("-ERR Sin direcciones!!");
						}
					}
					else if(op.equalsIgnoreCase("n"))
					{
						System.out.print("Indique lugar:");
						String lugar=teclado.readLine();
						System.out.print("Indique direccion:");
						String direccion=teclado.readLine();
						dir[contDir]=new Address(lugar,direccion);
						dirs=new Addresses();
						dirs.addElement(dir[contDir]);
						contact.setAddresses(dirs);
						contDir++;
					}
				}
			}
			while(!cual2.equalsIgnoreCase("n"));
			String cual3;
			do
			{
				System.out.print("Modificar E-Mails (s/n)");
				cual3=teclado.readLine();
				if(cual3.equalsIgnoreCase("s"))
				{
					System.out.println("Nueva o eliminar?(n/e)");
					String op=teclado.readLine();
					if(op.equalsIgnoreCase("e"))
					{
						mails=contact.getEmails();
						if(mails!=null)
						{
							Enumeration enu=mails.elements();
							Address c[]=new Address[15];
							int contador=0;
							while(enu.hasMoreElements())
							{
								c[contador]=(Address)enu.nextElement();
								System.out.println(contador+"."+c[contador].toString());
								contador++;
							}
							if(contador>0)
							{
								System.out.println("Indica el numero a eliminar");
								int cual=Integer.parseInt(teclado.readLine());
								mails.removeElement(c[cual]);
							}
							else
							{
								System.out.println("-ERR Sin direcciones!!");
							}
						}
						else
						{
							System.out.println("-ERR Sin direcciones!!");
						}
					}
					else if(op.equalsIgnoreCase("n"))
					{
						System.out.print("Indique cuenta:");
						String cuenta=teclado.readLine();
						System.out.print("Indique direccion:");
						String email=teclado.readLine();
						mail[contMail]=new Address(cuenta,email);
						mails=new Addresses();
						mails.addElement(mail[contMail]);
						contact.setEmails(mails);
						contMail++;
					}
				}
			}
			while(!cual3.equalsIgnoreCase("n"));
			System.out.print("Indique fecha de nacimiento (dd/mm/aa):");
			String cumpleaños=teclado.readLine();
			if(cumpleaños.length()>0)
			{
				Date d=DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(cumpleaños);
				contact.setBirthday(d);
			}
			if(modServidor)
			{
				caw=new CharArrayWriter();
				salva(caw);
				mandaAgenda=new String(caw.toCharArray());
				he2=new HiloEscribe2(host,userServidor,passServidor,mandaAgenda);
				he2.start();
				System.out.println("+OK AGENDA MODIFICADA!!");
				principio();
			}
			else
			{
				fw=new FileWriter(cual);
				salva(fw);
				System.out.println("+OK AGENDA MODIFICADA!!");
				principio();
			}
		}
		catch(ParseException ex)
		{
			System.out.println("Error en la fecha:"+ex.toString());
			principio();
		}
		catch(Exception ex)
		{
			System.out.println("-ERR error en la modificacion!!");
			System.out.println("-ERR agenda no modificada!!");
		}
	}
	public static boolean puede(int code)
	{
		Enumeration grupo=ab.elements();
		while(grupo.hasMoreElements())
		{
			Contact c=(Contact)grupo.nextElement();
			int codigo=c.getCode();
			if(code==codigo)
			{
				return false;
			}
		}
		return true;
	}
	public static void servidor()
	{
		try
		{
			opcion=3;
			System.out.println("Indica el nombre del SERVIDOR...");
			host=teclado.readLine();
			System.out.println("Indica usuario:");
			userServidor=teclado.readLine();
			he=new HiloEscribe(host,userServidor);
			he.start();
		}
		catch(Exception ex)
		{
			System.out.println("Host desconocido...");
			principio();
		}
	}
	public static void salva(Writer w)throws Exception
	{
		ab.save(w);
		w.close();
	}
	public static void setAddressBook(AddressBook address,String usuar,String pass)
	{
		ab=address;
		userServidor=usuar;
		passServidor=pass;
		modServidor=true;
		modificarAgenda();
	}	
}