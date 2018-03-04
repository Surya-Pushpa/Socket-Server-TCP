// Java implementation for multithreaded chat client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2 
{
	final static int ServerPort = 1234;

	public static void main(String args[]) throws UnknownHostException, IOException 
	{
		Scanner scn = new Scanner(System.in);
		
		// getting localhost ip
		InetAddress ip = InetAddress.getByName("localhost");
		
		// establish the connection
		Socket s = new Socket(ip, ServerPort);
		
		// obtaining input and out streams
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());

		// sendMessage thread
		Thread sendMessage = new Thread(new Runnable() 
		{
			@Override
			public void run() {
				while (true) {

					// read the message to deliver.
					String msgr = scn.nextLine();
					String msgar[] = msgr.split(":");
					String msg = msgar[2];
					//String type = msg.substring()


					try {
						// write on the output stream

						char[] chars = msg.toCharArray();
						StringBuffer hex = new StringBuffer();
						for(int i=0;i<chars.length;i++){
							hex.append(Integer.toHexString((int)chars[i]));
						}
						String msg2 =  hex.toString();


						dos.writeUTF(msg2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		// readMessage thread
		Thread readMessage = new Thread(new Runnable() 
		{
			@Override
			public void run() {

				while (true) {
					try {
						// read the message sent to this client
						String msg = dis.readUTF();
						System.out.println(msg);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		});

		sendMessage.start();
		readMessage.start();

	}
}
