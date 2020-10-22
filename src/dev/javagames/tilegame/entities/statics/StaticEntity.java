package dev.javagames.tilegame.entities.statics;

import dev.javagames.tilegame.Handler;
import dev.javagames.tilegame.entities.Entity;

public abstract class StaticEntity extends Entity{

	public StaticEntity(Handler handler, float x, float y, int width, int height )
	{
		super(handler, x, y, width, height);
	}
}
