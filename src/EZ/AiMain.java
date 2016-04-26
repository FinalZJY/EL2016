package EZ;

import Spear.Spear;

public class AiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InformationReceive IR=new InformationReceive();		
		GameIniInformation GII=new GameIniInformation(IR.inReceive());
		if(GII.samuraiID==0||GII.samuraiID==3){
			
			for(int i=1;i<GII.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();				
				TurnInformation TIF=new TurnInformation(TIR.turnInformation,TIR.battleField);
				Spear spear=new Spear();

				
			}
			
		}else if(GII.samuraiID==1||GII.samuraiID==4){
			
			for(int i=1;i<GII.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();				
				TurnInformation TIF=new TurnInformation(TIR.turnInformation,TIR.battleField);
				Sword sword=new Sword();
//				sword.
				
			}
			
		}else if(GII.samuraiID==2||GII.samuraiID==5){
			
			for(int i=1;i<GII.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();				
				TurnInformation TIF=new TurnInformation(TIR.turnInformation,TIR.battleField);
				Battleax battleax=new Battleax();
//				battleax.
				
			}
			
		}	

	}

}
