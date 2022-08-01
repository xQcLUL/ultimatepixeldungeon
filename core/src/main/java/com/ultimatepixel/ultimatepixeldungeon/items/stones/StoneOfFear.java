/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
 *
 * Ultimate Pixel Dungeon
 * Copyright (C) 2022
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.ultimatepixel.ultimatepixeldungeon.items.stones;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Actor;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Buff;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Terror;
import com.ultimatepixel.ultimatepixeldungeon.effects.Flare;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.ultimatepixel.ultimatepixeldungeon.tiles.DungeonTilemap;
import com.ultimatepixel.ultimatepixeldungeon.utils.Xml;
import com.watabou.noosa.audio.Sample;

public class StoneOfFear extends Runestone {
	
	{
		image = ItemSpriteSheet.STONE_FEAR;

		color = 0x990022;
	}
	
	@Override
	protected void activate(int cell) {

		Char ch = Actor.findChar( cell );

		if (ch != null){
			Buff.affect( ch, Terror.class, Terror.DURATION ).object = curUser.id();
		}

		new Flare( 5, 16 ).color(Xml.getColor("red"), true ).show(Dungeon.hero.sprite.parent, DungeonTilemap.tileCenterToWorld(cell), 2f );
		Sample.INSTANCE.play( Assets.Sounds.READ );
		
	}
	
}
