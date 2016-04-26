package Battleax;

import java.util.ArrayList;
import java.util.Arrays;
import EZ.AI;
import EZ.TurnInformation;
/*斧头武士AI
 * v1.0
 * by 俊毅
 */
public class BattleaxAi extends AI {
	public  BattleaxAi() {
		samuraiID=2;
	}
	
	public void run(){                     //Battleax的AI开始计算
		if(canAttactEnemy()){   //如果能杀人，就杀
			if(me.state==1){  //如果隐身了，就先现形
				show();
			}
			for(int[] position:enemyPosition()){  //找到enemyPosition中第一个能杀的杀掉
				if(moveThenAttact(position[0], position[1])){
					hide();
					break;
				}
			}	
		}			
		
		else if(mustEscape()){
			escape();
		}
		
		else{                    //其他情况采用评分算法
			SimulateActions SA=new SimulateActions();
			SA.tryAllActions();
			int[] i=SA.maxScore();
			for(int j:i){
				if(j!=0){
					actions=actions+j+" ";
				}
			}
		}
	}

	public boolean canAttactEnemy(){                          //如果视野中有能攻击到的敌人，则返回true，否则false
		for(int[] position:enemyPosition()){
			if(distantFromMe(position[0], position[1])<=2){
				return true;
			}
		}
		return false;
	}
	
	public boolean attact(int row,int col) {                  //攻击(row,col)，成功则返回true，否则false
		if(minusMyCoordinate(row, col)[0]==1){
			return occupy("southward");
		}
		else if(minusMyCoordinate(row, col)[0]==-1){
			return occupy("northward");
		}
		else if(minusMyCoordinate(row, col)[1]==1){
			return occupy("eastward");
		}
		else if(minusMyCoordinate(row, col)[1]==-1){
			return occupy("westward");
		}
		else{
			return false;
		}
	}
	
	public boolean moveThenAttact(int row,int col) {      //移动一步，再攻击，攻击完成就返回true，无法攻击就返回fasle。
		if(distantFromMe(row, col)>3){
			return false;
		}
		if(leftPoint>=6){
			for(int i=Math.max(row-1, 0);i<=Math.min(row+1, 14);i++){  //看看敌人的周围8个格子中能移动到哪个
				for(int j=Math.max(col-1, 0);j<=Math.min(col+1, 14);j++){
					if((i==row)&&(j==col)){                     //不移动到敌人那块地
						continue;
					}
					if(distantFromMe(i, j)<=1){
						moveTo(i, j);                         //移动到这
						attact(row, col);
						return true;
					}
					
				}
			}
		}
		else if(leftPoint>=4){
			return attact(row, col);
		}
		
		return false;
	}
	
	public boolean mustEscape() {    //如果下一步会被杀，就返回true，否则false
		int[] fromEnemySpear=minusMyCoordinate(TurnInformation.nowAllSamurai.get(3).row,TurnInformation.nowAllSamurai.get(3).col);
		int[] fromEnemySword=minusMyCoordinate(TurnInformation.nowAllSamurai.get(3).row,TurnInformation.nowAllSamurai.get(4).col);
		    //逃离矛的攻击
			if(fromEnemySpear[0]==0){
				if((fromEnemySpear[1]>=3 && fromEnemySpear[1]<=5) || (fromEnemySpear[1]>=-5 && fromEnemySpear[1]<=-3)){
					return true;
				}
			}
			if(fromEnemySpear[0]==-1 || fromEnemySpear[0]==1){
				if((fromEnemySpear[1]>=3 && fromEnemySpear[1]<=4) || (fromEnemySpear[1]>=-4 && fromEnemySpear[1]<=-3)){
					return true;
				}
			}
			if((fromEnemySpear[0]>=3 && fromEnemySpear[0]<=4) || (fromEnemySpear[0]>=-4 && fromEnemySpear[1]<=-3)){
				if(fromEnemySpear[1]>=-1 || fromEnemySpear[1]<=1){
					return true;
				}
			}
			if(fromEnemySpear[0]==5 || fromEnemySpear[0]==-5){
				if(fromEnemySpear[1]==0){
					return true;
				}
			}			
             //逃离剑的攻击
			if(fromEnemySword[0]>=-1 && fromEnemySword[0]<=1){
				if(fromEnemySword[1]==-3 || fromEnemySword[1]<=-4 || fromEnemySword[1]<=3 || fromEnemySword[1]<=4){
					return true;
				}
			}
			if(fromEnemySword[0]==-3 || fromEnemySword[0]<=-4 || fromEnemySword[0]<=3 || fromEnemySword[0]<=4){
				if(fromEnemySword[1]>=-1 && fromEnemySword[1]<=1){
					return true;
				}
			}
		
		return false;
	}
	
	private void escape() {               //逃跑的方法
		int i = (int)(Math.random()*4);  //随机产生一个方向逃跑，具体逃跑路径和方法可后续设置;
		ArrayList<String> directions=new ArrayList<String>();
		directions.addAll(Arrays.asList("southward","eastward","northward","westward"));
		while(directions.size()!=0){
			i = (int)(Math.random()*directions.size());
			move(directions.get(i));
			move(directions.get(i));
			move(directions.get(i));
			if(!mustEscape()){
				hide();                  //建议逃跑后隐藏
				break;
			}
			cancelLastAction();
			cancelLastAction();
			cancelLastAction();
			directions.remove(i);
		}
		
	}
}
