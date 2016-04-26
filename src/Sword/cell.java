package Sword;

import EZ.TurnInformation;
//此类用来保存棋盘中的每个格子的坐标以及值。
public class cell {
	public int row;
	public int col;
	public int value = TurnInformation.battleField[col][row];

}
