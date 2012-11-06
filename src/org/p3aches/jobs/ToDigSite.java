package org.p3aches.jobs;

import org.p3aches.unidliquid.UnidentifiedLiquid;
import org.p3aches.unidliquid.UnidentifiedLiquid.States;
import org.p3aches.unidliquid.Vars;
import org.p3aches.utils.Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;

public class ToDigSite extends Node{

	Item pend;
	Widget tabs;
	Camera camera;
	private Tile[] unidBarrelPath = {new Tile(3342,3443,0),new Tile(3326,3432,0),new Tile(3330,3415,0)
	,new Tile(3330,3397,0),new Tile(3346,3392,0),new Tile(3356,3385,0), new Tile(3364,3379,0)};

	@Override
	public boolean activate() {
		if(UnidentifiedLiquid.currentState == States.TO_DIG_SITE)
			return true;
		return false;
	}

	@Override
	public void execute() {
		if(Bank.isOpen())
			Bank.close();
		if(Players.getLocal().getLocation().distance(Vars.T_FREM_BANKER)<10){
			tabs = new Widget(Vars.TABS);
			if(tabs != null){
				tabs.getChild(Vars.EQUP_INV).click(true);
				Task.sleep(200,400);
			}
			pend = Equipment.getItem(Vars.DIG_PEND);
			if(pend != null){
				pend.getWidgetChild().interact("Rub");
				Task.sleep(2200,2500);
			}
		}
		Util.walkPath(unidBarrelPath, false);
		Task.sleep(500,800);		
		Util.turnTo(Vars.T_BARREL, camera);
		Mouse.click(Vars.T_BARREL.getCentralPoint(),true);
		Task.sleep(500,800);
		if(Players.getLocal().getLocation().distance(Vars.T_BARREL) == 0){
			UnidentifiedLiquid.currentState = States.FILL_VIALS;
		}
	}
}
