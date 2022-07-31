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

package com.ultimatepixel.ultimatepixeldungeon;

import com.badlogic.gdx.Input;
import com.watabou.input.ControllerHandler;
import com.watabou.input.GameAction;
import com.watabou.input.KeyBindings;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;

import java.io.IOException;
import java.util.LinkedHashMap;

public class UPDAction extends GameAction {

	protected UPDAction(String name ){
		super( name );
	}

	//--New references to existing actions from GameAction
	public static final GameAction NONE  = GameAction.NONE;
	public static final GameAction BACK  = GameAction.BACK;

	public static final GameAction LEFT_CLICK   = GameAction.LEFT_CLICK;
	public static final GameAction RIGHT_CLICK  = GameAction.RIGHT_CLICK;
	public static final GameAction MIDDLE_CLICK = GameAction.MIDDLE_CLICK;
	//--

	public static final GameAction N            = new UPDAction("n");
	public static final GameAction W            = new UPDAction("w");
	public static final GameAction S            = new UPDAction("s");
	public static final GameAction E            = new UPDAction("e");
	public static final GameAction NW           = new UPDAction("nw");
	public static final GameAction NE           = new UPDAction("ne");
	public static final GameAction SW           = new UPDAction("sw");
	public static final GameAction SE           = new UPDAction("se");
	public static final GameAction WAIT_OR_PICKUP   = new UPDAction("wait_or_pickup");

	public static final GameAction INVENTORY    = new UPDAction("inventory");
	public static final GameAction INVENTORY_SELECTOR   = new UPDAction("inventory_selector");
	public static final GameAction QUICKSLOT_SELECTOR   = new UPDAction("quickslot_selector");
	public static final GameAction QUICKSLOT_1  = new UPDAction("quickslot_1");
	public static final GameAction QUICKSLOT_2  = new UPDAction("quickslot_2");
	public static final GameAction QUICKSLOT_3  = new UPDAction("quickslot_3");
	public static final GameAction QUICKSLOT_4  = new UPDAction("quickslot_4");
	public static final GameAction QUICKSLOT_5  = new UPDAction("quickslot_5");
	public static final GameAction QUICKSLOT_6  = new UPDAction("quickslot_6");

	public static final GameAction BAG_1        = new UPDAction("bag_1");
	public static final GameAction BAG_2        = new UPDAction("bag_2");
	public static final GameAction BAG_3        = new UPDAction("bag_3");
	public static final GameAction BAG_4        = new UPDAction("bag_4");
	public static final GameAction BAG_5        = new UPDAction("bag_5");

	public static final GameAction EXAMINE      = new UPDAction("examine");
	public static final GameAction WAIT         = new UPDAction("wait");
	public static final GameAction REST         = new UPDAction("rest");

	public static final GameAction TAG_ATTACK   = new UPDAction("tag_attack");
	public static final GameAction TAG_DANGER   = new UPDAction("tag_danger");
	public static final GameAction TAG_ACTION   = new UPDAction("tag_action");
	public static final GameAction TAG_LOOT     = new UPDAction("tag_loot");
	public static final GameAction TAG_RESUME   = new UPDAction("tag_resume");

	public static final GameAction HERO_INFO    = new UPDAction("hero_info");
	public static final GameAction JOURNAL      = new UPDAction("journal");

	public static final GameAction ZOOM_IN      = new UPDAction("zoom_in");
	public static final GameAction ZOOM_OUT     = new UPDAction("zoom_out");

