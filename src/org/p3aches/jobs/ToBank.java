package org.p3aches.jobs;

import org.p3aches.unidliquid.UnidentifiedLiquid;
import org.p3aches.unidliquid.UnidentifiedLiquid.States;
import org.p3aches.unidliquid.Vars;
import org.p3aches.utils.Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;



public class ToBank extends Node{
	
	public Widget tabs;
	private Tile[] bankPath = {new Tile(3448,3697,0),new Tile(3449,3713,0)};
	public static int tripCnt = 0;

	@Override
	public boolean activate() {
		if(UnidentifiedLiquid.currentState == States.TO_BANK)
			return true;
		return false;
	}

	@Override
	public void execute() {
		tabs = new Widget(Vars.TABS);
		
		tabs.getChild(Vars.EQUP_INV).click(true);
		Task.sleep(100,200);
		Item ring = Equipment.getItem(Vars.DUNG_RING);
		if(ring != null && Players.getLocal().getLocation().distance(Vars.T_BARREL)<10){
			ring.getWidgetChild().interact("Teleport to Daemonheim");			
			while(Players.getLocal().getLocation().distance(Vars.T_DUNG)>20)
				Task.sleep(400,500);
			tripCnt++;
		}
		else if(Players.getLocal().getLocation().distance(Vars.T_DUNG)<20){
			Util.walkPath(bankPath, false);
			Task.sleep(500,700);
			UnidentifiedLiquid.currentState = States.BANK;

		}

	}

}
