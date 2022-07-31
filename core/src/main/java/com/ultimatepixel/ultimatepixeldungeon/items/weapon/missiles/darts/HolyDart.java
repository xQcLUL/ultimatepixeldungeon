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

package com.ultimatepixel.ultimatepixeldungeon.items.weapon.missiles.darts;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Bless;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Buff;
import com.ultimatepixel.ultimatepixeldungeon.effects.particles.ShadowParticle;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class HolyDart extends TippedDart {

	{
		image = ItemSpriteSheet.HOLY_DART;
	}
	
	@Override
	public int proc(Char attacker, Char defender, int damage) {
		
		if (attacker.alignment == defender.alignment){
			Buff.affect(defender, Bless.class, Math.round(Bless.DURATION));
			return 0;
		}

		if (Char.hasProp(defender, Char.Property.UNDEAD) || Char.hasProp(defender, Char.Property.DEMONIC)){
			defender.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+buffedLvl() );
			Sample.INSTANCE.play(Assets.Sounds.BURNING);
			defender.damage(Random.NormalIntRange(10 + Dungeon.scalingDepth()/3, 20 + Dungeon.scalingDepth()/3), this);
		} else {
			Buff.affect(defender, Bless.class, Math.round(Bless.DURATION));
		}
		
		return super.proc(attacker, defender, damage);
	}
}