	private static final LinkedHashMap<Integer, GameAction> defaultBindings = new LinkedHashMap<>();
	static {
		defaultBindings.put( Input.Keys.ESCAPE,         UPDAction.BACK );
		defaultBindings.put( Input.Keys.BACKSPACE,      UPDAction.BACK );

		defaultBindings.put( Input.Keys.W,              UPDAction.N );
		defaultBindings.put( Input.Keys.A,              UPDAction.W );
		defaultBindings.put( Input.Keys.S,              UPDAction.S );
		defaultBindings.put( Input.Keys.D,              UPDAction.E );
		defaultBindings.put( Input.Keys.SPACE,          UPDAction.WAIT_OR_PICKUP);

		defaultBindings.put( Input.Keys.UP,             UPDAction.N );
		defaultBindings.put( Input.Keys.LEFT,           UPDAction.W );
		defaultBindings.put( Input.Keys.DOWN,           UPDAction.S );
		defaultBindings.put( Input.Keys.RIGHT,          UPDAction.E );

		defaultBindings.put( Input.Keys.NUMPAD_8,       UPDAction.N );
		defaultBindings.put( Input.Keys.NUMPAD_4,       UPDAction.W );
		defaultBindings.put( Input.Keys.NUMPAD_2,       UPDAction.S );
		defaultBindings.put( Input.Keys.NUMPAD_6,       UPDAction.E );
		defaultBindings.put( Input.Keys.NUMPAD_7,       UPDAction.NW );
		defaultBindings.put( Input.Keys.NUMPAD_9,       UPDAction.NE );
		defaultBindings.put( Input.Keys.NUMPAD_1,       UPDAction.SW );
		defaultBindings.put( Input.Keys.NUMPAD_3,       UPDAction.SE );
		defaultBindings.put( Input.Keys.NUMPAD_5,       UPDAction.WAIT_OR_PICKUP );

		defaultBindings.put( Input.Keys.F,              UPDAction.INVENTORY );
		defaultBindings.put( Input.Keys.I,              UPDAction.INVENTORY );
		defaultBindings.put( Input.Keys.NUM_1,          UPDAction.QUICKSLOT_1 );
		defaultBindings.put( Input.Keys.NUM_2,          UPDAction.QUICKSLOT_2 );
		defaultBindings.put( Input.Keys.NUM_3,          UPDAction.QUICKSLOT_3 );
		defaultBindings.put( Input.Keys.NUM_4,          UPDAction.QUICKSLOT_4 );
		defaultBindings.put( Input.Keys.NUM_5,          UPDAction.QUICKSLOT_5 );
		defaultBindings.put( Input.Keys.NUM_6,          UPDAction.QUICKSLOT_6 );

		defaultBindings.put( Input.Keys.F1,             UPDAction.BAG_1 );
		defaultBindings.put( Input.Keys.F2,             UPDAction.BAG_2 );
		defaultBindings.put( Input.Keys.F3,             UPDAction.BAG_3 );
		defaultBindings.put( Input.Keys.F4,             UPDAction.BAG_4 );
		defaultBindings.put( Input.Keys.F5,             UPDAction.BAG_5 );

		defaultBindings.put( Input.Keys.E,              UPDAction.EXAMINE );
		defaultBindings.put( Input.Keys.Z,              UPDAction.REST );

		defaultBindings.put( Input.Keys.Q,              UPDAction.TAG_ATTACK );
		defaultBindings.put( Input.Keys.TAB,            UPDAction.TAG_DANGER );
		defaultBindings.put( Input.Keys.X,              UPDAction.TAG_ACTION );
		defaultBindings.put( Input.Keys.C,              UPDAction.TAG_LOOT );
		defaultBindings.put( Input.Keys.ENTER,          UPDAction.TAG_LOOT );
		defaultBindings.put( Input.Keys.R,              UPDAction.TAG_RESUME );

		defaultBindings.put( Input.Keys.H,              UPDAction.HERO_INFO );
		defaultBindings.put( Input.Keys.J,              UPDAction.JOURNAL );

		defaultBindings.put( Input.Keys.PLUS,           UPDAction.ZOOM_IN );
		defaultBindings.put( Input.Keys.EQUALS,         UPDAction.ZOOM_IN );
		defaultBindings.put( Input.Keys.MINUS,          UPDAction.ZOOM_OUT );
	}

	public static LinkedHashMap<Integer, GameAction> getDefaults() {
		return new LinkedHashMap<>(defaultBindings);
	}

	private static final LinkedHashMap<Integer, GameAction> defaultControllerBindings = new LinkedHashMap<>();
	static {
		defaultControllerBindings.put( Input.Keys.BUTTON_START,     UPDAction.BACK );

		defaultControllerBindings.put( Input.Keys.BUTTON_R2,        UPDAction.LEFT_CLICK );
		defaultControllerBindings.put( Input.Keys.BUTTON_THUMBR,    UPDAction.LEFT_CLICK );
		defaultControllerBindings.put( Input.Keys.BUTTON_L2,        UPDAction.RIGHT_CLICK );
		defaultControllerBindings.put( Input.Keys.BUTTON_SELECT,    UPDAction.MIDDLE_CLICK );

		defaultControllerBindings.put( Input.Keys.DPAD_UP+1000,     UPDAction.TAG_ACTION );
		defaultControllerBindings.put( Input.Keys.DPAD_LEFT+1000,   UPDAction.TAG_DANGER );
		defaultControllerBindings.put( Input.Keys.DPAD_DOWN+1000,   UPDAction.TAG_RESUME );
		defaultControllerBindings.put( Input.Keys.DPAD_RIGHT+1000,  UPDAction.TAG_LOOT );

		defaultControllerBindings.put( Input.Keys.BUTTON_THUMBL,    UPDAction.WAIT_OR_PICKUP );

		defaultControllerBindings.put( Input.Keys.BUTTON_R1,        UPDAction.ZOOM_IN );
		defaultControllerBindings.put( Input.Keys.BUTTON_L1,        UPDAction.ZOOM_OUT );

		defaultControllerBindings.put( Input.Keys.BUTTON_A,         UPDAction.TAG_ATTACK );
		defaultControllerBindings.put( Input.Keys.BUTTON_B,         UPDAction.EXAMINE );
		defaultControllerBindings.put( Input.Keys.BUTTON_X,         UPDAction.QUICKSLOT_SELECTOR );
		defaultControllerBindings.put( Input.Keys.BUTTON_Y,         UPDAction.INVENTORY_SELECTOR );
	}

