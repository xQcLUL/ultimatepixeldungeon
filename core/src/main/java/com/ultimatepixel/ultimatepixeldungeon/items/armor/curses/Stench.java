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

package com.ultimatepixel.ultimatepixeldungeon.items.armor.curses;

import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.blobs.Blob;
import com.ultimatepixel.ultimatepixeldungeon.actors.blobs.ToxicGas;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.Armor;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.utils.Xml;
import com.watabou.utils.Random;

public class Stench extends Armor.Glyph {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( Xml.getColor("pure_black") );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		if ( Random.Int( 8 ) == 0) {

			GameScene.add( Blob.seed( defender.pos, 250, ToxicGas.class ) );

		}

		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
	}

	@Override
	public boolean curse() {
		return true;
	}
}
