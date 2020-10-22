package dev.javagames.tilegame.tiles;

import dev.javagames.tilegame.gfx.Assets;

public class RockTile extends Tile{
	
	public RockTile(int id)
	{
		super(Assets.stone, id);
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
}
