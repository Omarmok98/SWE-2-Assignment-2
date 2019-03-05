import java.util.Date;
import java.security.MessageDigest;

public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	
	Block(String data, String previousHash)
	{
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	         
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public String calculateHash()
	{
		String outputHash = applySha256(this.previousHash + Long.toString(this.timeStamp) + data);
		return outputHash;
	}

}
