package Battleax;
import java.util.ArrayList;
import java.util.Arrays;
/*斧头武士的action模拟系统
* v1.0
* by 俊毅
* 
* 7点体力
* 0、1、2、3对应左、右、上、下占领，耗费4点体力
* 4、5、6、7对应左、右、上、下移动，耗费2点体力
* 8、9对应隐身，现形，耗费1点体力
* 
* 将所有可能模拟一遍，让GradingActions打分，分数存在steps中，调用maxScore方法可返回最高分数的步骤
*/

public class SimulateActions {
	ArrayList<StepAndScore> steps=new ArrayList<>();
	public SimulateActions(){
		
	}
	
	public void tryAllActions() {
		GradingActions GA=new GradingActions();
		int[] action={0,0,0,0};
		for(int i=1;i<=4;i++){                                  //首先执行占领的情况
			action[0]=i;
			steps.add(new StepAndScore(action,GA.getScore(action)));				
			for(int j=5;j<=8;j++){                              //首先执行占领+移动的情况
				action[1]=j;
				steps.add(new StepAndScore(action,GA.getScore(action)));
                                                                //执行占领+移动+隐形的情况
				action[2]=9;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				action[2]=0;
			}
			action[1]=0;
                                                                //执行占领+隐形的情况
			action[1]=9;
			steps.add(new StepAndScore(action,GA.getScore(action)));
			for(int j=5;j<=9;j++){ 
				action[2]=j;
				steps.add(new StepAndScore(action,GA.getScore(action)));
			}
			action[2]=0;
			action[1]=0;
			action[0]=0;
		}
		
		for(int i=5;i<=8;i++){                                  //首先执行移动的情况
			action[0]=i;
			steps.add(new StepAndScore(action,GA.getScore(action)));
			for(int a=5;a<=8;a++){                              //
				action[1]=a;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				for(int b=5;b<=8;b++){
					action[2]=b;
					steps.add(new StepAndScore(action,GA.getScore(action)));
					action[3]=9;
					steps.add(new StepAndScore(action,GA.getScore(action)));
					action[3]=0;
					action[2]=0;
				}
				action[2]=9;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				action[2]=0;
				
				action[1]=0;
			}
				
			for(int j=1;j<=4;j++){                              //首先执行移动+占领的情况
				action[1]=j;
				steps.add(new StepAndScore(action,GA.getScore(action)));
                                                                //执行移动+占领+隐形的情况
				action[2]=9;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				action[2]=0;
				
				action[1]=0;
			}
			
			for(int j=9;j<=10;j++){                              //先执行移动+显（隐）形的情况
				action[1]=j;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				for(int a=1;a<=4;a++){                                //执行移动+显形+占领的情况
					action[2]=a;
					steps.add(new StepAndScore(action,GA.getScore(action)));
					action[2]=0;
				}
				
				action[1]=0;
			}
			
            action[0]=0;
		}
		
		for(int i=9;i<=9;i++){                                     //首先执行隐形的情况
			action[0]=i;
			steps.add(new StepAndScore(action,GA.getScore(action)));
			for(int a=5;a<=8;a++){                              //
				action[1]=a;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				for(int b=5;b<=8;b++){
					action[2]=b;
					steps.add(new StepAndScore(action,GA.getScore(action)));
					for(int c=5;c<=8;c++){
						action[3]=c;
						steps.add(new StepAndScore(action,GA.getScore(action)));
						action[3]=0;
					}
					action[2]=0;
				}
				
				action[1]=0;
			}
			action[0]=0;
		}
		
		for(int i=10;i<=10;i++){                                     //首先执行显形的情况
			action[0]=i;
			steps.add(new StepAndScore(action,GA.getScore(action)));
			for(int a=5;a<=8;a++){                              //
				action[1]=a;
				steps.add(new StepAndScore(action,GA.getScore(action)));
				for(int j=1;j<=4;j++){
					action[2]=j;
					steps.add(new StepAndScore(action,GA.getScore(action)));
					action[2]=10;
				}
				for(int b=5;b<=8;b++){
					action[2]=b;
					steps.add(new StepAndScore(action,GA.getScore(action)));
					for(int c=5;c<=8;c++){
						action[3]=c;
						steps.add(new StepAndScore(action,GA.getScore(action)));
						action[3]=0;
					}
					action[2]=0;
				}
				
				action[1]=0;
			}	
			action[0]=0;
		}
		
	}

	public int[] maxScore() {                //将最高分步骤以int数组返回
		StepAndScore maxStep=steps.get(0);
		for(StepAndScore step:steps){
			if(step.score>maxStep.score){
				maxStep=step;
			}
			
		}
		return maxStep.step;
	}
}

