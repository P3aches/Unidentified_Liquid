package jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import unIDLiquid.*;
import unIDLiquid.UnidentifiedLiquid.States;

public class bank extends Node{

	Tile tFeremBank;
	NPC fermBanker;
	public Widget bank,tabs;
	private WidgetChild neck;
	public boolean needPend;

	
	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		if(Players.getLocal().getLocation().distance(vars.T_FREM_BANKER)<10)
		{
			if(28 == Inventory.getCount(vars.EMPTY_VIAL))
				if((new  WidgetChild(new Widget(387), 12).getChildId()) != -1)
				{
					UnidentifiedLiquid.currentState = States.TO_DIG_SITE;
					return false;
				}
			return true;
		}
		else if(Players.getLocal().getLocation().distance(vars.T_BARREL)<100 
				&& UnidentifiedLiquid.currentState != States.FILL_VIALS)
			UnidentifiedLiquid.currentState = States.TO_DIG_SITE;
		return false;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		fermBanker = NPCs.getNearest("Fremennik banker");
		bank = new Widget(vars.BANK);
		if(!Bank.isOpen())
		{
			tabs = new Widget(vars.TABS);
			tabs.getChild(vars.EQUP_INV).click(true);
			neck = new WidgetChild(new Widget(387),12);
			Task.sleep(200,300);
			if (neck.getChildId() == -1)
				needPend = true;
		}
		
		
		if(fermBanker != null && !bank.getChild(2).visible())//Open Bank
		{
			fermBanker.interact("Bank");
			Task.sleep(1000,1500);			
		}
		else
		{
			//Bank inventory
			while(Inventory.getCount(vars.UNID_LIQUID)>0)
			{
				Bank.deposit(vars.UNID_LIQUID, 28);
				Task.sleep(200,300);
			}
			if(needPend)//Get New Pend if needed
			{
				Bank.withdraw(vars.DIG_PEND[0], 1);
				Task.sleep(300,500);
				Item pend = Inventory.getItem(vars.DIG_PEND);
				if(pend != null)
					pend.getWidgetChild().interact("Wear");	
				Task.sleep(300,500);
				needPend = false;
			}
			Bank.withdraw(vars.EMPTY_VIAL, 0);
			Task.sleep(300,500);
			Bank.close();	
			UnidentifiedLiquid.currentState = States.TO_DIG_SITE;
		}
		
	}

}