	public static LinkedHashMap<Integer, GameAction> getControllerDefaults() {
		return new LinkedHashMap<>(defaultControllerBindings);
	}

	//hard bindings for android devices
	static {
		KeyBindings.addHardBinding( Input.Keys.BACK, UPDAction.BACK );
		KeyBindings.addHardBinding( Input.Keys.MENU, UPDAction.INVENTORY );
	}

	//we only save/loads keys which differ from the default configuration.
	private static final String BINDINGS_FILE = "keybinds.dat";

	public static void loadBindings(){

		if (!KeyBindings.getAllBindings().isEmpty()){
			return;
		}

		try {
			Bundle b = FileUtils.bundleFromFile(BINDINGS_FILE);

			Bundle firstKeys = b.getBundle("first_keys");
			Bundle secondKeys = b.getBundle("second_keys");
			Bundle thirdKeys = b.getBundle("third_keys");

			LinkedHashMap<Integer, GameAction> defaults = getDefaults();
			LinkedHashMap<Integer, GameAction> merged = new LinkedHashMap<>();

			for (GameAction a : allActions()) {
				if (firstKeys.contains(a.name()) && !ControllerHandler.icControllerKey(firstKeys.getInt(a.name()))) {
					if (firstKeys.getInt(a.name()) == 0){
						continue; //we have no keys assigned to this action, move to the next one
					} else {
						merged.put(firstKeys.getInt(a.name()), a);
						defaults.remove(firstKeys.getInt(a.name())); //prevent duplicates in other actions
					}
				} else {
					//if we have no custom key here, find the first one from defaults and merge it
					for (int i : defaults.keySet()){
						if (defaults.get(i) == a){
							merged.put(i, defaults.remove(i));
							break;
						}
					}
				}

				if (secondKeys.contains(a.name()) && !ControllerHandler.icControllerKey(secondKeys.getInt(a.name()))) {
					if (secondKeys.getInt(a.name()) == 0){
						continue; //we have no more keys assigned to this action, move to the next one
					} else {
						merged.put(secondKeys.getInt(a.name()), a);
						defaults.remove(secondKeys.getInt(a.name()));
					}
				} else {
					//if we have no custom key here, find the next one from defaults and merge it
					for (int i : defaults.keySet()){
						if (defaults.get(i) == a){
							merged.put(i, defaults.remove(i));
							break;
						}
					}
				}

				if (thirdKeys.contains(a.name()) && !ControllerHandler.icControllerKey(thirdKeys.getInt(a.name()))) {
					if (thirdKeys.getInt(a.name()) == 0){
						continue; //we have no more keys assigned to this action, move to the next one
					} else {
						merged.put(thirdKeys.getInt(a.name()), a);
						defaults.remove(thirdKeys.getInt(a.name()));
					}
				} else {
					//if we have no custom key here, find the next one from defaults and merge it
					for (int i : defaults.keySet()){
						if (defaults.get(i) == a){
							merged.put(i, defaults.remove(i));
							break;
						}
					}
				}

			}

			KeyBindings.setAllBindings(merged);

			defaults = getControllerDefaults();
			merged.clear();

			Bundle firstButtons = b.getBundle("first_keys_controller");
			Bundle secondButtons = b.getBundle("second_keys_controller");
			Bundle thirdButtons = b.getBundle("third_keys_controller");

			for (GameAction a : allActions()) {
				if (firstButtons.contains(a.name()) && ControllerHandler.icControllerKey(firstButtons.getInt(a.name()))) {
					if (firstButtons.getInt(a.name()) == 0){
						continue; //we have no keys assigned to this action, move to the next one
					} else {
						merged.put(firstButtons.getInt(a.name()), a);
						defaults.remove(firstButtons.getInt(a.name())); //prevent duplicates in other actions
					}
				} else {
					//if we have no custom key here, find the first one from defaults and merge it
					for (int i : defaults.keySet()){
						if (defaults.get(i) == a){
							merged.put(i, defaults.remove(i));
							break;
						}
					}
				}

				if (secondButtons.contains(a.name()) && ControllerHandler.icControllerKey(secondButtons.getInt(a.name()))) {
					if (secondButtons.getInt(a.name()) == 0){
						continue; //we have no more keys assigned to this action, move to the next one
					} else {
						merged.put(secondButtons.getInt(a.name()), a);
						defaults.remove(secondButtons.getInt(a.name()));
					}
				} else {
					//if we have no custom key here, find the next one from defaults and merge it
					for (int i : defaults.keySet()){
						if (defaults.get(i) == a){
							merged.put(i, defaults.remove(i));
							break;
						}
					}
				}

				if (thirdButtons.contains(a.name()) && ControllerHandler.icControllerKey(thirdButtons.getInt(a.name()))) {
					if (thirdButtons.getInt(a.name()) == 0){
						continue; //we have no more keys assigned to this action, move to the next one
					} else {
						merged.put(thirdButtons.getInt(a.name()), a);
						defaults.remove(thirdButtons.getInt(a.name()));
					}
				} else {
					//if we have no custom key here, find the next one from defaults and merge it
					for (int i : defaults.keySet()){
						if (defaults.get(i) == a){
							merged.put(i, defaults.remove(i));
							break;
						}
					}
				}

			}

			KeyBindings.setAllControllerBindings(merged);

		} catch (Exception e){
			KeyBindings.setAllBindings(getDefaults());
			KeyBindings.setAllControllerBindings(getControllerDefaults());
		}

	}

