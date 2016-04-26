package Battleax;
import EZ.Samurai;
import EZ.TurnInformation;
/*斧头武士的评分系统
 * v1.0
 * by 俊毅
 */
//注意：分数为0表示这个action不成立
public class GradingActions{
	
	private int score=0;
	private static final int samuraiID=2;            //斧头武士的评分系统
	private int kill=0;
	
	public int getScore(int[] actions) {
		int myRow=TurnInformation.nowAllSamurai.get(samuraiID).row;
		int myCol=TurnInformation.nowAllSamurai.get(samuraiID).col;
		int myState=TurnInformation.nowAllSamurai.get(samuraiID).state;
		int battleField[][]=TurnInformation.battleField;
		int turnNum=TurnInformation.turnNum;
		int myRecoverRound=TurnInformation.myRecoverRound;
		
		for(int action:actions){
			if(action==1){       //南占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){      //棋盘范围外的无法占领
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow-1)&&(j==myCol)){
							continue;
						}
						battleField[i][j]=samuraiID;
						isKill(i,j);                                         //看看有没有敌人在这一格被剁死
					}
				}
			}

			if(action==2){       //东占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow)&&(j==myCol-1)){
							continue;
						}
						battleField[i][j]=samuraiID;
						isKill(i,j);
					}
				}
			}
			
			if(action==3){      //北占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow+1)&&(j==myCol)){
							continue;
						}
						battleField[i][j]=samuraiID;
						isKill(i,j);
					}
				}
			}
			
			if(action==4){    //西占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow)&&(j==myCol+1)){
							continue;
						}
						battleField[i][j]=samuraiID;
						isKill(i,j);
					}
				}
			}
			
			if(action==5){   //南移动
				if(myRow==14 || existEnemy(myRow+1, myCol)){    //不能移动到棋盘外，或者敌人所在的格子
					return 0;
				}
				myRow++;
				if(myState==1&&battleField[myRow][myCol]>2){   //不能在隐身时移动到非己方的格子
					return 0;
				}
			}
			
			if(action==6){   //东移动
				if(myCol==0 || existEnemy(myRow, myCol-1)){
					return 0;
				}
				myCol--;
				if(myState==1&&battleField[myRow][myCol]>2){
					return 0;
				}
			}
			
			if(action==7){  //北移动
				if(myRow==0 || existEnemy(myRow-1, myCol)){
					return 0;
				}
				myRow--;
				if(myState==1&&battleField[myRow][myCol]>2){
					return 0;
				}
			}
			
			if(action==8){   //西移动
				if(myCol==14 || existEnemy(myRow, myCol+1)){
					return 0;
				}
				myCol++;
				if(myState==1&&battleField[myRow][myCol]>2){
					return 0;
				}
			}
			
			if(action==9){   //隐身
				if(battleField[myRow][myCol]>2){   //不能在非己方的格子隐身
					return 0;
				}
				myState=1;
			}
			
			if(action==10){  //现身
				myState=0;
			}
			
		}
		
		score=score+kill*10000;                            //暂定杀人加10000分
		for(int[] i:battleField){
			for(int j:i){
				if((j==0)||(j==1)||(j==2)){
					score=score+500;                      //暂定每有一块地加500分
				}
			}
		}
		if(myState==1){
			score=score+100;                             //暂定回合结束时隐身加100分
		}
		return score;
	}
	
	public boolean isKill(int i,int j) {                 //若(i,j)有敌人且能被杀死，则杀敌数+1且返回true，否则false
		for(Samurai enemy:TurnInformation.nowAllSamurai){
			if(enemy.ID>=3 && enemy.row==i && enemy.col==j && enemy.state==0){ //恢复中的敌人（enemy.state==-1）当成没看见
				kill++;
				return true;
			}
		}
		return false;
	}
	
	public boolean existEnemy (int row,int col) {       //若(i,j)有敌人，则返回true，否则false
		for(Samurai enemy:TurnInformation.nowAllSamurai){
			if(enemy.ID>=3 && enemy.row==row && enemy.col==col && (enemy.state==0||enemy.state==-1)){
				return true;
			}
		}
		return false;
	}

}
