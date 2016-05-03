package EZ;


public class AiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InformationReceive IR=new InformationReceive();
		IR.inReceive();		
		GameIniInformation GII=new GameIniInformation();
		if(GII.samuraiID==0||GII.samuraiID==3){
			for(int i=1;i<GII.totalRounds;i++){
				Spear spear=new Spear();
//				spear.	
			}
			
			
		}else if(GII.samuraiID==1||GII.samuraiID==4){
			
			for(int i=1;i<GII.totalRounds;i++){
				Sword sword=new Sword();
//				sword.
				
			}
			
		}else if(GII.samuraiID==2||GII.samuraiID==5){
			
			for(int i=1;i<GII.totalRounds;i++){
				Battleax battleax=new Battleax();
//				battleax.
				
			}
			
		}	

	}

}