	public static void saveBindings(){

		Bundle b = new Bundle();

		Bundle firstKeys = new Bundle();
		Bundle secondKeys = new Bundle();
		Bundle thirdKeys = new Bundle();

		for (GameAction a : allActions()){
			int firstCur = 0;
			int secondCur = 0;
			int thirdCur = 0;
			int firstDef = 0;
			int secondDef = 0;
			int thirdDef = 0;

			for (int i : defaultBindings.keySet()){
				if (defaultBindings.get(i) == a){
					if (firstDef == 0) {
						firstDef = i;
					} else if (secondDef == 0) {
						secondDef = i;
					} else {
						thirdDef = i;
					}
				}
			}

			LinkedHashMap<Integer, GameAction> curBindings = KeyBindings.getAllBindings();
			for (int i : curBindings.keySet()){
				if (curBindings.get(i) == a){
					if (firstCur == 0) {
						firstCur = i;
					} else if (secondCur == 0) {
						secondCur = i;
					} else {
						thirdCur = i;
					}
				}
			}

			if (firstCur != firstDef){
				firstKeys.put(a.name(), firstCur);
			}
			if (secondCur != secondDef){
				secondKeys.put(a.name(), secondCur);
			}
			if (thirdCur != thirdDef){
				thirdKeys.put(a.name(), thirdCur);
			}

		}

		b.put("first_keys", firstKeys);
		b.put("second_keys", secondKeys);
		b.put("third_keys", thirdKeys);

		Bundle firstButtons = new Bundle();
		Bundle secondButtons = new Bundle();
		Bundle thirdButtons = new Bundle();

		for (GameAction a : allActions()){
			int firstCur = 0;
			int secondCur = 0;
			int thirdCur = 0;
			int firstDef = 0;
			int secondDef = 0;
			int thirdDef = 0;

			for (int i : defaultControllerBindings.keySet()){
				if (defaultControllerBindings.get(i) == a){
					if (firstDef == 0) {
						firstDef = i;
					} else if (secondDef == 0) {
						secondDef = i;
					} else {
						thirdDef = i;
					}
				}
			}

			LinkedHashMap<Integer, GameAction> curBindings = KeyBindings.getAllControllerBindings();
			for (int i : curBindings.keySet()){
				if (curBindings.get(i) == a){
					if (firstCur == 0) {
						firstCur = i;
					} else if (secondCur == 0) {
						secondCur = i;
					} else {
						thirdCur = i;
					}
				}
			}

			if (firstCur != firstDef){
				firstButtons.put(a.name(), firstCur);
			}
			if (secondCur != secondDef){
				secondButtons.put(a.name(), secondCur);
			}
			if (thirdCur != thirdDef){
				thirdButtons.put(a.name(), thirdCur);
			}

		}

		b.put("first_keys_controller", firstButtons);
		b.put("second_keys_controller", secondButtons);
		b.put("third_keys_controller", thirdButtons);

		try {
			FileUtils.bundleToFile(BINDINGS_FILE, b);
		} catch (IOException e) {
			UltimatePixelDungeon.reportException(e);
		}

	}

}
