package jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import unIDLiquid.UnidentifiedLiquid;
import unIDLiquid.vars;
import unIDLiquid.UnidentifiedLiquid.States;

public class fillVials extends Node{

	public SceneObject barrel; 
	public static int unIdVialCount = 0;

	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		if(UnidentifiedLiquid.currentState == States.FILL_VIALS &&
				28 == Inventory.getCount(vars.EMPTY_VIAL))
			return true;
		else
		{
			UnidentifiedLiquid.currentState = States.TO_BANK;
			return false;
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		while(Inventory.getCount(vars.EMPTY_VIAL)>1)
		{
			for(Item i : Inventory.getAllItems(false))
			{
				if(i.getId() == vars.EMPTY_VIAL)
				{
					i.getWidgetChild().interact("Use");
					barrel = SceneEntities.getNearest(vars.BARREL);
					if(barrel != null)
					{
						Mouse.click(barrel.getCentralPoint(),true);
						Task.sleep(300,400);
						unIdVialCount++;
	
					}
				}						
			}
		}
		if(Inventory.getCount(vars.UNID_LIQUID) == 28)
		{
			UnidentifiedLiquid.currentState = States.TO_BANK;
		}
	}

}
