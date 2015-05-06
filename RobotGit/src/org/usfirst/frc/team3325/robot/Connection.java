package org.usfirst.frc.team3325.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import edu.wpi.first.wpilibj.command.Command;

public class Connection extends Thread // extends Command
{
	public static final int SERVER_PORT = 1180;

	public static Connection instance = new Connection();
	
	private static ServerSocket serverSocket;
	private static HashMap<String, Socket> clients;
	private static HashMap<String, PrintWriter> clientWriters;
	private static HashMap<String, ClientListener> clientReaders;

	//private static Command connectThread = Connection::connectClient;

	//@Override
	private Connection()
	{
		System.out.println("trying to init connection...");
		try
		{
			System.out.println("starting to try");
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("created server SOcket");

			clients = new HashMap<>(2);
			clientWriters = new HashMap<>(2);
			clientReaders = new HashMap<>(2);
			System.out.println("hashes (weed)");

		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("out of try");
	}

	@Override
	public void run()
	{
		System.out.println("accepting connections...");
		while(true)
		{
			try
			{
				System.out.println("awaiting connection...");
				Socket clientSocket = serverSocket.accept();
				System.out.println("attempted connection!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
				String start = inFromClient.readLine();
				String id = start.substring(start.indexOf('[') + 1, start.indexOf(']'));
				System.out.println("MessageID: " + id);
				id = clientSocket.getInetAddress().toString();
				System.out.println("IPID: " + id);
				/*if(clients.containsKey(id))
				{
					clientSocket.close();
				}
				else
				{*/
					clients.put(id, clientSocket);
					clientWriters.put(id, outToClient);
					clientReaders.put(id, new ClientListener(id, inFromClient));

					clientReaders.get(id).run();
				//}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}

	public static void disconnectClient(String clientId)
	{
		clientReaders.get(clientId).stop();
		sendData(clientId, "disconnect");
		clientReaders.remove(clientId);
		clientWriters.remove(clientId);
		try
		{
			clients.get(clientId).close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		clients.remove(clientId);
	}



	public static void sendData(String id, String data)
	{
		clientWriters.get(id).println("[server] " + data);
	}

	public static void sendDataRaw(String id, String rawData)
	{
		clientWriters.get(id).print(rawData);
	}

	public static void broadcast(String data) {
		for (PrintWriter pw : clientWriters.values()) {
			pw.println("[server] " + data);
		}
	}
	
	public static String[] getData(String id) {
		if (!clientReaders.containsKey(id)) {
			return new String[]{};
		}
		return clientReaders.get(id).getMessages();
	}


	private static class ClientListener extends Thread
	{
		private String id;
		private BufferedReader reader;
		private boolean isRunning = false;
		private ArrayList<String> messages;

		public ClientListener(String id, BufferedReader reader)
		{
			this.id = id;
			this.reader = reader;

			messages = new ArrayList<>();
		}

		@Override
		public void run()
		{
			isRunning = true;
			while(isRunning)
			{
				try
				{
					String msg = reader.readLine();
					synchronized (this)
					{
						messages.add(msg);
					}
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}

		/*public void stop()
		{
			this.isRunning = false;
		}*/

		public synchronized String[] getMessages()
		{
			String[] ret = new String[messages.size()];
			messages.toArray(ret);
			messages.clear();
			return ret;
		}
	}

	/*@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		
	}*/
}
