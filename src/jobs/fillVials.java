package jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import unIDLiquid.UnidentifiedLiquid;
import unIDLiquid.Vars;
import unIDLiquid.UnidentifiedLiquid.States;

public class FillVials extends Node{

	public SceneObject barrel; 
	public static long unIdVialCount = 0;

	@Override
	public boolean activate() {
		if(UnidentifiedLiquid.currentState == States.FILL_VIALS &&
				28 == Inventory.getCount(Vars.EMPTY_VIAL))
			return true;
		else{
			UnidentifiedLiquid.currentState = States.TO_BANK;
			return false;
		}
	}

	@Override
	public void execute() {
		while(Inventory.getCount(Vars.EMPTY_VIAL)>1){
			for(Item i : Inventory.getAllItems(false)){
				if(i.getId() == Vars.EMPTY_VIAL){
					i.getWidgetChild().interact("Use");
					barrel = SceneEntities.getNearest(Vars.BARREL);
					if(barrel != null){
						Mouse.click(barrel.getCentralPoint(),true);
						Task.sleep(300,400);
						unIdVialCount++;	
					}
				}						
			}
		}
		if(Inventory.getCount(Vars.UNID_LIQUID) == 28){
			UnidentifiedLiquid.currentState = States.TO_BANK;
		}
	}
}
