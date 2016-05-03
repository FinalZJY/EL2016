package EZ;

import Spear.SpearAi;

public class Spear {
	public Spear(){
		SpearAi spear=new SpearAi();//spear
        if(spear.recover!=0){//Spear先判断自己是否死亡，再去判断战场状态
				System.out.println("0");
		}
        else {
			spear.analyseEnemy();
			spear.analyseEnemyCourt();
			System.out.println(spear.order);//输出指令到控制台，manager自己读取命令
		}
	}
//	TurnInformationReceive TIR=new TurnInformationReceive();
//	TIR.tuReceive();
//	TurnInformation TI=new TurnInformation(TIR.turnInformation,TIR.battleField);
	
}
