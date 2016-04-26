package Sword;

import java.util.ArrayList;

import javafx.scene.control.Cell;

import javax.swing.text.StyledEditorKit.ForegroundAction;





import EZ.Samurai;
import EZ.TurnInformation;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

//对攻击距离分析，当剑无法攻击到时，只有敌方矛有可能攻击到剑，因此逃离方法中只分析矛的情况
//方向即是示例中的指令，1~4表示占领方向，5~8表示移动方向
//个人认为体力限制可通过执行完之后分析action或者在分析方法中实现，以下方法中没有具体的实现
//对于棋盘边界对于移动和占领的限制，只使用了if语句进行部分处理，还没有想出很完美的方法
public class NecessaryAct {
	public static String action;  //行动指令
	public static int strength;   //体力限制
	Samurai Sword,emSpear; //剑，敌方矛
	int intiCol = Sword.col;  //保存回合开始时的位置
	int intiRow = Sword.row;
	
	public NecessaryAct(){ //构造方法，开始时得到剑和敌方矛的信息
		for(Samurai samurai:TurnInformation.nowAllSamurai){
			if(samurai.ID == 1){  //我自行在Samurai里添加的ID，便于区分武士
				Sword = samurai;
			}
			if(samurai.ID == 3){
				emSpear = samurai;
			}
		}
	}
	
