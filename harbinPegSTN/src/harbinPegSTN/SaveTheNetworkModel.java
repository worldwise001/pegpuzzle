package harbinPegSTN;

public class SaveTheNetworkModel extends Model{

	
	public SaveTheNetworkModel(){
		super();
		for(int i=1;i<=6;i++)
			super.setPegAt(Peg.NONE, i);
		setPegAt(Peg.BLACK,7);
		setPegAt(Peg.BLACK,8);
		setPegAt(Peg.BLACK,12);
		setPegAt(Peg.BLACK,13);
		for(int i=14;i<=33;i++)
			super.setPegAt(Peg.BLACK, i);
			
	}
}
