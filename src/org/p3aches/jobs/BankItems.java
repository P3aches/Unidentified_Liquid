package org.p3aches.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import org.p3aches.unidliquid.*;
import org.p3aches.unidliquid.UnidentifiedLiquid.States;
import org.p3aches.utils.*;


public class BankItems extends Node{

	Tile tFeremBank;
	NPC fermBanker;
	public Widget bank,tabs;
	private WidgetChild neck;
	public boolean needPend;
	Camera camera;


	@Override
	public boolean activate() {
		if(Players.getLocal().getLocation().distance(Vars.T_FREM_BANKER)<10){
			if(28 == Inventory.getCount(Vars.EMPTY_VIAL))
				if((new  WidgetChild(new Widget(387), 12).getChildId()) != -1){
					UnidentifiedLiquid.currentState = States.TO_DIG_SITE;
					return false;
				}
			return true;
		}
		else if(Players.getLocal().getLocation().distance(Vars.T_BARREL)<100 
				&& UnidentifiedLiquid.currentState != States.FILL_VIALS)
			UnidentifiedLiquid.currentState = States.TO_DIG_SITE;
		return false;
	}

	@Override
	public void execute() {
		fermBanker = NPCs.getNearest("Fremennik banker");
		Util.turnTo(fermBanker, camera);
		bank = new Widget(Vars.BANK);
		if(!Bank.isOpen()){
			tabs = new Widget(Vars.TABS);
			tabs.getChild(Vars.EQUP_INV).click(true);
			neck = new WidgetChild(new Widget(387),12);
			Task.sleep(200,300);
			if (neck.getChildId() == -1)
				needPend = true;
		}
		else{
			if(!(Bank.getItemCount(Vars.DIG_PEND)>0)||!(Bank.getItemCount(Vars.EMPTY_VIAL)>0))
				UnidentifiedLiquid.currentState = States.OUT_OF_SUPPLIES;
		}

		if(fermBanker != null && !bank.getChild(2).visible()){//Open Bank
			fermBanker.interact("Bank");
			Task.sleep(1000,1500);			
		}
		else{//Bank inventory			
			while(Inventory.getCount(Vars.UNID_LIQUID)>0){
				Bank.deposit(Vars.UNID_LIQUID, 28);
				Task.sleep(200,300);
			}
			if(needPend){//Get New Pend if needed				
				Bank.withdraw(Vars.DIG_PEND[0], 1);
				Task.sleep(300,500);
				Item pend = Inventory.getItem(Vars.DIG_PEND);
				if(pend != null)
					pend.getWidgetChild().interact("Wear");	
				Task.sleep(300,500);
				needPend = false;				
			}
			Bank.withdraw(Vars.EMPTY_VIAL, 0);
			Task.sleep(300,500);
			Bank.close();	
			UnidentifiedLiquid.currentState = States.TO_DIG_SITE;
		}
	}
}
