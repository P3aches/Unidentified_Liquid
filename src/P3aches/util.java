package P3aches;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class util {


	public static void turnTo(NPC npc, Camera camera)
	{
		if(!npc.isOnScreen())
		{
			camera.turnTo(npc);			
		}		
	}
	public static void turnTo(SceneObject scObj, Camera camera)
	{
		if(!scObj.isOnScreen())
		{
			camera.turnTo(scObj);			
		}		
	}
	public static void turnTo(Tile tile, Camera camera)
	{
		if(!tile.isOnScreen())
		{
			camera.turnTo(tile);			
		}		
	}
	public static boolean walkPath(Tile[] path, boolean reverse) {
        if (reverse) {
                for (int i = 0; i < path.length; i++) {
                        path[i] = path[path.length - i - 1];
                }
        }
        if (Calculations.distanceTo(path[path.length - 1]) > 4) {
                Tile n = getNext(path);
                if (n != null) {
                        n.randomize(2, 2).clickOnMap();
                        Task.sleep(600,800);
                        
                }
        }
        return false;
}

public static Tile getNext(Tile[] path) {
        boolean found = false;
        for (int a = 0; a < path.length && !found; a++) {
                if (Calculations.worldToMap(path[path.length-1-a].getX(), path[path.length-1-a].getX()) != null) {
                        found = true;
                        return path[path.length - 1 - a];
                }
        }
        return null;
}
public static String getRuntimeString(long elapsed) {
    final int days = (int) elapsed / 86400000;
    long remainder = elapsed % 86400000;
    final int hours = (int) elapsed / 3600000;
    remainder = elapsed % 3600000;
    final int minutes = (int) remainder / 60000;
    remainder = remainder % 60000;
    final int seconds = (int) remainder / 1000;

    final String dd = days < 10 ? "0" + days : Integer.toString(days);
    final String hh = hours < 10 ? "0" + hours : Integer.toString(hours);
    final String mm = minutes < 10 ? "0" + minutes : Integer.toString(minutes);
    final String ss = seconds < 10 ? "0" + seconds : Integer.toString(seconds);
    return dd + ":" + hh + ":" + mm + ":" + ss;
}

}
