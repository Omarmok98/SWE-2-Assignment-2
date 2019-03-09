import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class Peer {
	Blockchain blk = new Blockchain();
	DatagramSocket ds = null;
	InetAddress ip = null;
	Voter voter;
	int port = -1;
	public static ArrayList<Peer> peers = new ArrayList<Peer>();

	public Peer(Voter voter, int port) {
		this.voter = voter;
		this.port = port;
		peers.add(this);
	}

	public void connect() {
		try {

			this.ip = InetAddress.getByName("127.0.0.1");
			this.ds = new DatagramSocket(this.port);
			this.ds.setBroadcast(true);
			this.ds.setSoTimeout(5000);
			System.out.println(this.voter.id + " Connected!");

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String str, InetAddress ip, int port) {

		try {
			String s = this.voter.id + " " + str;
			DatagramPacket dp = new DatagramPacket(s.getBytes(), s.length(), ip, port);
			ds.send(dp);
			System.out.println(str + " , Sent By: " + this.voter.id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void broadcastVote(Vote v) throws ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException { //broadcast vote for miners to add to a block

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(v);
			baos.flush();
			oos.flush();
			final byte[] data = baos.toByteArray();
			for (int i = 0; i < Peer.peers.size(); i++) {
				DatagramPacket dp = new DatagramPacket(data, data.length, this.ip, Peer.peers.get(i).getPort());
				ds.send(dp);
			}

			// System.out.println(str + " , Sent By: " + this.voter.id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveVote() throws ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException { //for miners

		try {

			byte[] buf = new byte[5000];
			DatagramPacket dp = new DatagramPacket(buf, 1024);

			ds.receive(dp);
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
			Vote v = (Vote) ois.readObject();
			bais.close();
			ois.close();
			//String vJson = new GsonBuilder().setPrettyPrinting().create().toJson(v);
			System.out.println(v.choice);
			Block b = this.mine(v);
			for(int i=0;i<peers.size();i++)
			{
				peers.get(i).disconnect();
				peers.get(i).connect();
			}
			
			broadcastBlock(b);
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void broadcastBlock(Block b) throws ClassNotFoundException { //for miners 

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(b);
			baos.flush();
			oos.flush();
			
			final byte[] data = baos.toByteArray();
			for (int i = 0; i < Peer.peers.size(); i++) {
				
				DatagramPacket dp = new DatagramPacket(data, data.length, this.ip, Peer.peers.get(i).getPort());
				ds.send(dp);
				//Peer.peers.get(i).receiveBlock();
				
			}

			// System.out.println(str + " , Sent By: " + this.voter.id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveBlock() throws ClassNotFoundException //for everyone in the network
	{
		try {
			

			byte[] buf = new byte[5000];
			DatagramPacket dp = new DatagramPacket(buf, 5000);
			
			ds.receive(dp);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
			
			Block b =  (Block) ois.readObject();
			//String vJson = new GsonBuilder().setPrettyPrinting().create().toJson(v);
			System.out.println(b.hash);
			//blk.blockchain.add(b);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void disconnect() {
		ds.close();
	}

	public void setBlk(Blockchain b) {
		this.blk = b;

	}

	public int getPort() {
		return this.port;
	}

	public static ArrayList<Integer> getPorts() {
		ArrayList<Integer> portsList = new ArrayList<Integer>();
		for (int i = 0; i < peers.size(); i++) {
			portsList.add(peers.get(i).getPort());
		}
		return portsList;

	}

	public Block mine(Vote v)
			throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException {
		Block b = null;
		if (v.verifySign() && blk.chainValid()) {
			
			b = blk.addBlock(v);
			return b;
		} else {
			System.err.println("Not valid");
			return b;
		}

	}
	/*public static Blockchain getLongest(final Peer x[])
	{
		Blockchain blk =null;
		for(int i=0;i<x.length;i++)
		{
			
		}
		return blk;
	}*/

}
