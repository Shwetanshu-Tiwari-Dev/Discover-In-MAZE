package dev.javagames.tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.javagames.tilegame.display.Display;
import dev.javagames.tilegame.gfx.Assets;
import dev.javagames.tilegame.gfx.GameCamera;
import dev.javagames.tilegame.input.KeyManager;
import dev.javagames.tilegame.input.MouseManager;
import dev.javagames.tilegame.states.GameState;
import dev.javagames.tilegame.states.MenuState;
import dev.javagames.tilegame.states.State;

public class Game implements Runnable{

	private Display display;
	public String title;
	private int width, height;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public State gameState;
	private State menuState;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private GameCamera gameCamera;
	
	private Handler handler;
	
	public Game(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init()
	{
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
	}
	
	
	private void tick()
	{	
		keyManager.tick();
		
		if(State.getState()!=null)
		{
			State.getState().tick();
		}
	}
	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
			
		if(State.getState()!=null)
		{
			State.getState().render(g);
		}
		
		bs.show();
		g.dispose();
	}
	
	public void run()
	{
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			
			if(delta >= 1)
			{
				tick();
				render();
				delta--;
			}
		}
		
		stop();
	}
	
	public KeyManager getKeyManager()
	{
		return keyManager;
	}
	
	public MouseManager getMouseManager()
	{
		return mouseManager;
	}
	
	public GameCamera getGameCamera()
	{
		return gameCamera;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public synchronized void start()
	{
		if(running)
		{
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
		{
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
