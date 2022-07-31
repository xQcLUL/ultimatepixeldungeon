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

package com.ultimatepixel.ultimatepixeldungeon.items;

import com.ultimatepixel.ultimatepixeldungeon.Badges;
import com.ultimatepixel.ultimatepixeldungeon.Challenges;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.UltimatePixelDungeon;
import com.ultimatepixel.ultimatepixeldungeon.Statistics;
import com.ultimatepixel.ultimatepixeldungeon.actors.Actor;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.AscensionChallenge;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.scenes.AmuletScene;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.io.IOException;
import java.util.ArrayList;

public class Amulet extends Item {
	
	private static final String AC_END = "END";
	
	{
		image = ItemSpriteSheet.AMULET;
		
		unique = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (hero.buff(AscensionChallenge.class) != null){
			actions.clear();
		} else {
			actions.add(AC_END);
		}
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_END)) {
			showAmuletScene( false );
		}
	}
	
	@Override
	public boolean doPickUp(Hero hero, int pos) {
		if (super.doPickUp( hero, pos )) {
			
			if (!Statistics.amuletObtained) {
				Statistics.amuletObtained = true;
				hero.spend(-TIME_TO_PICK_UP);

				//add a delayed actor here so pickup behaviour can fully process.
				Actor.addDelayed(new Actor(){
					@Override
					protected boolean act() {
						Actor.remove(this);
						showAmuletScene( true );
						return false;
					}
				}, -5);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	private void showAmuletScene( boolean showText ) {
		AmuletScene.noText = !showText;
		Game.switchScene( AmuletScene.class, new Game.SceneChangeCallback() {
			@Override
			public void beforeCreate() {

			}

			@Override
			public void afterCreate() {
				Badges.validateVictory();
				Badges.validateChampion(Challenges.activeChallenges());
				try {
					Dungeon.saveAll();
				} catch (IOException e) {
					UltimatePixelDungeon.reportException(e);
				}
			}
		});
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (Dungeon.hero.buff(AscensionChallenge.class) == null){
			desc += "\n\n" + Messages.get(this, "desc_origins");
		} else {
			desc += "\n\n" + Messages.get(this, "desc_ascent");
		}

		return desc;
	}
}
