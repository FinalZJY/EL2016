package EZ;

import Battleax.BattleaxAi;

public class Battleax {
	String actions="";                    //AiMain在新建一个Battleax对象后可以从actions中取得最终行动
	public Battleax(){
		BattleaxAi AI=new BattleaxAi();
		AI.run();
		this.actions=AI.actions.substring(0,AI.actions.length()-2);
	}
}
