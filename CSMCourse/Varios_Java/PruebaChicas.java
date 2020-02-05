import java.io.*;
public class PruebaChicas
{
    public static int opcion=0;
    public static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in) );
    public static Chicas miembro[]=new Chicas [100];
    public static void main (String[]args)throws Exception
    {
          
          do{
                    System.out.println("\nAGENDA PERSONAL");
                    System.out.println("\nElige una opcion");
                    System.out.println("\n1.-Dar de alta");
                    System.out.println("2.-Dar de baja");
                    System.out.println("3.-Listado");
                    System.out.println("4.-Busqueda");
                    System.out.println("5.-Salir");
                    String linea=teclado.readLine();
                    opcion=Integer.parseInt(linea);
                    switch (opcion){
                    case 1: darAlta();
                                 break;
                    case 2: darBaja();
                                 break;
                    case 3: listado();
                                 break;
                    case 4: busqueda();
                                 break;
                    case 5: System.out.println("ADIOS . . .");
                                 break;
                   default : System.out.println("NO IMPLEMENTADO");
                                 break;
                     }
             }
                  while(opcion<5);
     }
     public static void darAlta()throws Exception
     {
              System.out.println("DAR DE ALTA");
              System.out.println("\nNOMBRE :");
              String nom=teclado.readLine();
              System.out.println("\nEDAD :");
              int n=Integer.parseInt(teclado.readLine());
              System.out.println("\nTELEFONO :");
              int tel=Integer.parseInt(teclado.readLine());
              System.out.println("\nDIRECCION :");
              String dir=teclado.readLine();
              System.out.println("\nGUSTOS :");
              String gus=teclado.readLine();
              for(int i=0;i<miembro.length;i++)
              {
                   if(miembro[i]==null) {
                   miembro[i]=new Chicas(n,nom,tel,dir,gus);
                   break; }
              }
              System.out.println("\nCHICA DADA DE ALTA");
    }
 public static void darBaja()throws Exception
     {
              System.out.println("DAR DE BAJA");
              System.out.println("\nNOMBRE :");
              String nom=teclado.readLine();
              System.out.println("\nEDAD :");
              int n=Integer.parseInt(teclado.readLine());
              for(int i=0;i<miembro.length;i++)
              {
                   if(miembro[i]!=null && nom.equalsIgnoreCase(miembro[i].nombre) && n==miembro[i].edad) {
                   miembro[i]=null;
                   break; }
              }
              System.out.println("\nCHICA DADA DE BAJA");
    }
 public static void busqueda()throws Exception
     {
              System.out.println("BUSQUEDA");
              System.out.println("\nNOMBRE :");
              boolean noencont=true;
              String nom=teclado.readLine();
              for(int i=0;i<miembro.length;i++)
              {
                   if(miembro[i]!=null && nom.equalsIgnoreCase(miembro[i].nombre)) {
                    System.out.println("EDAD : "+miembro[i].edad);
                    System.out.println("TLF : "+miembro[i].tlf);
                    System.out.println("DIRECCION : "+miembro[i].dir);
                    System.out.println("GUSTOS : "+miembro[i].gusto);
                   noencont=false;
                   break; }
              }
              if(noencont)
              System.out.println("\nCHICA NO ENCONTRADA");
    }
public static void listado()
     {
	      boolean noencont=true;
              for(int i=0;i<miembro.length;i++)
              {
                   if(miembro[i]!=null) {
		    System.out.println("\nNOMBRE : "+miembro[i].nombre);
                    System.out.println("EDAD : "+miembro[i].edad);
                    System.out.println("TLF : "+miembro[i].tlf);
                    System.out.println("DIRECCION : "+miembro[i].dir);
                    System.out.println("GUSTOS : "+miembro[i].gusto);
                    noencont=false;     }
              }
              if(noencont)
              System.out.println("\nSIN CHICAS EN LA LISTA");
    }
}