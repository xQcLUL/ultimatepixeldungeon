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

import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Actor;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.ultimatepixel.ultimatepixeldungeon.mechanics.Ballistica;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;

public class StoneOfBlink extends Runestone {
	
	{
		image = ItemSpriteSheet.STONE_BLINK;

		color = 0x903B90;

		speedSkill = 1.2f;
		icon_speed = true;
		accSkill = 1.2f;
		icon_acc = true;

		extraMaxDmgWeapon = -1;
		extraMinDmgWeapon = 1;
	}
	
	private static Ballistica throwPath;
	
	@Override
	public int throwPos(Hero user, int dst) {
		throwPath = new Ballistica( user.pos, dst, Ballistica.PROJECTILE );
		return throwPath.collisionPos;
	}
	
	@Override
	protected void onThrow(int cell) {
		if (Actor.findChar(cell) != null && throwPath.dist >= 1){
			cell = throwPath.path.get(throwPath.dist-1);
		}
		throwPath = null;
		super.onThrow(cell);
	}
	
	@Override
	protected void activate(int cell) {
		if (!ScrollOfTeleportation.teleportToLocation(curUser, cell)){
			Dungeon.level.drop(this, cell).sprite.drop();
		}
	}
}
