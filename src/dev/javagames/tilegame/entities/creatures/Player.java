package dev.javagames.tilegame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.javagames.tilegame.Handler;
import dev.javagames.tilegame.entities.Entity;
import dev.javagames.tilegame.gfx.Animation;
import dev.javagames.tilegame.gfx.Assets;
import dev.javagames.tilegame.inventory.Inventory;

public class Player extends Creature{

	private Animation animDown, animUp, animLeft, animRight;
	private long lastAttackTimer, attackCoolDown = 800, attackTimer = attackCoolDown;
	
	private Inventory inventory;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		
		bounds.x = 22;
		bounds.y = 44;
		bounds.width = 19;
		bounds.height = 19;
		
		animDown = new Animation(500, Assets.player_down);
		animUp = new Animation(500, Assets.player_up);
		animLeft = new Animation(500, Assets.player_left);
		animRight = new Animation(500, Assets.player_right);
		
		inventory = new Inventory(handler);
	}


	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
		
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		checkAttacks();
		
		inventory.tick();
	}
	
	private void checkAttacks()
	{
		attackTimer += System.currentTimeMillis() -lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCoolDown)
		{
			return;
		}
		
		if(inventory.isActive())
		{
			return;
		}
		
		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		

		if(handler.getKeyManager().up)
		{
			if(handler.getKeyManager().kick)
			{
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y - arSize;
			}
		}
		else if(handler.getKeyManager().down)
		{
			if(handler.getKeyManager().kick)
			{
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			}
		}
		else if(handler.getKeyManager().left)
		{
			if(handler.getKeyManager().kick)
			{
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}
		}
		else if(handler.getKeyManager().right)
		{
			if(handler.getKeyManager().kick)
			{
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}
		}
		else
		{
			return ;
		}
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities())
		{
			if(e.equals(this))
			{
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar))
			{
				e.hurt(1);
				return;
			}
		}
	}
	
	@Override
	public void die()
	{
		System.out.println("You Lose");
	}
	
	private void getInput()
	{
		if(inventory.isActive())
		{
			return;
		}
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up)
		{
			yMove = -speed;
		}
		if(handler.getKeyManager().down)
		{
			yMove = speed;
		}
		if(handler.getKeyManager().left)
		{
			xMove = -speed;
		}
		if(handler.getKeyManager().right)
		{
			xMove = speed;
		}
	} 
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	public void postRender(Graphics g)
	{
		inventory.render(g);
	}

	private BufferedImage getCurrentAnimationFrame()
	{
		if(xMove > 0)
		{
			return animRight.getCurrentFrame();
		}
		
		else if(xMove < 0)
		{
			return animLeft.getCurrentFrame();
		}
		
		else if(yMove > 0)
		{
			return animDown.getCurrentFrame();
		}
		
		else if(yMove < 0)
		{
			return animUp.getCurrentFrame();
		}
		
		else
		{
			return Assets.player;
		}
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
