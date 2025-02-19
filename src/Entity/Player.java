package Entity;

import static utilz.Constans.PlayerConstans.GetSpriteAmount;
import static utilz.Constans.PlayerConstans.*;
import static utilz.Constans.Directions.*;
import static utilz.Tool.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.PrinterInfo;

import utilz.LoadSave;

import Main.Game;

public class Player extends Entity {
	
	private BufferedImage[][] animation;
    private int aniTrick, aniIndex , aniSpeed = 10;
    private int player_Acotion =  Idle;
    private int playerDir = -1;
    private  boolean moving = false;
    private boolean attack = false;
    private boolean  left,up, right , down ,jump;
    private float playerSpeed = 2f;
    private int[][] lvlData;
    
    //gravity setting 
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.scale;
    private float jumpSpeed = -2.25f * Game.scale;
    private float fallSpeedAfterCOllision = 0.5f * Game.scale;
    private boolean inAir =  false;
    

	public Player(float x, float y ,int width , int height) {
		this(x,y , width , height, 0, 0);
	}
	
	public Player(float x, float y ,int width , int height ,float marginX, float marginY) {
		super(x,y , width , height, marginX, marginY);
		inithitbox(x,y , (float) width , (float) height, marginX, marginY);
		loadAnimation();
	}
	
	
	public void update() {
		updatePos();
		updateAnimatetionTrick();
        setAnimation();
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[player_Acotion][aniIndex], (int)(hitBox.x - marginX), (int)(hitBox.y - marginY) ,256 , 256 , null);
		drawHitBox(g);
	}
	
	private void updatePos() {
		
		moving = false;
		
		if(jump) {
			jump();
		}
		
		if(!left && !right && !inAir) {
			return;
		}
		
		float xSpeed = 0;
		
		if(left) {
			xSpeed -= playerSpeed;

		}
		
		if(right){
			xSpeed += playerSpeed;
		}
		
		if(!inAir) {
			if(!IsEntityOnFloor(hitBox, lvlData)) {
				inAir = true;
			}
		}
		
		if(inAir) {
			if(CanMove(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
				hitBox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}else {
				hitBox.y = GetEntityYPos(hitBox , airSpeed);
				System.out.println("hitBox Y A:" + hitBox.y);
				if(airSpeed > 0) {
					resetInAir();
				}else {
					airSpeed = fallSpeedAfterCOllision;
				}
				updateXPos(xSpeed);
			}
		}else {
			updateXPos(xSpeed);
		}
		
		moving = true;
		
		
	}
	
	private void jump() {
		if(inAir) {
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	public void updateXPos(float xSpeed) {
		if(CanMove(hitBox.x + xSpeed , hitBox.y, width , height , lvlData)) {
			hitBox.x += xSpeed;
		}
		
	}
	
	public void LoadlvData(int[][] lvlvData){
		this.lvlData = lvlvData;
		if (!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
	}
    
    private void updateAnimatetionTrick() {
		aniTrick++;
		if (aniTrick >= aniSpeed) {
			aniTrick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(player_Acotion)) {
				aniIndex  = 0;
				attack = false;
			}
		}
	}
    
    private void setAnimation() {
    	int startAnimation = player_Acotion;
    	
    	if (moving) {
    		player_Acotion = Walk;
    	}else {
    		player_Acotion = Idle;
    	}
    	
    	if(attack) {
    		player_Acotion = Attack01;
    	}
    	
    	if (startAnimation != player_Acotion) {
    		resetAntiTick();
    	}
    }
    
    private void resetAntiTick() {
    	aniTrick = 0;
    	aniIndex = 0;
    }
	
	private void loadAnimation() {
		
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.Player_ATLAS);

		animation =  new BufferedImage[7][9];
		
		for (int j = 0 ; j < animation.length; j++)
			for (int i = 0; i < animation[j].length ; i++ )
				animation[j][i] = img.getSubimage(i * 100,j * 100, 100, 100);

	}
	
	public void resetDirBoolean() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setAttack(boolean attack) {
		this.attack = attack;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
}
