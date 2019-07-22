package model;

import java.awt.Point;
import java.util.Random;

public class PointModel {
	final static int RIGHT_MOVE = 0;
	final static int LOWER_RIGHT_MOVE = 1;
	final static int UPPER_RIGHT_MOVE = 2;
	final static int LEFT_MOVE = 3;
	final static int LOWER_LEFT_MOVE = 4;
	final static int UPPER_LEFT_MOVE = 5;
	final static int LOWER_MOVE = 6;
	final static int UPPER_MOVE = 7;
	
	private int oldR;
	private long oldTiem;
	private int oldDi;
	
	private Random random;
	private Point point;
	
	private Point focusPoint;
	
	private int width;
	private int height;
	public PointModel(int width, int height) {
		this.width = width;
		this.height = height;
		random = new Random();
		oldR = -1;
		oldTiem = -1l;
		oldDi = -1;
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		point = new Point(x, y);
	}
	
	public Point getPoint() {
		return point;
	}
	
	public Point getFocusPoint() {
		return focusPoint;
	}

	public void setFocusPoint(Point focusPoint) {
		this.focusPoint = focusPoint;
	}

	public void pointMove(){
		
		if (oldTiem == -1l 
				|| System.currentTimeMillis() - oldTiem >= 600) {
			oldR = random.nextInt(99) + 1;
			oldTiem = System.currentTimeMillis();
			if(oldDi == -1) oldDi = getOldDi();
			nextDirection(random.nextInt(3));
		}
		
		
		
		pointMove(point, oldDi);
		//TODO
		
		
//		int[] direction = locationForPanel(point);
//		if(oldR < 20) {
//			pointMove(point, direction[0]);
//		} else if (oldR < 35) {
//			pointMove(point, direction[1]);
//		}else if (oldR < 40) {
//			pointMove(point, direction[2]);
//		}else if (oldR < 52) {
//			pointMove(point, direction[3]);
//		}else if (oldR < 64) {
//			pointMove(point, direction[4]);
//		}else if (oldR < 76) {
//			pointMove(point, direction[5]);
//		}else if (oldR < 88) {
//			pointMove(point, direction[6]);
//		}else if (oldR < 100) {
//			pointMove(point, direction[7]);
//		}
		
	}
	
	private void nextDirection(int direction) {
		switch (oldDi) {
		
		case RIGHT_MOVE:
			oldDi = direction == 0 ? UPPER_RIGHT_MOVE : direction==1 ? LOWER_RIGHT_MOVE : RIGHT_MOVE;
			break;
		case LOWER_RIGHT_MOVE:
			oldDi = direction == 0 ? RIGHT_MOVE : direction==1 ? LOWER_MOVE : LOWER_RIGHT_MOVE;
			break;
		case UPPER_RIGHT_MOVE:
			oldDi = direction == 0 ? RIGHT_MOVE : direction==1 ? UPPER_MOVE : UPPER_RIGHT_MOVE;
			break;
			
		case LEFT_MOVE:
			oldDi = direction == 0 ? UPPER_LEFT_MOVE : direction==1 ? LOWER_LEFT_MOVE : LEFT_MOVE;
			break;
			
		case UPPER_LEFT_MOVE:
			oldDi = direction == 0 ? LEFT_MOVE : direction==1 ? UPPER_MOVE : UPPER_LEFT_MOVE;
			break;
			
		case LOWER_LEFT_MOVE:
			oldDi = direction == 0 ? LEFT_MOVE : direction==1 ? LOWER_MOVE : LOWER_LEFT_MOVE;
			break;
			
		case LOWER_MOVE:
			oldDi = direction == 0 ? LOWER_RIGHT_MOVE : direction==1 ? LOWER_LEFT_MOVE : LOWER_MOVE;
			break;
			
		case UPPER_MOVE:
			oldDi = direction == 0 ? UPPER_RIGHT_MOVE : direction==1 ? UPPER_LEFT_MOVE : UPPER_MOVE;
			break;
		default:
			break;
		}
	}

	private int getOldDi() {
		if(oldR < 15) {
			oldDi = 0;
		} else if (oldR < 28) {
			oldDi = 1;
		}else if (oldR < 40) {
			oldDi = 2;
		}else if (oldR < 52) {
			oldDi = 3;
		}else if (oldR < 64) {
			oldDi = 4;
		}else if (oldR < 76) {
			oldDi = 5;
		}else if (oldR < 88) {
			oldDi = 6;
		}else if (oldR < 100) {
			oldDi = 7;
		}
		return oldDi;
	}

	public void pointMoveR(Point p){
		p.x = p.x + -1;
		p.y = p.y + 1;
	}
	
	public boolean inPanel(Point p) {
		return p.x < this.width && p.y < this.height;
	}
	
	public boolean inPanel() {
		return point.x < this.width && point.y < this.height;
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
			p.x += 1;
			break;
		case LOWER_RIGHT_MOVE:			
			p.x += 1;
			p.y += 1;
			break;
		case UPPER_RIGHT_MOVE:			
			p.x += 1;
			p.y -= 1;
			break;
			
		case LEFT_MOVE:			
			p.x -= 1;
			break;
		case LOWER_LEFT_MOVE:			
			p.x -= 1;
			p.y += 1;
			break;
		case UPPER_LEFT_MOVE:			
			p.x -= 1;
			p.y += 1;
			break;
			
		case LOWER_MOVE:			
			p.y += 1;
			break;
		case UPPER_MOVE:			
			p.y -= 1;
			break;
			
		default:
			break;
		}
	}

}
