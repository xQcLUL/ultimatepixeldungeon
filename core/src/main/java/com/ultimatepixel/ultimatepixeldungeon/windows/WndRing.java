/*
 *
 *  * Pixel Dungeon
 *  * Copyright (C) 2012-2015 Oleg Dolya
 *  *
 *  * Shattered Pixel Dungeon
 *  * Copyright (C) 2014-2022 Evan Debenham
 *  *
 *  * Ultimate Pixel Dungeon
 *  * Copyright (C) 2022
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.ultimatepixel.ultimatepixeldungeon.windows;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.items.bags.Bag;
import com.ultimatepixel.ultimatepixeldungeon.items.bags.VelvetPouch;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.Ring;
import com.ultimatepixel.ultimatepixeldungeon.items.stones.Runestone;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.ultimatepixel.ultimatepixeldungeon.ui.InventoryPane;
import com.ultimatepixel.ultimatepixeldungeon.ui.InventorySlot;
import com.ultimatepixel.ultimatepixeldungeon.ui.RedButton;
import com.ultimatepixel.ultimatepixeldungeon.ui.Window;
import com.ultimatepixel.ultimatepixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class WndRing extends WndInfoItem{

    private static final float BUTTON_HEIGHT	= 16;

    private static final float GAP	= 2;

    private InventorySlot inv1;
    private InventorySlot inv2;
    private InventorySlot inv3;

    private WndRing INSTANCE;

    private int activeSlot = 0;

    private float yHeight;
    private Window ownerWindow;
    private Ring itemDisplayed;

    public WndRing(final Window owner, final Item item) {
        super(item);

        INSTANCE = this;
        ownerWindow = owner;
        itemDisplayed = (Ring) item;

        float y = height;

        int slotWidth = 16;
        int slotHeight = 20;

        WndBag.ItemSelector itemSelector = new WndBag.ItemSelector(){

            @Override
            public String textPrompt() {
                return Messages.get(WndRing.class, "select_stone");
            }

            @Override
            public Class<? extends Bag> preferredBag() {
                return VelvetPouch.class;
            }

            @Override
            public boolean itemSelectable(Item item) {
                return item instanceof Runestone;
            }

            @Override
            public void onSelect(Item i) {
                if(i instanceof Runestone){
                    if(!(((Ring) item).stoneSlots().get(activeSlot) instanceof Runestone.PlaceHolder)){
                        ((Ring) item).stoneSlots().get(activeSlot).doDrop(Dungeon.hero);
                    }
                    Runestone st = (Runestone) i.detach(Dungeon.hero.belongings.backpack);
                    ((Ring) item).setRunestone(st, activeSlot);
                    ((Ring) item).stoneSlots().set(activeSlot, st);
                    switch (activeSlot){
                        case 0:
                            inv1.getSprite().view(st);
                            INSTANCE.layoutIcons(yHeight, true);
                            break;
                        case 1:
                            inv2.getSprite().view(st);
                            INSTANCE.layoutIcons(yHeight, true);
                            break;
                        case 2:
                            inv3.getSprite().view(st);
                            INSTANCE.layoutIcons(yHeight, true);
                            break;
                    }
                }
            }
        };

        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item) && item != null) {
            y += GAP;
           inv1 = new InventorySlot(((Ring) item).stoneSlots().get(0)){
               boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 0;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector);
                    }
                    longClicked = false;
                }

               @Override
               protected boolean onLongClick() {
                   activeSlot = 0;
                   if(itemDisplayed.stoneSlots().get(activeSlot) instanceof Runestone.PlaceHolder){
                       return super.onLongClick();
                   }
                   longClicked = true;
                   itemDisplayed.stoneSlots().get(activeSlot).doDrop(Dungeon.hero);
                   Runestone runestone = new Runestone.PlaceHolder();
                   itemDisplayed.setRunestone(runestone, activeSlot);
                   inv1.getSprite().view(runestone);
                   INSTANCE.layoutIcons(yHeight, true);
                   return super.onLongClick();
               }
           };
           inv2 = new InventorySlot(((Ring) item).stoneSlots().get(1)){

               boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 1;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector);
                    }
                    longClicked = false;
                }

               @Override
               protected boolean onLongClick() {
                    activeSlot = 1;
                    if(itemDisplayed.stoneSlots().get(activeSlot) instanceof Runestone.PlaceHolder){
                        return super.onLongClick();
                    }
                    longClicked = true;
                    itemDisplayed.stoneSlots().get(activeSlot).doDrop(Dungeon.hero);
                    Runestone runestone = new Runestone.PlaceHolder();
                    itemDisplayed.setRunestone(runestone, activeSlot);
                    inv1.getSprite().view(runestone);
                    INSTANCE.layoutIcons(yHeight, true);
                    return super.onLongClick();
               }
           };
            inv3 = new InventorySlot(((Ring) item).stoneSlots().get(2)){
                boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 2;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector);
                    }
                    longClicked = false;
                }

                @Override
                protected boolean onLongClick() {
                    activeSlot = 2;
                    if(itemDisplayed.stoneSlots().get(activeSlot) instanceof Runestone.PlaceHolder){
                        return super.onLongClick();
                    }
                    longClicked = true;
                    itemDisplayed.stoneSlots().get(activeSlot).doDrop(Dungeon.hero);
                    Runestone runestone = new Runestone.PlaceHolder();
                    itemDisplayed.setRunestone(runestone, activeSlot);
                    inv1.getSprite().view(runestone);
                    INSTANCE.layoutIcons(yHeight, true);
                    return super.onLongClick();
                }
            };
            ArrayList<RedButton> buttons = new ArrayList<>();
            for (final String action : item.actions( Dungeon.hero )) {

                RedButton btn = new RedButton( item.actionName(action, Dungeon.hero), 8 ) {
                    @Override
                    protected void onClick() {
                        hide();
                        if (owner != null && owner.parent != null) owner.hide();
                        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)){
                            item.execute( Dungeon.hero, action );
                        }
                        Item.updateQuickslot();
                        if (action == item.defaultAction && item.usesTargeting && owner == null){
                            InventoryPane.useTargeting();
                        }
                    }
                };
                btn.setSize( btn.reqWidth(), BUTTON_HEIGHT );
                buttons.add(btn);
                add( btn );

                if (action.equals(item.defaultAction)) {
                    btn.textColor( TITLE_COLOR );
                }

            }
            y = layoutButtons(buttons, width, y);
            y += GAP;
            inv1.setRect(0, y, slotWidth, slotHeight);
            add(inv1);
            inv2.setRect(inv1.width()+GAP, y, slotWidth, slotHeight);
            add(inv2);
            inv3.setRect(inv2.width() + GAP + inv1.width() + GAP, y, slotWidth, slotHeight);
            add(inv3);
        }
        yHeight = y;
        layoutIcons(yHeight, false);
        resize( width, (int)(y+GAP+slotHeight));
    }

    private void layoutIcons(float y, boolean update){
        ArrayList<Image> icons = new ArrayList<>();

        //TODO: could make this a loop
        if(((Runestone) inv1.item()).icon_dmg || ((Runestone) inv2.item()).icon_dmg || ((Runestone) inv3.item()).icon_dmg){
            Image im_dmg = new Image(Assets.Sprites.ITEM_ICONS);
            im_dmg.frame(ItemSpriteSheet.Icons.film.get(ItemSpriteSheet.Icons.RUNESTONE_DAMAGE));
            icons.add(im_dmg);
        }
        if(((Runestone) inv1.item()).icon_dr || ((Runestone) inv2.item()).icon_dr || ((Runestone) inv3.item()).icon_dr){
            Image im_dr = new Image(Assets.Sprites.ITEM_ICONS);
            im_dr.frame(ItemSpriteSheet.Icons.film.get(ItemSpriteSheet.Icons.RUNESTONE_DMGRED));
            icons.add(im_dr);
        }
        if(((Runestone) inv1.item()).icon_evasion || ((Runestone) inv2.item()).icon_evasion || ((Runestone) inv3.item()).icon_evasion){
            Image im_evasion = new Image(Assets.Sprites.ITEM_ICONS);
            im_evasion.frame(ItemSpriteSheet.Icons.film.get(ItemSpriteSheet.Icons.RUNESTONE_EVASION));
            icons.add(im_evasion);
        }
        if(((Runestone) inv1.item()).icon_acc || ((Runestone) inv2.item()).icon_acc || ((Runestone) inv3.item()).icon_acc){
            Image im_acc = new Image(Assets.Sprites.ITEM_ICONS);
            im_acc.frame(ItemSpriteSheet.Icons.film.get(ItemSpriteSheet.Icons.RUNESTONE_ACCURACY));
            icons.add(im_acc);
        }
        if(((Runestone) inv1.item()).icon_surprise || ((Runestone) inv2.item()).icon_surprise || ((Runestone) inv3.item()).icon_surprise){
            Image im_surprise = new Image(Assets.Sprites.ITEM_ICONS);
            im_surprise.frame(ItemSpriteSheet.Icons.film.get(ItemSpriteSheet.Icons.RUNESTONE_SURPRISE));
            icons.add(im_surprise);
        }
        if(((Runestone) inv1.item()).icon_speed || ((Runestone) inv2.item()).icon_speed || ((Runestone) inv3.item()).icon_speed){
            Image im_speed = new Image(Assets.Sprites.ITEM_ICONS);
            im_speed.frame(ItemSpriteSheet.Icons.film.get(ItemSpriteSheet.Icons.RUNESTONE_SPEED));
            icons.add(im_speed);
        }

        int i = 0;
        for(Image icon : icons){
            i++;
            icon.scale.set(1.5f);
            if(i > 3){
                icon.x = width - ((i-3)*12);
                icon.y = y+icon.height()+2;
            } else {
                icon.x = width - (i*12);
                icon.y = y;
            }
            add(icon);
        }
        if(update){
            INSTANCE.remove();
            GameScene.show(new WndRing(ownerWindow, itemDisplayed));
        }
    }

    private static float layoutButtons(ArrayList<RedButton> buttons, float width, float y){
        ArrayList<RedButton> curRow = new ArrayList<>();
        float widthLeftThisRow = width;

        while( !buttons.isEmpty() ){
            RedButton btn = buttons.get(0);

            widthLeftThisRow -= btn.width();
            if (curRow.isEmpty()) {
                curRow.add(btn);
                buttons.remove(btn);
            } else {
                widthLeftThisRow -= 1;
                if (widthLeftThisRow >= 0) {
                    curRow.add(btn);
                    buttons.remove(btn);
                }
            }

            //layout current row. Currently forces a max of 3 buttons but can work with more
            if (buttons.isEmpty() || widthLeftThisRow <= 0 || curRow.size() >= 3){

                //re-use this variable for laying out the buttons
                widthLeftThisRow = width - (curRow.size()-1);
                for (RedButton b : curRow){
                    widthLeftThisRow -= b.width();
                }

                //while we still have space in this row, find the shortest button(s) and extend them
                while (widthLeftThisRow > 0){

                    ArrayList<RedButton> shortest = new ArrayList<>();
                    RedButton secondShortest = null;

                    for (RedButton b : curRow) {
                        if (shortest.isEmpty()) {
                            shortest.add(b);
                        } else {
                            if (b.width() < shortest.get(0).width()) {
                                secondShortest = shortest.get(0);
                                shortest.clear();
                                shortest.add(b);
                            } else if (b.width() == shortest.get(0).width()) {
                                shortest.add(b);
                            } else if (secondShortest == null || secondShortest.width() > b.width()){
                                secondShortest = b;
                            }
                        }
                    }

                    float widthToGrow;

                    if (secondShortest == null){
                        widthToGrow = widthLeftThisRow / shortest.size();
                        widthLeftThisRow = 0;
                    } else {
                        widthToGrow = secondShortest.width() - shortest.get(0).width();
                        if ((widthToGrow * shortest.size()) >= widthLeftThisRow){
                            widthToGrow = widthLeftThisRow / shortest.size();
                            widthLeftThisRow = 0;
                        } else {
                            widthLeftThisRow -= widthToGrow * shortest.size();
                        }
                    }

                    for (RedButton toGrow : shortest){
                        toGrow.setRect(0, 0, toGrow.width()+widthToGrow, toGrow.height());
                    }
                }

                //finally set positions
                float x = 0;
                for (RedButton b : curRow){
                    b.setRect(x, y, b.width(), b.height());
                    x += b.width() + 1;
                }

                //move to next line and reset variables
                y += BUTTON_HEIGHT+1;
                widthLeftThisRow = width;
                curRow.clear();

            }

        }

        return y - 1;
    }

}
