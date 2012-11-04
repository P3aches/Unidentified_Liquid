package jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;

import P3aches.util;

import unIDLiquid.UnidentifiedLiquid;
import unIDLiquid.vars;
import unIDLiquid.UnidentifiedLiquid.States;

public class toBank extends Node{
	
	public Widget tabs;
	private Tile[] bankPath = {new Tile(3448,3697,0),new Tile(3449,3713,0)};
	public static int tripCnt = 0;

	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		if(UnidentifiedLiquid.currentState == States.TO_BANK)
			return true;
		return false;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		tabs = new Widget(vars.TABS);
		
		tabs.getChild(vars.EQUP_INV).click(true);
		Task.sleep(100,200);
		Item ring = Equipment.getItem(vars.DUNG_RING);
		if(ring != null && Players.getLocal().getLocation().distance(vars.T_BARREL)<10)
		{
			ring.getWidgetChild().interact("Teleport to Daemonheim");			
			while(Players.getLocal().getLocation().distance(vars.T_DUNG)>20)
				Task.sleep(400,500);
			tripCnt++;
		}
		else if(Players.getLocal().getLocation().distance(vars.T_DUNG)<20)
		{
			util.walkPath(bankPath, false);
			Task.sleep(500,700);
			UnidentifiedLiquid.currentState = States.BANK;

		}

	}

}
