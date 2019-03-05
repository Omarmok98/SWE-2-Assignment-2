import java.util.ArrayList;

public class Blockchain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	
	public static boolean chainValid()
	{
		Block curr;
		Block prev;
		
		for(int i=0 ; i<blockchain.size() ; i++)
		{
			curr = blockchain.get(i);
			prev = blockchain.get(i-1);
			if(!curr.hash.equals(curr.calculateHash()))
			{
				System.out.println("Current Hashes not equal");
				return false;
			}
			if(!prev.hash.equals(curr.previousHash))
			{
				System.out.println("Previous Hashes not equal");
				return false;
				
			}
		}
		return true;
	}

}
