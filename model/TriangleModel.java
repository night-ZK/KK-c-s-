package model;

import java.awt.Point;
import java.util.Random;

public class TriangleModel {
	
	final static int RIGHT_MOVE = 0;
	final static int LOWER_RIGHT_MOVE = 1;
	final static int UPPER_RIGHT_MOVE = 2;
	final static int LEFT_MOVE = 3;
	final static int LOWER_LEFT_MOVE = 4;
	final static int UPPER_LEFT_MOVE = 5;
	final static int LOWER_MOVE = 6;
	final static int UPPER_MOVE = 7;
	
	private Point xp;
	private Point yp;
	private Point zp;
	private Random random;
	
	private int width;
	private int height;
	public TriangleModel(int width, int height) {
		this.random = new Random();
		
		this.width = width;
		this.height = height;
	}
	
	public Point randomPoint(int width, int height) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		return new Point(x, y);
	}
	
	public Point randomPoint() {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		return new Point(x, y);
	}
	
	public void pointMove() {
		pointMove(xp);
		pointMove(yp);
		pointMove(zp);
	}
	
	int oldR = -1;
	long oldTiem = -1l;
	public void pointMove(Point p){
//		p.x = p.x + -1;
//		p.y = p.y + 1;
		
//		int r;
		if (oldTiem == -1l || System.currentTimeMillis() - oldTiem >= 500) {
			oldR = random.nextInt(99) + 1;
			oldTiem = System.currentTimeMillis();
		}
		
//		int r = random.nextInt(100);
		int[] direction = locationForPanel(p);
		if(oldR < 20) {
			pointMove(p, direction[0]);
		} else if (oldR < 35) {
			pointMove(p, direction[1]);
		}else if (oldR < 40) {
			pointMove(p, direction[2]);
		}else if (oldR < 52) {
			pointMove(p, direction[3]);
		}else if (oldR < 64) {
			pointMove(p, direction[4]);
		}else if (oldR < 76) {
			pointMove(p, direction[5]);
		}else if (oldR < 88) {
			pointMove(p, direction[6]);
		}else if (oldR < 100) {
			pointMove(p, direction[7]);
		}
		
	}
	
	public void pointMoveR(Point p){
		p.x = p.x + -1;
		p.y = p.y + 1;
		
//		pointMove(p, direction[7]);
	}
	
	public boolean isRemove() {
		return !(inPanel(xp) || inPanel(yp) || inPanel(zp));
	}

	public boolean inPanel(Point p) {
		return p.x < this.width && p.y < this.height;
	}
	
	public int[] locationForPanel(Point p) {
		int[] direction = new int[8];
		if (p.x < width/3 && p.y < height/3) {
			direction[0] = LOWER_RIGHT_MOVE;
			direction[1] = RIGHT_MOVE;
			direction[2] = LOWER_MOVE;
			direction[3] = LEFT_MOVE;
			direction[4] = LOWER_LEFT_MOVE;
			direction[5] = UPPER_LEFT_MOVE;
			direction[6] = UPPER_MOVE;
			direction[7] = UPPER_RIGHT_MOVE;
		}else if(p.x < width*2/3 && p.y < height/3){
			direction[0] = LOWER_RIGHT_MOVE;//40%
			direction[1] = RIGHT_MOVE;//20%
			direction[2] = LOWER_MOVE;//15%
			direction[3] = LEFT_MOVE;//5%
			direction[4] = LOWER_LEFT_MOVE;//5%
			direction[5] = UPPER_LEFT_MOVE;//5%
			direction[6] = UPPER_MOVE;//5%
			direction[7] = UPPER_RIGHT_MOVE;//5%
		}else if(p.x < width && p.y < height/3){
			direction[0] = LOWER_LEFT_MOVE;
			direction[1] = LOWER_MOVE;
			direction[2] = LEFT_MOVE;
			direction[3] = RIGHT_MOVE;
			direction[4] = LOWER_RIGHT_MOVE;
			direction[5] = UPPER_RIGHT_MOVE;
			direction[6] = UPPER_MOVE;
			direction[7] = UPPER_LEFT_MOVE;
		}else if(p.x < width/3 && p.y < height*2/3) {
			direction[0] = LOWER_RIGHT_MOVE;//40%
			direction[1] = RIGHT_MOVE;//20%
			direction[2] = LOWER_MOVE;//15%
			direction[3] = LEFT_MOVE;//5%
			direction[4] = LOWER_LEFT_MOVE;//5%
			direction[5] = UPPER_LEFT_MOVE;//5%
			direction[6] = UPPER_MOVE;//5%
			direction[7] = UPPER_RIGHT_MOVE;//5%
		}else if(p.x < width*2/3 && p.y < height*2/3) {
			direction[0] = LOWER_RIGHT_MOVE;//40%
			direction[1] = LOWER_MOVE;//20%
			direction[2] = RIGHT_MOVE;//15%
			direction[3] = LEFT_MOVE;//5%
			direction[4] = LOWER_LEFT_MOVE;//5%
			direction[5] = UPPER_LEFT_MOVE;//5%
			direction[6] = UPPER_MOVE;//5%
			direction[7] = UPPER_RIGHT_MOVE;//5%
		}else if(p.x < width && p.y < height*2/3){
			direction[0] = LOWER_LEFT_MOVE;
			direction[1] = LOWER_MOVE;
			direction[2] = LEFT_MOVE;
			direction[3] = RIGHT_MOVE;
			direction[4] = LOWER_RIGHT_MOVE;
			direction[5] = UPPER_RIGHT_MOVE;
			direction[6] = UPPER_MOVE;
			direction[7] = UPPER_LEFT_MOVE;
		}else if(p.x < width/3 && p.y < height){
			direction[0] = UPPER_RIGHT_MOVE;
			direction[1] = UPPER_MOVE;
			direction[2] = RIGHT_MOVE;
			direction[3] = LEFT_MOVE;
			direction[4] = LOWER_LEFT_MOVE;
			direction[5] = UPPER_LEFT_MOVE;
			direction[6] = LOWER_MOVE;
			direction[7] = LOWER_RIGHT_MOVE;
		}else if(p.x < width*2/3 && p.y < height){
			direction[0] = UPPER_RIGHT_MOVE;
			direction[1] = UPPER_MOVE;
			direction[2] = RIGHT_MOVE;
			direction[3] = LEFT_MOVE;
			direction[4] = LOWER_LEFT_MOVE;
			direction[5] = UPPER_LEFT_MOVE;
			direction[6] = LOWER_MOVE;
			direction[7] = LOWER_RIGHT_MOVE;
		}else {
			direction[0] = UPPER_LEFT_MOVE;
			direction[1] = UPPER_MOVE;
			direction[2] = LEFT_MOVE;
			direction[3] = LOWER_LEFT_MOVE;
			direction[4] = RIGHT_MOVE;
			direction[5] = UPPER_RIGHT_MOVE;
			direction[6] = LOWER_RIGHT_MOVE;
			direction[7] = LOWER_MOVE;
		}
		
		return direction;
	}
	
	public void pointMove(Point p, int direction){
		
		switch (direction) {
		case RIGHT_MOVE:			
			p.x = p.x + 1;
			break;
		case LOWER_RIGHT_MOVE:			
			p.x = p.x + 1;
			p.y = p.y + 1;
			break;
		case UPPER_RIGHT_MOVE:			
			p.x = p.x + 1;
			p.y = p.y - 1;
			break;
			
		case LEFT_MOVE:			
			p.x = p.x - 1;
			break;
		case LOWER_LEFT_MOVE:			
			p.x = p.x - 1;
			p.y = p.y + 1;
			break;
		case UPPER_LEFT_MOVE:			
			p.x = p.x - 1;
			p.y = p.y - 1;
			break;
			
		case LOWER_MOVE:			
			p.y = p.y - 1;
			break;
		case UPPER_MOVE:			
			p.y = p.y + 1;
			break;
			
		default:
			break;
		}
	}
	
}
