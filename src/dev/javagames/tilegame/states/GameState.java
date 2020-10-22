package dev.javagames.tilegame.states;

import java.awt.Graphics;

import dev.javagames.tilegame.Handler;
import dev.javagames.tilegame.worlds.World;


public class GameState extends State{

	private World world;
	
	public GameState(Handler handler)
	{
		super(handler);
		world = new World(handler, "res/world/world1.txt");
		handler.setWorld(world);
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
	}
	


}
