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

package com.ultimatepixel.ultimatepixeldungeon.items.armor.glyphs;

import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.effects.particles.EnergyParticle;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.Armor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.Armor.Glyph;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite.Glowing;
import com.ultimatepixel.ultimatepixeldungeon.utils.Xml;
import com.watabou.utils.Random;

public class Potential extends Glyph {
	
	private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing(Xml.getColor("pure_white"), 0.6f );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.buffedLvl() );
		
		// lvl 0 - 16.7%
		// lvl 1 - 28.6%
		// lvl 2 - 37.5%
		if (defender instanceof Hero && Random.Int( level + 6 ) >= 5 ) {
			int wands = ((Hero) defender).belongings.charge( 1f );
			if (wands > 0) {
				defender.sprite.centerEmitter().burst(EnergyParticle.FACTORY, 10);
			}
		}
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return WHITE;
	}
}
