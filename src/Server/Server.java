//Shemar Brown-Wright
//20/11/2021
package Server;

import java.io.*;
import java.text.*;
import java.time.LocalTime;
import java.util.*;
import java.net.*;
import java.util.Random;

import Client.InputHandler;

// Server class
public class Server
{
	public static void main(String[] args) throws IOException
	{
		//score counters declaration
		int user1_score = 0;
		int user2_score = 0;
		//boolean flags to show if users are connected or not
		boolean connected_user1 = false;
		boolean connected_user2 = false;

		// Initialize objects of class inputHandler		
		InputHandler user1 = new InputHandler();
		InputHandler user2 = new InputHandler();

		// this is used to randomly assign positions to users at the beginning of the round
		String[] positions = new String[]{ "dealer", "spotter"};
		Random rand=new Random(); 
		//randomizing number based on array length
		int randomNumber = rand.nextInt(positions.length);
		String user1_position = positions[randomNumber];
		String user2_position = "dealer";
		if(user1_position.equals(user2_position))
		{
			user2_position = "spotter";
		}
		
		//using true or false to decipher whether user is dealer or not
		
//		boolean user1IsDealer=false;
//		boolean user2IsDealer=false;
//		
//		if(randomNumber % 2 == 0) {
//			user1IsDealer = true;
//		}else
//		{
//			user2IsDealer = true;
//		}
//		
//		if(user1IsDealer == user2IsDealer)
//		{
//			user2IsDealer = false;
//		}
//		System.out.print(user2IsDealer);
//		System.out.print(user1IsDealer);
		// initialize users score with zero
		
	

		// initialize variable for storing users choice
		String user1_choice = "1";
		String user2_choice = "2";
		// server is listening on port 7621
		ServerSocket ss = new ServerSocket(7621);
		


		/*running infinite loop for getting
		client request until both users are connected*/
		while (!connected_user1 || !connected_user2)
		{
			Socket s = null;
			//declaration for credentials
			String username;
			String password;
			try
			{
				// socket object to receive incoming client requests
				s = ss.accept();
				
				System.out.println("Request recieved for connection from : " + s);
				
				// obtaining input and out streams
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				dos.writeUTF("This is the game to Find the Queen. You will either be a dealer or spotter depending on your luck.");

				//ask client to write username and receive it
				dos.writeUTF("UserName");
				username = dis.readUTF();

				//ask client to write password and receive it
				dos.writeUTF("Password ");
				password = dis.readUTF();

				
				if(username.equals("dannyboi") && password.equals("dre@margh_shelled"))
				{
					
					if(!connected_user1)
					{
						System.out.println("DannyBoi connected");
						
						user1.initializeObject(s,dis,dos);
						
						dos.writeUTF("Established");
						
						connected_user1 = true;
					}

					else
					{
						dos.writeUTF("Dannyboi is already connected.");
					}

				}
				else if(username.equals("matty7") && password.equals("win&win99"))
				{
					if(!connected_user2)
					{
						System.out.println("Matty7 connected");
						user2.initializeObject(s,dis,dos);
						dos.writeUTF("Established");
						connected_user2 = true;
					}

					else
					{
						dos.writeUTF("Matty7? Why are you trying to log in again?");
					}
				}
				// error prompt from server due to incorrect credentials 
				else
				{
					dos.writeUTF("Wrong credentials");
					s.close();
				}
				
			}
			catch (Exception e){
				s.close();
				e.printStackTrace();
			}
		}

		user1.writeMessage("The Game has begun. Enjoy Find the Queen.");
		user2.writeMessage("The Game has begun. Enjoy Find the Queen.");

		// check if both the users are connected to server
		if(connected_user1 && connected_user2)
		{
			// loop 
			for (int i = 0; i < 5; i++) 
			{
				//check which user is currently "dealer" / "spotter"
				
				user1.writePosition(user1_position);
				user2.writePosition(user2_position);

				//if statement to allow the user that is the dealer to choose position first
				if(user1_position.equals("dealer"))
				{
				user1.writeMessage("Enter Number between 1 and 3");
				user1_choice = user1.readChoice();
				//paused input until the subsequent spotter is ready
				user2.writeMessage("Enter Number between 1 and 3");
				user2_choice = user2.readChoice();
				}
				
				else
				{
				user2.writeMessage("Enter Number between 1 and 3");
				user2_choice = user2.readChoice();
				user1.writeMessage("Enter Number between 1 and 3");
				user1_choice = user1.readChoice();
				}

				
					//scoring scheme based on current position (dealer/spotter)
				if(user1_choice.equals(user2_choice))
				{
					if(user1_position.equals("spotter"))
					{
						user1_score = user1_score+1;
					}

					else
					{
						user2_score = user2_score+1;
					}
				}

				else{
					if(user1_position.equals("dealer"))
					{
						user1_score = user1_score+1;
					}

					else
					{
						user2_score = user2_score+1;
					}
				}

				//swap roles
				String temp_position = user1_position;
				user1_position = user2_position;
				user2_position = temp_position;

			}

				//concluding statements for winner and loser
			if(user1_score>user2_score)
			{
				user1.writeMessage("You Won this time, congrats of Finding the Queen!");
				user2.writeMessage("Better Luck Next Time, you lost.");
			}
			else
			{
				user2.writeMessage("You Won this time, congrats of Finding the Queen!");
				user1.writeMessage("Better Luck Next Time, you lost.");
			}

			user1.writeMessage("Thanks for playing, find the queen will be closing now. Don't be a stranger");
			user2.writeMessage("Thanks for playing, find the queen will be closing now. Don't be a stranger");

		}

	}
}
//--SBW--
