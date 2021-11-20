//Shemar Brown-Wright
//20/11/2021
package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
	public static void main(String[] args) throws IOException
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			
			
	
			// establish the connection with server port 7621
			Socket s = new Socket("localhost", 7621);
	
			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			
			//introduction prompt to introduce to game
			System.out.println(dis.readUTF());
			

			//username prompt received from server
			System.out.println(dis.readUTF());
			dos.writeUTF(scanner.nextLine());


			//password prompt received from server
			System.out.println(dis.readUTF());
			dos.writeUTF(scanner.nextLine());

			//check if connection is established
			if (dis.readUTF().equals("Established"))
			{
				System.out.println(dis.readUTF());
				/*loop to handle information exchange*/
				for (int i = 0; i < 5; i++) 
				{
					System.out.println("");
					System.out.println(dis.readUTF());
	
					System.out.println(dis.readUTF());
	
					dos.writeUTF(scanner.nextLine());
				}
			
			//win or loss message
			System.out.println(dis.readUTF());
			//thank you closing message
			System.out.println(dis.readUTF());

			//closing streams
			scanner.close();
			dis.close();
			dos.close();
			}

		else{
			scanner.close();
			dis.close();
			dos.close();
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}