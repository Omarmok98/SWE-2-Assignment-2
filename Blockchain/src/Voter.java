import java.security.*;
import java.util.Base64;

public class Voter implements java.io.Serializable {
	public String id;
	public  PrivateKey sk;
	public  PublicKey pk;
	private Vote v;
	Voter(String id)
	{
		this.id = id;
		generateKeyPair();
	}

	private void generateKeyPair() {
		
			try {
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA","SUN");
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
				keyGen.initialize(512,random);
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	sk = keyPair.getPrivate();
	        	pk = keyPair.getPublic();
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
					
	}
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	public String getSK() {
		return Base64.getEncoder().encodeToString(sk.getEncoded());
	}
	public String getPK() {
		return Base64.getEncoder().encodeToString(pk.getEncoded());
	}
	public Vote getVote()
	{
		return this.v;
	}
	public void setVote(Vote vote)
	{
		this.v = vote;
	}
}