	public ArrayList<cell> Occupy(int direction){ //传入方向，返回占领的棋盘数组
		
		
		ArrayList<cell> occupyList = new ArrayList<cell>();
		
			for(int i=1;i<=3;i++){
				if(direction==1 || direction == 3){
					cell C = new cell();
					if(Sword.col-i>=0){
					C.col = Sword.col - i;
					C.row = Sword.row;
					occupyList.add(C);
					}
				}
				else if(direction == 2 || direction == 4){
					cell C = new cell();
					if(Sword.col+i>=0){
					C.col = Sword.col + i;
					C.row = Sword.row;
					occupyList.add(C);
					}
				}
				else if(direction == 1 || direction == 4){
					cell C = new cell();
					if(Sword.row+i>=0){
					C.col = Sword.col;
					C.row = Sword.row + i;
					occupyList.add(C);
					}
				}
				else if(direction == 2 || direction == 3){
					cell C = new cell();
					if(Sword.row-i>=0){
					C.col = Sword.col;
					C.row = Sword.row - i;
					occupyList.add(C);
					}
				}
			}
			
				cell C1 = new cell();
				cell C2 = new cell();
				cell C3 = new cell();
				if(direction == 1){
					
					C1.col = Sword.col-1;
					C1.row = Sword.row+1;
					C2.col = Sword.col-1;
					C2.row = Sword.row+2;
					C3.col = Sword.col-2;
					C3.row = Sword.row+1;
				}
				else if(direction == 2){
					C1.col = Sword.col+1;
					C1.row = Sword.row-1;
					C1.col = Sword.col+2;
					C1.row = Sword.row-1;
					C1.col = Sword.col+1;
					C1.row = Sword.row-2;
				}
				else if(direction == 3){
					C1.col = Sword.col-1;
					C1.row = Sword.row-1;
					C2.col = Sword.col-1;
					C2.row = Sword.row-2;
					C3.col = Sword.col-2;
					C3.row = Sword.row-1;
				}
				else if(direction == 4){
					C1.col = Sword.col+1;
					C1.row = Sword.row+1;
					C2.col = Sword.col+1;
					C2.row = Sword.row+2;
					C3.col = Sword.col+2;
					C3.row = Sword.row+1;
				}
				if(C1.col>=0 && C1.row>=0 && C1.col <=19 && C1.col<=19)
					occupyList.add(C1);
				if(C1.col>=0 && C1.row>=0 && C2.col <=19 && C2.col<=19)
					occupyList.add(C2);
				if(C1.col>=0 && C1.row>=0 && C3.col <=19 && C3.col<=19)
					occupyList.add(C3);
				
			
		
		return occupyList;
		
	}
	public int ValueOfOccupy(ArrayList<cell> cells){ //占领的棋盘中可以加分的数目
		int value = 0;
		for(cell c:cells){
			if(c.value == 8||c.value == 9||c.value == 3 || c.value == 4 || c.value == 5){
			value++;	
			}
		}
		return value;
	}
	public int Move(int direction){   //移动方法
		
		
		switch (direction) {
		case 5:
			
			Sword.col--;
			break;
		case 6:
			Sword.col++;
			break;
		case 7:
			Sword.row--;
			break;
		case 8:
			Sword.row--;

		default:
			break;
		}
		return 2;
	}
	public int Hide(){  //隐身方法
		action = action + "9";
		Sword.state = 1;
		return 1;
	}
	public int Show(){  //现身方法
		action = action + "10";
		Sword.state = 0;
		return 1;
	}
	public int Kill(){  //击杀方法，若能击杀，返回方向，若不能，返回-1
		for(int i=1;i<=4;i++){
			ArrayList<cell> temp = this.Occupy(i);
			for(cell c:temp){
				for(Samurai s:TurnInformation.nowAllSamurai){
					if(c.col == s.col && c.row == s.row){
						return i;
					}
				}
			}
			
		}
		return -1;
	}
	public boolean MustKill(){  //必杀方法，尝试行动一步或者不动的击杀，若能返回true，不能返回false
		if(Kill()!=-1){  //原地能击杀
			Occupy(Kill());
			action = action+Kill();
			return true;
		}
		else{  //行动后击杀
		for(int i=5;i<=8;i++){ 
				Move(i);  //尝试移动
				if(Kill()!=-1){
					Occupy(Kill());
					action = action+Kill();
					action = action + i;
					return true;
				
			}
			Sword.col = intiCol; //恢复初始位置，重新尝试
			Sword.row = intiRow;
		}
		}
		return false;
		
	}
	public boolean MustEscape(){  //判断是否会被敌方矛击杀
		int colDis = Math.abs(Sword.col-emSpear.col);//与敌方矛之间的距离
		int rowDis = Math.abs(Sword.row-emSpear.row);
		if((colDis == 5 && rowDis ==0) || (rowDis == 5 && colDis == 0) || (rowDis == 4 && colDis == 1) || (rowDis == 1 && colDis == 4)){
			return true;
		}
		else {
			return false;
		}
	}
	public void Escape(){  //逃跑方法
		if(MustEscape()){
			int i;
			for(;;){
				i = (int)(4+Math.random()*4);  //随机产生一个方向逃跑，具体逃跑路径和方法可后续设置
				Move(i);
				if(!MustEscape()){
					action = action+i;
					Hide();   //建议逃跑后隐藏
					break;
				}
				Sword.col=intiCol;
				Sword.row=intiRow;
			}
		}
	}
	public void ShouldOccupy(){  //在不需要逃跑和击杀时，发育的方法
		int [][]Va =new int[5][4];  //Va储存不同行动后的收益，共20种
		for(int i=0;i<=4;i++){
			for(int j=0;j<=3;j++){
				if(i==0){  //不移动，直接占领的收益
					Va[0][j]=ValueOfOccupy(Occupy(j+1));
				}
				else{
					Move(i+4);  //移动后占领的收益
					Va[i][j]=ValueOfOccupy(Occupy(j+1));
					Sword.col = intiCol; //恢复初始位置，重新尝试
					Sword.row = intiRow;
				}
			}
		}
		int max =Va[0][0];  //以下为取最大值算法，同时取出数组下标
		int Xmax =0;
		int Ymax =0;
		for(int a=0;a<=4;a++){
			for(int b=0;b<=3;b++){
				if(Va[a][b]>max){
					max=Va[a][b];
					Xmax = a;
					Ymax = b;
				}
			}
		}
		if(Xmax ==0 ){
			action = action + (Ymax+4);
		}
		else {
			action = action + Xmax + (Ymax+4);
		}
		
	}

}
