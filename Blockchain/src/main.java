import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

public class main {
	/*public static Blockchain getLongest(final Peer x[])
	{
		Blockchain blk =null;
		for(int i=0;i<x.length;i++)
		{
		}
		return blk;
	}*/

	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException, UnknownHostException, SocketException {
		/*
		Blockchain blk = new Blockchain();
		Voter q = new Voter("123");
		//Test public and private keys
		System.out.println("Private and public keys For 123:");
		System.out.println("Private Key: "+q.getSK());
		System.out.println("Public Key: "+q.getPK());
		System.out.println("****************************");
		Voter w = new Voter("456");
		System.out.println("Private and public keys For 456:");
		System.out.println("Private Key: "+w.getSK());
		System.out.println("Public Key: "+w.getPK());
		System.out.println("****************************");
		Vote s = new Vote(789,"x",q);
		System.out.println(s.verifySign());
		System.out.println("****************************");
		Vote d = new Vote(753,"y",w);
		System.out.println(d.verifySign());
		blk.addBlock(d);
		blk.addBlock(s);
		blk.PrintBlockChain();
		*/
		//DatagramSocket ds = new DatagramSocket(3000);
		//DatagramSocket ds2 = new DatagramSocket(200);
		//ds.setSoTimeout(5000);
		//ds.setBroadcast(true);
		Voter v = new Voter("123");
		Voter v2 = new Voter("456");
		Voter v3 = new Voter("789");
		Peer p = new Peer(v, 200);
		Peer p2 = new Peer(v2, 3000);
		Peer p3 = new Peer(v3, 6666);
		System.out.println(Peer.peers.size());
		System.out.println(Peer.getPorts());
		
		/*p.connect();
		p2.connect();
		
		
		p.broadcast("HELLO");

		p2.receive();
		p.receive();*/
		
		
		
		
		
		
		

	}

}
