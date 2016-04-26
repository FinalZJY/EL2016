package EZ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * 游戏初始信息获取，根据提示一行一行读
 */

public class InformationReceive {

	public int[] inReceive(){
		// TODO Auto-generated method stub
		int [] information = new int[30];
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		try {
			
			String tip1=br.readLine();
			if(tip1.equals("# Game Info")){
				String line1=br.readLine();
				String[]a=line1.split(" ");
				for(int i=0;i<6;i++){
					information[i]=Integer.parseInt(a[i]);
				}			
			}
			
			String tip2=br.readLine();
			if(tip2.equals("# Home positions")){
				for(int i=0;i<6;i++){
					String[] a=br.readLine().split(" ");
					information[6+2*i]=Integer.parseInt(a[0]);
					information[7+2*i]=Integer.parseInt(a[1]);
				}			
			}
			
			String tip3=br.readLine();
			if(tip3.equals("# Ranks and scores of samurai")){
				for(int i=0;i<6;i++){
					String[] a=br.readLine().split(" ");
					information[18+2*i]=Integer.parseInt(a[0]);
					information[19+2*i]=Integer.parseInt(a[1]);
				}				
			}
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		GameIniInformation gif=new GameIniInformation(information);
		System.out.println(0);
		return information;
		
		
		
	}

}
