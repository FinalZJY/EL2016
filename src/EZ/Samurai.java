package EZ;

public class Samurai {
	public int ID;
	public int row;//行
	public int col;//列
	public int rank;//排名
	public int score;//得分
	public int state;//状态
	//
	public Samurai(int rank,int score){
		this.rank=rank;
		this.score=score;
		
	}
	//
	public Samurai(int row,int col,int state){
		this.row=row;
		this.col=col;
		this.state=state;
		
	}

}
