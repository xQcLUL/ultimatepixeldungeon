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

package com.ultimatepixel.ultimatepixeldungeon.actors.hero.abilities.rogue;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Actor;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Barrier;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Buff;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Cripple;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.FlavourBuff;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Terror;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Talent;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.abilities.ArmorAbility;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.ClassArmor;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.ui.BuffIndicator;
import com.ultimatepixel.ultimatepixeldungeon.ui.HeroIcon;
import com.ultimatepixel.ultimatepixeldungeon.utils.BArray;
import com.ultimatepixel.ultimatepixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

public class DeathMark extends ArmorAbility {

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	{
		baseChargeUse = 25f;
	}

	@Override
	public float chargeUse( Hero hero ) {
		float chargeUse = super.chargeUse(hero);
		if (hero.buff(DoubleMarkTracker.class) != null){
			//reduced charge use by 30%/50%/65%/75%
			chargeUse *= Math.pow(0.707, hero.pointsInTalent(Talent.DOUBLE_MARK));
		}
		return chargeUse;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {
		if (target == null){
			return;
		}

		Char ch = Actor.findChar(target);

		if (ch == null){
			GLog.w(Messages.get(this, "no_target"));
			return;
		} else if (ch.alignment != Char.Alignment.ENEMY){
			GLog.w(Messages.get(this, "ally_target"));
			return;
		}

		if (ch != null){
			Buff.affect(ch, DeathMarkTracker.class, 5f).setInitialHP(ch.HP);
		}

		armor.charge -= chargeUse( hero );
		armor.updateQuickslot();
		hero.sprite.zap(target);

		hero.next();

		if (hero.buff(DoubleMarkTracker.class) != null){
			hero.buff(DoubleMarkTracker.class).detach();
		} else if (hero.hasTalent(Talent.DOUBLE_MARK)) {
			Buff.affect(hero, DoubleMarkTracker.class, 0.01f);
		}

	}

	public static void processFearTheReaper( Char ch ){
		if (ch.HP > 0 || ch.buff(DeathMarkTracker.class) == null){
			return;
		}

		if (Dungeon.hero.hasTalent(Talent.FEAR_THE_REAPER)) {
			if (Dungeon.hero.pointsInTalent(Talent.FEAR_THE_REAPER) >= 2) {
				Buff.prolong(ch, Terror.class, 5f).object = Dungeon.hero.id();
			}
			Buff.prolong(ch, Cripple.class, 5f);

			if (Dungeon.hero.pointsInTalent(Talent.FEAR_THE_REAPER) >= 3) {
				boolean[] passable = BArray.not(Dungeon.level.solid, null);
				PathFinder.buildDistanceMap(ch.pos, passable, 3);

				for (Char near : Actor.chars()) {
					if (near != ch && near.alignment == Char.Alignment.ENEMY
							&& PathFinder.distance[near.pos] != Integer.MAX_VALUE) {
						if (Dungeon.hero.pointsInTalent(Talent.FEAR_THE_REAPER) == 4) {
							Buff.prolong(near, Terror.class, 5f).object = Dungeon.hero.id();
						}
						Buff.prolong(near, Cripple.class, 5f);
					}
				}
			}
		}
	}

	public static class DoubleMarkTracker extends FlavourBuff{};

	@Override
	public int icon() {
		return HeroIcon.DEATH_MARK;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.FEAR_THE_REAPER, Talent.DEATHLY_DURABILITY, Talent.DOUBLE_MARK, Talent.HEROIC_ENERGY};
	}

	public static class DeathMarkTracker extends FlavourBuff {

		int initialHP = 0;

		{
			type = buffType.NEGATIVE;
			announced = true;
		}

		@Override
		public int icon() {
			return BuffIndicator.INVERT_MARK;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(1f, 0.2f, 0.2f);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			//TODO show initial HP here?
			return Messages.get(this, "desc", dispTurns(visualcooldown()));
		}

		private void setInitialHP( int hp ){
			if (initialHP < hp){
				initialHP = hp;
			}
		}

		@Override
		public boolean attachTo(Char target) {
			if (super.attachTo(target)){
				target.deathMarked = true;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void detach() {
			super.detach();
			target.deathMarked = false;
			if (!target.isAlive()){
				target.sprite.flash();
				target.sprite.bloodBurstA(target.sprite.center(), target.HT*2);
				Sample.INSTANCE.play(Assets.Sounds.HIT_STAB);
				Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
				target.die(this);
				int shld = Math.round(initialHP * (0.125f*Dungeon.hero.pointsInTalent(Talent.DEATHLY_DURABILITY)));
				if (shld > 0 && target.alignment != Char.Alignment.ALLY){
					Buff.affect(Dungeon.hero, Barrier.class).setShield(shld);
				}
			}
		}

		private static String INITIAL_HP = "initial_hp";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(INITIAL_HP, initialHP);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			initialHP = bundle.getInt(INITIAL_HP);
		}
	}

}
