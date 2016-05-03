package EZ;

import java.util.ArrayList;
import EZ.TurnInformationReceive;
/* 3个武士AI的父类，包括了一些通用的方法
 * 放在EZ包里
 * Ver1.1:  补上了不能在非自己领地隐身的考虑
 * Ver1.2:  补上了不能移动到敌人所在格子的考虑，修改使用TurnInformation里的信息
 * by 俊毅
 */

public class AI {
	public  AI() {
		
	}
	
	protected static int samuraiID;//武士ID                                //1.2版本修改的部分
	protected Samurai me=TurnInformation.nowAllSamurai.get(samuraiID);
	protected String actions="";//最终的武士行为
	protected int leftPoint=7;  //剩余行动力
	
	public Samurai getMe() {
		return me;
	}	
	public int getMyRow() {
		return me.row;
	}	
	public int getMyCol() {
		return me.col;
	}	
	public int getState() {
		return me.state;
	}
	public int getLeftPoint() {
		return leftPoint;
	}
	
	public void run(){                               //根据不同的子类自己覆盖，AI开始计算的主入口
		
	}
	
	public boolean occupy(String direction){       //在行动中添加占领，成功就返回true，否则返回false
		if(leftPoint<4){
			return false;
		}
		if(direction.equals("southward")){
			actions=actions+"1 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else if(direction.equals("eastward")){
			actions=actions+"2 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else if(direction.equals("northward")){
			actions=actions+"3 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else if(direction.equals("westward")){
			actions=actions+"4 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean move(int direction) {          //1.2版本增加
		if(direction==5){
			return move("southward");
		}
		else if (direction==6) {
			return move("eastward");
		}
		else if (direction==7) {
			return move("northward");
		}
		else if (direction==8) {
			return move("westward");
		}
		return false;
	}
	
	public boolean move(String direction){         //在行动中添加移动，成功就返回true，否则返回false
		if(leftPoint<2){
			return false;
		}
		if((direction.equals("southward"))&&(me.row!=14)&&(!existEnemy(me.row+1,me.col))){//1.2版修改的部分
			if((TurnInformation.battleField[me.row+1][me.col]<=2)&&(me.state==1)){        //1.2版修改的部分
				return false;
			}
			actions=actions+"5 ";
			me.row++;
			leftPoint=leftPoint-2;
			return true;
		}
		else if((direction.equals("eastward"))&&(me.col!=14)&&(!existEnemy(me.row,me.col+1))){//1.2版修改的部分
			if((TurnInformation.battleField[me.row][me.col+1]<=2)&&(me.state==1)){            //1.2版修改的部分
				return false;
			}
			actions=actions+"6 ";
			me.col++;
			leftPoint=leftPoint-2;
			return true;
		}
		else if((direction.equals("northward"))&&(me.row!=0)&&(!existEnemy(me.row-1,me.col))){//1.2版修改的部分
			if((TurnInformation.battleField[me.row-1][me.col]<=2)&&(me.state==1)){            //1.2版修改的部分
				return false;
			}
			actions=actions+"7 ";
			me.row--;
			leftPoint=leftPoint-2;
			return true;
		}
		else if((direction.equals("westward"))&&(me.col!=0)&&(!existEnemy(me.row,me.col-1))){//1.2版修改的部分
			if((TurnInformation.battleField[me.row][me.col-1]<=2)&&(me.state==1)){           //1.2版修改的部分
				return false;
			}
			actions=actions+"8 ";
			me.col--;
			leftPoint=leftPoint-2;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean hide(){                         //在行动中添加隐身，成功就返回true，否则返回false
		if(leftPoint<1){
			return false;
		}
		if(TurnInformation.battleField[me.row][me.col]<=2 && !existEnemy(me.row,me.col)){  //1.2.1版修改的部分
			actions=actions+" 9";
			leftPoint=leftPoint-1;
			return true;
		}
		return false;
	}
	
	public boolean show(){                         //在行动中添加现身，成功就返回true，否则返回false
		if(leftPoint<1){
			return false;
		}
		actions=actions+" 10";
		leftPoint=leftPoint-1;
		return true;
	}
	
	public boolean canAttactEnemy(){    //根据不同的子类自己覆盖
		
		return false;
	}
	
	public boolean existEnemy(){         //己方武士视野中有敌人就返回true，否则false，恢复中的敌人（turnInformation[i]==-1）当成没看见
		for(int i=13;i<=19;i=i+3){
			if(TurnInformationReceive.turnInformation[i]==0){
				return true;
			}
		}
		return false;
	}
	public boolean existEnemy (int row,int col) {             //1.2版本添加，(row,col)中有敌人就返回true，否则false
		for(Samurai enemy:TurnInformation.nowAllSamurai){
			if(enemy.ID>=3 && enemy.row==row && enemy.col==col && (enemy.state==0||enemy.state==-1)){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<int[]> enemyPosition(){ //将看见的敌人的位置放进positions里传回，int[0]是row，int[1]是col
		ArrayList<int[]> positions=new ArrayList<int[]>();
		for(int i=13;i<=19;i=i+3){
			if(TurnInformationReceive.turnInformation[i]==0){ //恢复中的敌人（turnInformation[i]==-1）当成没看见
				int[] position ={TurnInformationReceive.turnInformation[i-2]};
				positions.add(position);
			}
		}
		return positions;
	}
	
	public int distantFromMe(int row,int col){   //返回(row,col)距离我这个武士的曼哈顿距离
		return Math.abs(me.row-row)+Math.abs(me.col-col);
	}
	
	public int[] minusMyCoordinate(int row,int col) { //目标的坐标减我这个武士的坐标，返回记录了坐标差的数组
		int[] result={row-me.row,col-me.col};
		return result;
	}
	
	public boolean moveTo(int row,int col){        //在行动中添加移动到(row,col)的步骤，成功就返回true，否则返回false
		if(leftPoint<distantFromMe(row, col)*2){   
			return false;
		}
		
		if((me.row==row)||(me.col==col)){          //1.1版修改的部分
			while(me.row>row){
				move("northward");
			}
			while(me.row<row){
				move("southward");
			}
			while(me.col>col){
				move("westward");
			}
			while(me.col<col){
				move("eastward");
			}
		}
		else if(Math.abs(me.row-row)==1 && Math.abs(me.col-col)==1){
			String aDirection="";
			String bDirection="";
			if(me.row==row+1){
				aDirection="northward";
			}
			else if(me.row==row-1){
				aDirection="southward";
			}
			if(me.col==col+1){
				bDirection="westward";
			}
			else if(me.row==row-1){
				bDirection="eastward";
			}
			
			if(move(aDirection)){
				if(move(bDirection)){
					return true;
				}
				else {
					cancelLastAction();
				}
			}
			if (move(bDirection)) {
				if(move(aDirection)){
					return true;
				}
				else {
					cancelLastAction();
				}
			}
		}
		
		return false;
	}
	
	public boolean cancelLastAction() {              //1.1版修改的部分  //删掉上一个步骤，成功返回true，否则false
		if(actions==""){
			return false;
		}
		String[] newActions=actions.split(" ");
		actions="";
		for(String action:newActions){
			if(!action.equals(newActions[newActions.length-1])){
				actions=actions+action+" ";
			}
		}
		return true;
	}
}
