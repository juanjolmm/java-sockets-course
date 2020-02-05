class primos
{
public static void main(String[]args)
{

/*Un numero que no sea divisible por ningun numero ke este por debajo
es primo*/

for(int x=2;x<16;x++)
	{
	for(int i=2;i<=x;i++)
		{
		if(x==i)
			{
			System.out.println(x+" es primo.");
			break;
			}
		if(x%i!=0)
			{
			continue;
			}
		     else
			{
			System.out.println(x+" no es primo.");
			}
		break;
		}
	}
}
}