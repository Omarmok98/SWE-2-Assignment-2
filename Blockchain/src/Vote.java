import java.io.UnsupportedEncodingException;
import java.security.*;
public class Vote implements java.io.Serializable{
	
	public int voteID;
	public Voter v;
	public String choice;
	public byte[] dig_sign;
	
	Vote(int voteID,String choice,Voter v) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException
	{
		this.v = v;
		this.voteID = voteID;
		this.choice = choice;
		this.dig_sign=createSign();
	}

	private byte[] createSign() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
		
		Signature signature = Signature.getInstance("SHA256WithDSA");
		SecureRandom secureRandom = new SecureRandom();
		signature.initSign(v.sk, secureRandom);	
		byte[] data = choice.getBytes("UTF-8");
		signature.update(data);
		byte[] digitalSignature = signature.sign();
		return digitalSignature;
	}
	
	public boolean verifySign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		
		Signature signature = Signature.getInstance("SHA256WithDSA");
		signature.initVerify(v.pk);
		byte[] data = choice.getBytes("UTF-8");
		signature.update(data);
		boolean verified = signature.verify(dig_sign);
		return verified;
		
	}
	
}
