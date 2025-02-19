package utilz;

import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;

import Main.Game;


public class Tool {
	
	public static boolean CanMove(float x , float y , float width , float height , int[][] lvlData) {
		if(!IsBox(x , y, lvlData)) {
			if(!IsBox(x + width, y + height, lvlData)) {
				if(!IsBox(x + width, y, lvlData)) {
					if (!IsBox(x, y + height, lvlData)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private static boolean IsBox(float x , float y , int[][] lvlData) {
		if(x < 0 || x >= Game.Game_width) {
			return true;
		}
		
		if (y < 0 || y >= Game.Game_height) {
			return true;
		}
		
		float xIndex = x/Game.tileSize;
		float yIndex = y/Game.tileSize;
		
		int value = lvlData[(int)yIndex][(int)xIndex];
		
		if (value >=48 || value < 0 || value != 11) {
			return true;
		}
		
		return false;
	}
	
	public static float GetEntityYPos(Rectangle2D.Float hitBox , float airSpeed) {
		int currentTile = (int) (hitBox.y / Game.tileSize);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.tileSize;
			int yOffset = (int) ((Game.originalTileSize * 2) - hitBox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.tileSize;
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox,int[][] lvlData) {
		
		if(!IsBox(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
			if(!IsBox(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)){
				return false;
			}
		}
		return true;
	}
}
