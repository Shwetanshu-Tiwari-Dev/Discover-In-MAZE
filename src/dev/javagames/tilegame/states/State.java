package dev.javagames.tilegame.states;

import java.awt.Graphics;

import dev.javagames.tilegame.Handler;

public abstract class State {
	
	private static State currentState = null;
	
	//GameStateManager
	public static void setState(State state)
	{
		currentState = state;
	}
	
	public static State getState()
	{
		return currentState;
	}
	
	protected Handler handler;
	
	public State(Handler handler)
	{
		this.handler = handler;
	}
	
	public abstract void tick();

	public abstract void render(Graphics g);

}
