//Shemar Brown-Wright
//20/11/2021
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class InputHandler
{
	DataInputStream dis;
	DataOutputStream dos;
	Socket s;
	

	public void initializeObject(Socket s, DataInputStream dis, DataOutputStream dos){
		this.s = s;
		this.dis = dis;
		this.dos = dos;
	}
//prompts user what role they currently are using
	public void writePosition(String position){
		try{
		dos.writeUTF("You are " + position);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	//reads input choice
	public String readChoice(){
		String in = new String();
		try{
			in = dis.readUTF();	
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return in;
	}
	//output message
	public void writeMessage(String message){
		try{
			dos.writeUTF(message);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
