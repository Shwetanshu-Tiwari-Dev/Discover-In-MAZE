package dev.javagames.tilegame.worlds;

import java.awt.Graphics;

import dev.javagames.tilegame.Handler;
import dev.javagames.tilegame.entities.EntityManager;
import dev.javagames.tilegame.entities.creatures.Player;
import dev.javagames.tilegame.entities.statics.Rock;
import dev.javagames.tilegame.entities.statics.Tree;
import dev.javagames.tilegame.items.ItemManager;
import dev.javagames.tilegame.tiles.Tile;
import dev.javagames.tilegame.utils.Utils;

public class World {
	
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	private EntityManager entityManager;
	
	private ItemManager itemManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public World(Handler handler, String path)
	{
		this.handler = handler;
		entityManager =  new EntityManager(handler, new Player(handler, 0, 0));
		itemManager = new ItemManager(handler);
		entityManager.addEntity(new Tree(handler, 110, 120));
		entityManager.addEntity(new Tree(handler, 220, 310));
		entityManager.addEntity(new Tree(handler, 330, 1600));
		entityManager.addEntity(new Tree(handler, 600, 270));
		entityManager.addEntity(new Tree(handler, 550, 1450));
		entityManager.addEntity(new Tree(handler, 1500, 1400));
		entityManager.addEntity(new Tree(handler, 1400, 1200));
		entityManager.addEntity(new Tree(handler, 120, 1800));
		entityManager.addEntity(new Tree(handler, 900, 1050));
		entityManager.addEntity(new Rock(handler, 150, 240));
		entityManager.addEntity(new Rock(handler, 1800, 130));
		entityManager.addEntity(new Rock(handler, 650, 700));
		entityManager.addEntity(new Rock(handler, 850, 650));
		entityManager.addEntity(new Rock(handler, 1000, 550));
		entityManager.addEntity(new Rock(handler, 1300, 440));
		entityManager.addEntity(new Rock(handler, 500, 330));
		entityManager.addEntity(new Rock(handler, 1250, 230));
		entityManager.addEntity(new Rock(handler, 1800, 1750));
		loadWorld(path);
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}
	
	public void tick()
	{
		itemManager.tick();
		entityManager.tick();
	}
	
	public void render(Graphics g)
	{
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
		
		
		for(int y = yStart; y < yEnd; y++)
		{
			for(int x = xStart; x < xEnd; x++)
			{
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		itemManager.render(g);
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
		{
			return Tile.grassTile;
		}

		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
		{
			return Tile.dirtTile;
		}
		return t;
	}
	
	private void loadWorld(String path)
	{
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for(int y = 0;y < height;y++)
		{
			for(int x = 0;x < width;x++)
			{
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
}
