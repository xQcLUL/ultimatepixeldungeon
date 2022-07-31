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
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Charm;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Degrade;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Hex;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.MagicalSleep;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Vulnerable;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Weakness;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.ultimatepixel.ultimatepixeldungeon.actors.mobs.DM100;
import com.ultimatepixel.ultimatepixeldungeon.actors.mobs.Eye;
import com.ultimatepixel.ultimatepixeldungeon.actors.mobs.Shaman;
import com.ultimatepixel.ultimatepixeldungeon.actors.mobs.Warlock;
import com.ultimatepixel.ultimatepixeldungeon.actors.mobs.YogFist;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.Armor;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfBlastWave;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfDisintegration;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfFireblast;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfFrost;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfLightning;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfLivingEarth;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfMagicMissile;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfPrismaticLight;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfTransfusion;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfWarding;
import com.ultimatepixel.ultimatepixeldungeon.levels.traps.DisintegrationTrap;
import com.ultimatepixel.ultimatepixeldungeon.levels.traps.GrimTrap;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.utils.Xml;
import com.watabou.utils.Random;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing(Xml.getColor("electric_blue"));
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( MagicalSleep.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		RESISTS.add( Vulnerable.class );
		RESISTS.add( Hex.class );
		RESISTS.add( Degrade.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );

		RESISTS.add( WarpBeacon.class );
		
		RESISTS.add( DM100.LightningBolt.class );
		RESISTS.add( Shaman.EarthenBolt.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( YogFist.BrightFist.LightBeam.class );
		RESISTS.add( YogFist.DarkFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see Hero.damage and GhostHero.damage and ArmoredStatue.damage
		return damage;
	}
	
	public static int drRoll( int level ){
		return Random.NormalIntRange(level, 3 + Math.round(level*1.5f));
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}