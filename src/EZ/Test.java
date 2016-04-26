package EZ;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=1;i<=2;i++){
			TurnInformationReceive TIR=new TurnInformationReceive();
			TIR.tuReceive();
			TurnInformation TI=new TurnInformation(TIR.turnInformation,TIR.battleField);
			System.out.println(TI.turnNum);
		}
	}
}
