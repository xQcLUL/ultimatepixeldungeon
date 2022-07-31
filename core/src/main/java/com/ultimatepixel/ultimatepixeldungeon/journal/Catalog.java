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

package com.ultimatepixel.ultimatepixeldungeon.journal;

import com.ultimatepixel.ultimatepixeldungeon.Badges;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.ClothArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.HuntressArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.LeatherArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.MageArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.MailArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.PlateArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.RogueArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.ScaleArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.WarriorArmor;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.AlchemistsToolkit;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.ChaliceOfBlood;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.CloakOfShadows;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.DriedRose;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.EtherealChains;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.HornOfPlenty;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.MasterThievesArmband;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.SandalsOfNature;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.TalismanOfForesight;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.TimekeepersHourglass;
import com.ultimatepixel.ultimatepixeldungeon.items.artifacts.UnstableSpellbook;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfExperience;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfFrost;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfHaste;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfHealing;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfInvisibility;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfLevitation;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfLiquidFlame;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfMindVision;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfParalyticGas;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfPurity;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfStrength;
import com.ultimatepixel.ultimatepixeldungeon.items.potions.PotionOfToxicGas;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfAccuracy;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfElements;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfEnergy;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfEvasion;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfForce;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfFuror;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfHaste;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfMight;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfSharpshooting;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfTenacity;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.RingOfWealth;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfIdentify;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfLullaby;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfRage;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfRecharging;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfRetribution;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfTerror;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfBlastWave;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfCorrosion;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfCorruption;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfDisintegration;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfFireblast;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfFrost;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfLightning;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfLivingEarth;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfMagicMissile;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfPrismaticLight;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfRegrowth;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfTransfusion;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.WandOfWarding;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.AssassinsBlade;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.BattleAxe;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Crossbow;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Dagger;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Dirk;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Flail;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Gauntlet;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Glaive;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Gloves;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Greataxe;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Greatshield;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Greatsword;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.HandAxe;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Longsword;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Mace;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.MagesStaff;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Quarterstaff;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.RoundShield;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.RunicBlade;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Sai;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Scimitar;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Shortsword;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Spear;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Sword;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.WarHammer;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.Whip;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee.WornShortsword;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public enum Catalog {
	
	WEAPONS,
	ARMOR,
	WANDS,
	RINGS,
	ARTIFACTS,
	POTIONS,
	SCROLLS;
	
	private LinkedHashMap<Class<? extends Item>, Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}
	
	public boolean allSeen(){
		for (Class<?extends Item> item : items()){
			if (!seen.get(item)){
				return false;
			}
		}
		return true;
	}
	
	static {
		WEAPONS.seen.put( WornShortsword.class,             false);
		WEAPONS.seen.put( Gloves.class,                     false);
		WEAPONS.seen.put( Dagger.class,                     false);
		WEAPONS.seen.put( MagesStaff.class,                 false);
		WEAPONS.seen.put( Shortsword.class,                 false);
		WEAPONS.seen.put( HandAxe.class,                    false);
		WEAPONS.seen.put( Spear.class,                      false);
		WEAPONS.seen.put( Quarterstaff.class,               false);
		WEAPONS.seen.put( Dirk.class,                       false);
		WEAPONS.seen.put( Sword.class,                      false);
		WEAPONS.seen.put( Mace.class,                       false);
		WEAPONS.seen.put( Scimitar.class,                   false);
		WEAPONS.seen.put( RoundShield.class,                false);
		WEAPONS.seen.put( Sai.class,                        false);
		WEAPONS.seen.put( Whip.class,                       false);
		WEAPONS.seen.put( Longsword.class,                  false);
		WEAPONS.seen.put( BattleAxe.class,                  false);
		WEAPONS.seen.put( Flail.class,                      false);
		WEAPONS.seen.put( RunicBlade.class,                 false);
		WEAPONS.seen.put( AssassinsBlade.class,             false);
		WEAPONS.seen.put( Crossbow.class,                   false);
		WEAPONS.seen.put( Greatsword.class,                 false);
		WEAPONS.seen.put( WarHammer.class,                  false);
		WEAPONS.seen.put( Glaive.class,                     false);
		WEAPONS.seen.put( Greataxe.class,                   false);
		WEAPONS.seen.put( Greatshield.class,                false);
		WEAPONS.seen.put( Gauntlet.class,                   false);
	
		ARMOR.seen.put( ClothArmor.class,                   false);
		ARMOR.seen.put( LeatherArmor.class,                 false);
		ARMOR.seen.put( MailArmor.class,                    false);
		ARMOR.seen.put( ScaleArmor.class,                   false);
		ARMOR.seen.put( PlateArmor.class,                   false);
		ARMOR.seen.put( WarriorArmor.class,                 false);
		ARMOR.seen.put( MageArmor.class,                    false);
		ARMOR.seen.put( RogueArmor.class,                   false);
		ARMOR.seen.put( HuntressArmor.class,                false);
	
		WANDS.seen.put( WandOfMagicMissile.class,           false);
		WANDS.seen.put( WandOfLightning.class,              false);
		WANDS.seen.put( WandOfDisintegration.class,         false);
		WANDS.seen.put( WandOfFireblast.class,              false);
		WANDS.seen.put( WandOfCorrosion.class,              false);
		WANDS.seen.put( WandOfBlastWave.class,              false);
		WANDS.seen.put( WandOfLivingEarth.class,            false);
		WANDS.seen.put( WandOfFrost.class,                  false);
		WANDS.seen.put( WandOfPrismaticLight.class,         false);
		WANDS.seen.put( WandOfWarding.class,                false);
		WANDS.seen.put( WandOfTransfusion.class,            false);
		WANDS.seen.put( WandOfCorruption.class,             false);
		WANDS.seen.put( WandOfRegrowth.class,               false);
	
		RINGS.seen.put( RingOfAccuracy.class,               false);
		RINGS.seen.put( RingOfEnergy.class,                 false);
		RINGS.seen.put( RingOfElements.class,               false);
		RINGS.seen.put( RingOfEvasion.class,                false);
		RINGS.seen.put( RingOfForce.class,                  false);
		RINGS.seen.put( RingOfFuror.class,                  false);
		RINGS.seen.put( RingOfHaste.class,                  false);
		RINGS.seen.put( RingOfMight.class,                  false);
		RINGS.seen.put( RingOfSharpshooting.class,          false);
		RINGS.seen.put( RingOfTenacity.class,               false);
		RINGS.seen.put( RingOfWealth.class,                 false);
	
		ARTIFACTS.seen.put( AlchemistsToolkit.class,        false);
		//ARTIFACTS.seen.put( CapeOfThorns.class,             false);
		ARTIFACTS.seen.put( ChaliceOfBlood.class,           false);
		ARTIFACTS.seen.put( CloakOfShadows.class,           false);
		ARTIFACTS.seen.put( DriedRose.class,                false);
		ARTIFACTS.seen.put( EtherealChains.class,           false);
		ARTIFACTS.seen.put( HornOfPlenty.class,             false);
		//ARTIFACTS.seen.put( LloydsBeacon.class,             false);
		ARTIFACTS.seen.put( MasterThievesArmband.class,     false);
		ARTIFACTS.seen.put( SandalsOfNature.class,          false);
		ARTIFACTS.seen.put( TalismanOfForesight.class,      false);
		ARTIFACTS.seen.put( TimekeepersHourglass.class,     false);
		ARTIFACTS.seen.put( UnstableSpellbook.class,        false);
	
		POTIONS.seen.put( PotionOfHealing.class,            false);
		POTIONS.seen.put( PotionOfStrength.class,           false);
		POTIONS.seen.put( PotionOfLiquidFlame.class,        false);
		POTIONS.seen.put( PotionOfFrost.class,              false);
		POTIONS.seen.put( PotionOfToxicGas.class,           false);
		POTIONS.seen.put( PotionOfParalyticGas.class,       false);
		POTIONS.seen.put( PotionOfPurity.class,             false);
		POTIONS.seen.put( PotionOfLevitation.class,         false);
		POTIONS.seen.put( PotionOfMindVision.class,         false);
		POTIONS.seen.put( PotionOfInvisibility.class,       false);
		POTIONS.seen.put( PotionOfExperience.class,         false);
		POTIONS.seen.put( PotionOfHaste.class,              false);
	
		SCROLLS.seen.put( ScrollOfIdentify.class,           false);
		SCROLLS.seen.put( ScrollOfUpgrade.class,            false);
		SCROLLS.seen.put( ScrollOfRemoveCurse.class,        false);
		SCROLLS.seen.put( ScrollOfMagicMapping.class,       false);
		SCROLLS.seen.put( ScrollOfTeleportation.class,      false);
		SCROLLS.seen.put( ScrollOfRecharging.class,         false);
		SCROLLS.seen.put( ScrollOfMirrorImage.class,        false);
		SCROLLS.seen.put( ScrollOfTerror.class,             false);
		SCROLLS.seen.put( ScrollOfLullaby.class,            false);
		SCROLLS.seen.put( ScrollOfRage.class,               false);
		SCROLLS.seen.put( ScrollOfRetribution.class,        false);
		SCROLLS.seen.put( ScrollOfTransmutation.class,      false);
	}
	
	public static LinkedHashMap<Catalog, Badges.Badge> catalogBadges = new LinkedHashMap<>();
	static {
		catalogBadges.put(WEAPONS, Badges.Badge.ALL_WEAPONS_IDENTIFIED);
		catalogBadges.put(ARMOR, Badges.Badge.ALL_ARMOR_IDENTIFIED);
		catalogBadges.put(WANDS, Badges.Badge.ALL_WANDS_IDENTIFIED);
		catalogBadges.put(RINGS, Badges.Badge.ALL_RINGS_IDENTIFIED);
		catalogBadges.put(ARTIFACTS, Badges.Badge.ALL_ARTIFACTS_IDENTIFIED);
		catalogBadges.put(POTIONS, Badges.Badge.ALL_POTIONS_IDENTIFIED);
		catalogBadges.put(SCROLLS, Badges.Badge.ALL_SCROLLS_IDENTIFIED);
	}
	
	public static boolean isSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass)) {
				return cat.seen.get(itemClass);
			}
		}
		return false;
	}
	
	public static void setSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass) && !cat.seen.get(itemClass)) {
				cat.seen.put(itemClass, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateItemsIdentified();
	}
	
	private static final String CATALOG_ITEMS = "catalog_items";
	
	public static void store( Bundle bundle ){
		
		Badges.loadGlobal();
		
		ArrayList<Class> seen = new ArrayList<>();
		
		//if we have identified all items of a set, we use the badge to keep track instead.
		if (!Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)) {
			for (Catalog cat : values()) {
				if (!Badges.isUnlocked(catalogBadges.get(cat))) {
					for (Class<? extends Item> item : cat.items()) {
						if (cat.seen.get(item)) seen.add(item);
					}
				}
			}
		}
		
		bundle.put( CATALOG_ITEMS, seen.toArray(new Class[0]) );
		
	}
	
	public static void restore( Bundle bundle ){
		
		Badges.loadGlobal();
		
		//logic for if we have all badges
		if (Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)){
			for ( Catalog cat : values()){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
			return;
		}
		
		//catalog-specific badge logic
		for (Catalog cat : values()){
			if (Badges.isUnlocked(catalogBadges.get(cat))){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
		}
		
		//general save/load
		if (bundle.contains(CATALOG_ITEMS)) {
			List<Class> seenClasses = new ArrayList<>();
			if (bundle.contains(CATALOG_ITEMS)) {
				seenClasses = Arrays.asList(bundle.getClassArray(CATALOG_ITEMS));
			}
			
			for (Catalog cat : values()) {
				for (Class<? extends Item> item : cat.items()) {
					if (seenClasses.contains(item)) {
						cat.seen.put(item, true);
					}
				}
			}
		}
	}
	
}
