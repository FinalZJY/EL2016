package EZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Initial information of game
 * 储存游戏初始信息：回合总数，队伍ID，武士ID，战场大小，恢复周期，大本营位置，队伍布置
 *
 */
public class GameIniInformation {
	public static int totalRounds;//回合总数
	public static int teamID;//队伍ID
	public static int samuraiID;//武士ID	
	public static int widthOfBf;//战场大小
	public static int heightOfBf;	
	
	public static int recoverRound;//恢复周期
	public static List<Home> home=new ArrayList<>();//大本营位置
	public static List<Samurai> SOT=new ArrayList<>();//队伍布置
	
	public GameIniInformation(){
		
	}
	
	 public GameIniInformation(int[] a){//所有信息均以数组传递
		 totalRounds=a[0];
		 teamID=a[1];
		 samuraiID=a[2];
		 widthOfBf=a[3];
		 heightOfBf=a[4];
		 recoverRound=a[5];
		 Home[] allHome={
				 new Home(a[6],a[7]),
				 new Home(a[8],a[9]),
				 new Home(a[10],a[11]),
				 new Home(a[12],a[13]),
				 new Home(a[14],a[15]),
				 new Home(a[16],a[17]),		 
		 };
		 home.addAll(Arrays.asList(allHome));
		 	
		 Samurai[] allSamurai={
				 new Samurai(a[18],a[19]),
				 new Samurai(a[20],a[21]),
				 new Samurai(a[22],a[23]),
				 new Samurai(a[24],a[25]),
				 new Samurai(a[26],a[27]),
				 new Samurai(a[28],a[29]),
		 };
		 SOT.addAll(Arrays.asList(allSamurai));
		  
		 
		 
	 }
}
