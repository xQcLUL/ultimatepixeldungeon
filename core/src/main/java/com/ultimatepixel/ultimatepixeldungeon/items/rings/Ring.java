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

package com.ultimatepixel.ultimatepixeldungeon.items.rings;

import com.ultimatepixel.ultimatepixeldungeon.Badges;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.UltimatePixelDungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Buff;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.EnhancedRings;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Talent;
import com.ultimatepixel.ultimatepixeldungeon.effects.Speck;
import com.ultimatepixel.ultimatepixeldungeon.items.Generator;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.items.ItemStatusHandler;
import com.ultimatepixel.ultimatepixeldungeon.items.KindofMisc;
import com.ultimatepixel.ultimatepixeldungeon.items.spells.Spell;
import com.ultimatepixel.ultimatepixeldungeon.items.stones.Runestone;
import com.ultimatepixel.ultimatepixeldungeon.journal.Catalog;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.ultimatepixel.ultimatepixeldungeon.utils.GLog;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Ring extends KindofMisc {

	public float getEvasionStoneSkill(){
		return stone1.evasionSkill+stone2.evasionSkill+stone3.evasionSkill;
	}

	public int getDrStoneSkill(){
		return stone1.drSkill+stone2.drSkill+stone3.drSkill;
	}

	public int getDmgStoneSkill(){
		return stone1.dmgSkill+stone2.dmgSkill+stone3.dmgSkill;
	}

	public float getSpeedStoneSkill(){
		return stone1.speedSkill+stone2.speedSkill+stone3.speedSkill;
	}

	public float getAccStoneSkill(){
		return stone1.accSkill+stone2.accSkill+stone3.accSkill;
	}

	public boolean getCanSurpriseAttackStoneSkill(){
		if(stone1.canSupriseAttack) return true;
		if(stone2.canSupriseAttack) return true;
		if(stone3.canSupriseAttack) return true;
		return false;
	}
	
	protected Buff buff;

	private Runestone stone1 = new Runestone.PlaceHolder();
	private Runestone stone2 = new Runestone.PlaceHolder();
	private Runestone stone3 = new Runestone.PlaceHolder();

	public void setRunestone(Runestone s, int slotActive) {
		switch (slotActive){
			case 0:
				stone1 = s;
				break;
			case 1:
				stone2 = s;
				break;
			case 2:
				stone3 = s;
				break;
		}
	}

	public ArrayList<Runestone> stoneSlots() {
		ArrayList<Runestone> stoneSlots = new ArrayList<>();
		stoneSlots.add( stone1 );
		stoneSlots.add( stone2 );
		stoneSlots.add( stone3 );
		return stoneSlots;
	}

	private Spell spell1 = new Spell.PlaceHolder();
	private Spell spell2 = new Spell.PlaceHolder();
	private Spell spell3 = new Spell.PlaceHolder();

	public void setSpell(Spell s, int slotActive) {
		switch (slotActive){
			case 0:
				spell1 = s;
				break;
			case 1:
				spell2 = s;
				break;
			case 2:
				spell3 = s;
				break;
		}
	}

	public ArrayList<Spell> spellSlots() {
		ArrayList<Spell> spellSlots = new ArrayList<>();
		spellSlots.add( spell1 );
		spellSlots.add( spell2 );
		spellSlots.add( spell3 );
		return spellSlots;
	}

	private static final LinkedHashMap<String, Integer> gems = new LinkedHashMap<String, Integer>() {
		{
			put("garnet",ItemSpriteSheet.RING_GARNET);
			put("ruby",ItemSpriteSheet.RING_RUBY);
			put("topaz",ItemSpriteSheet.RING_TOPAZ);
			put("emerald",ItemSpriteSheet.RING_EMERALD);
			put("onyx",ItemSpriteSheet.RING_ONYX);
			put("opal",ItemSpriteSheet.RING_OPAL);
			put("tourmaline",ItemSpriteSheet.RING_TOURMALINE);
			put("sapphire",ItemSpriteSheet.RING_SAPPHIRE);
			put("amethyst",ItemSpriteSheet.RING_AMETHYST);
			put("quartz",ItemSpriteSheet.RING_QUARTZ);
			put("agate",ItemSpriteSheet.RING_AGATE);
			put("diamond",ItemSpriteSheet.RING_DIAMOND);
		}
	};
	
	private static ItemStatusHandler<Ring> handler;
	
	private String gem;
	
	//rings cannot be 'used' like other equipment, so they ID purely based on exp
	private float levelsToID = 1;
	
	@SuppressWarnings("unchecked")
	public static void initGems() {
		handler = new ItemStatusHandler<>( (Class<? extends Ring>[])Generator.Category.RING.classes, gems );
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		handler.saveSelectively( bundle, items );
	}
	
	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<>( (Class<? extends Ring>[])Generator.Category.RING.classes, gems, bundle );
	}
	
	public Ring() {
		super();
		reset();
	}

	//anonymous rings are always IDed, do not affect ID status,
	//and their sprite is replaced by a placeholder if they are not known,
	//useful for items that appear in UIs, or which are only spawned for their effects
	protected boolean anonymous = false;
	public void anonymize(){
		if (!isKnown()) image = ItemSpriteSheet.RING_HOLDER;
		anonymous = true;
	}
	
	public void reset() {
		super.reset();
		levelsToID = 1;
		if (handler != null && handler.contains(this)){
			image = handler.image(this);
			gem = handler.label(this);
		}
	}
	
	public void activate( Char ch ) {
		if (buff != null){
			buff.detach();
			buff = null;
		}
		buff = buff();
		buff.attachTo( ch );
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {

			if (buff != null) {
				buff.detach();
				buff = null;
			}

			return true;

		} else {

			return false;

		}
	}
	
	public boolean isKnown() {
		return anonymous || (handler != null && handler.isKnown( this ));
	}
	
	public void setKnown() {
		if (!anonymous) {
			if (!isKnown()) {
				handler.know(this);
			}

			if (Dungeon.hero.isAlive()) {
				Catalog.setSeen(getClass());
			}
		}
	}
	
	@Override
	public String name() {
		return isKnown() ? super.name() : Messages.get(Ring.class, gem);
	}
	
	@Override
	public String info(){
		
		String desc = isKnown() ? super.desc() : Messages.get(this, "unknown_desc");
		
		if (cursed && isEquipped( Dungeon.hero )) {
			desc += "\n\n" + Messages.get(Ring.class, "cursed_worn");
			
		} else if (cursed && cursedKnown) {
			desc += "\n\n" + Messages.get(Ring.class, "curse_known");
			
		} else if (!isIdentified() && cursedKnown){
			desc += "\n\n" + Messages.get(Ring.class, "not_cursed");
			
		}
		
		if (isKnown()) {
			desc += "\n\n" + statsInfo();
		}

		if (!(stone1 instanceof Runestone.PlaceHolder) || !(stone2 instanceof Runestone.PlaceHolder) || !(stone3 instanceof Runestone.PlaceHolder)) {
			desc += "\n\n" + runeStats();
		}
		
		return desc;
	}

	private String runeStats(){
		String endString = "";
		if(getEvasionStoneSkill() != 3f){
			String evasion = Messages.get(Ring.class, "rune_evasion")+ ": _"+Math.round((getEvasionStoneSkill()-3f)*100)+"%_"+"\n";
			endString += evasion;
		}
		if(getDrStoneSkill() != 0){
			String drString = Messages.get(Ring.class, "rune_dr")+": _"+getDrStoneSkill()+"_\n";
			endString += drString;
		}
		if(getDmgStoneSkill() != 0){
			String dmgString = Messages.get(Ring.class, "rune_dmg")+": _"+getDmgStoneSkill()+"_\n";
			endString += dmgString;
		}
		if(getSpeedStoneSkill() != 3f){
			String speedString = Messages.get(Ring.class, "rune_speed")+": _"+Math.round((getSpeedStoneSkill()-3f)*100)+"%_"+"\n";
			endString += speedString;
		}
		if(getCanSurpriseAttackStoneSkill()){
			String surString = Messages.get(Ring.class, "rune_surprise")+": _"+getCanSurpriseAttackStoneSkill()+"_\n";
			endString += surString;
		}
		if(getAccStoneSkill() != 3f){
			String accString = Messages.get(Ring.class, "rune_acc")+": _"+Math.round((getAccStoneSkill()-3f)*100)+"%_"+"\n";
			endString += accString;
		}
		int s = 0;
		for(Item i : stoneSlots()){
			if(!(i instanceof Runestone.PlaceHolder)){
				s++;
			}
		}
		if(s > 0) endString += "\n"+Messages.get(this, "rune_stones_used")+": _"+s+"_";
		return endString;
	}
	
	protected String statsInfo(){
		return "";
	}
	
	@Override
	public Item upgrade() {
		super.upgrade();
		
		if (Random.Int(3) == 0) {
			cursed = false;
		}
		
		return this;
	}

	public int proc(final Char enemy, int damage){
		return damage;
	}

	@Override
	public Emitter emitter() {
		Emitter emitter = new Emitter();
		if (!(stone1 instanceof Runestone.PlaceHolder)
				|| !(stone2 instanceof Runestone.PlaceHolder)
				|| !(stone3 instanceof Runestone.PlaceHolder)
				|| !(spell1 instanceof Spell.PlaceHolder)
				|| !(spell2 instanceof Spell.PlaceHolder)
				|| !(spell3 instanceof Spell.PlaceHolder)) {
			emitter.pos(ItemSpriteSheet.film.width(image)/2f + 2f, ItemSpriteSheet.film.height(image)/3f);
			emitter.fillTarget = false;
			emitter.pour(Speck.factory( Speck.RED_LIGHT ), 0.6f);
		}
		return emitter;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		ItemSprite.Glowing COLOR = null;
		if (!(stone1 instanceof Runestone.PlaceHolder) && !(stone2 instanceof Runestone.PlaceHolder) && !(stone3 instanceof Runestone.PlaceHolder)) {
			 COLOR = new ItemSprite.Glowing(stone1.color + stone2.color + stone3.color);
		}
		return COLOR;
	}

	@Override
	public boolean isIdentified() {
		return super.isIdentified() && isKnown();
	}
	
	@Override
	public Item identify( boolean byHero ) {
		setKnown();
		levelsToID = 0;
		return super.identify(byHero);
	}
	
	@Override
	public Item random() {
		//+0: 66.67% (2/3)
		//+1: 26.67% (4/15)
		//+2: 6.67%  (1/15)
		int n = 0;
		if (Random.Int(3) == 0) {
			n++;
			if (Random.Int(5) == 0){
				n++;
			}
		}
		level(n);
		
		//30% chance to be cursed
		if (Random.Float() < 0.3f) {
			cursed = true;
		}
		
		return this;
	}
	
	public static HashSet<Class<? extends Ring>> getKnown() {
		return handler.known();
	}
	
	public static HashSet<Class<? extends Ring>> getUnknown() {
		return handler.unknown();
	}
	
	public static boolean allKnown() {
		return handler.known().size() == Generator.Category.RING.classes.length;
	}
	
	@Override
	public int value() {
		int price = 75;
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level() > 0) {
				price *= (level() + 1);
			} else if (level() < 0) {
				price /= (1 - level());
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}
	
	protected RingBuff buff() {
		return null;
	}

	private static final String LEVELS_TO_ID    = "levels_to_ID";
	private static final String STONE1		    = "stone_1";
	private static final String STONE2		    = "stone_2";
	private static final String STONE3		    = "stone_3";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVELS_TO_ID, levelsToID );
		bundle.put(STONE1, stone1.getClass());
		bundle.put(STONE2, stone2.getClass());
		bundle.put(STONE3, stone3.getClass());

	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		levelsToID = bundle.getFloat( LEVELS_TO_ID );
		try {
			stone1 = (Runestone) bundle.getClass(STONE1).newInstance();
			stone2 = (Runestone) bundle.getClass(STONE2).newInstance();
			stone3 = (Runestone) bundle.getClass(STONE3).newInstance();
		} catch (Exception e) {
			UltimatePixelDungeon.reportException(e);
		}
	}
	
	public void onHeroGainExp( float levelPercent, Hero hero ){
		if (isIdentified() || !isEquipped(hero)) return;
		levelPercent *= Talent.itemIDSpeedFactor(hero, this);
		//becomes IDed after 1 level
		levelsToID -= levelPercent;
		if (levelsToID <= 0){
			identify();
			GLog.p( Messages.get(Ring.class, "identify", toString()) );
			Badges.validateItemLevelAquired( this );
		}
	}

	@Override
	public int buffedLvl() {
		int lvl = super.buffedLvl();
		if (Dungeon.hero.buff(EnhancedRings.class) != null){
			lvl++;
		}
		return lvl;
	}

	public static int getBonus(Char target, Class<?extends RingBuff> type){
		int bonus = 0;
		for (RingBuff buff : target.buffs(type)) {
			bonus += buff.level();
		}
		return bonus;
	}

	public static int getBuffedBonus(Char target, Class<?extends RingBuff> type){
		int bonus = 0;
		for (RingBuff buff : target.buffs(type)) {
			bonus += buff.buffedLvl();
		}
		return bonus;
	}
	
	public int soloBonus(){
		if (cursed){
			return Math.min( 0, Ring.this.level()-2 );
		} else {
			return Ring.this.level()+1;
		}
	}

	public int soloBuffedBonus(){
		if (cursed){
			return Math.min( 0, Ring.this.buffedLvl()-2 );
		} else {
			return Ring.this.buffedLvl()+1;
		}
	}

	public class RingBuff extends Buff {
		
		@Override
		public boolean act() {
			
			spend( TICK );
			
			return true;
		}

		public int level(){
			return Ring.this.soloBonus();
		}

		public int buffedLvl(){
			return Ring.this.soloBuffedBonus();
		}

	}
}
