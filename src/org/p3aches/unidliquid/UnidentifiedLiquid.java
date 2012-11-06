package org.p3aches.unidliquid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.p3aches.jobs.BankItems;
import org.p3aches.jobs.FillVials;
import org.p3aches.jobs.ToBank;
import org.p3aches.jobs.ToDigSite;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;

@Manifest(authors = { "P3aches" }, name = "Unidentified Liquid" )
public class UnidentifiedLiquid extends ActiveScript implements PaintListener {

	private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;
	public static States currentState = States.BANK;
	Timer timer = new Timer(System.currentTimeMillis());

	public static enum States{
		BANK,
		TO_DIG_SITE,
		FILL_VIALS,
		TO_BANK,
		OUT_OF_SUPPLIES;
	}

	public final void provide(final Node... jobs) {
		for (final Node job : jobs) 	{
			if(!jobsCollection.contains(job)) {
				jobsCollection.add(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}

	public final void submit(final Job job) {
		getContainer().submit(job);
	}

	@Override
	public void onStart() {        		
		provide(new BankItems());
		provide(new ToDigSite());
		provide(new FillVials());
		provide(new ToBank());
		Mouse.setSpeed(Speed.FAST);
	}

	@Override
	public int loop() {
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
				if(currentState == States.OUT_OF_SUPPLIES)
				{
					System.out.println("Out Of Supplies");
					stop();
				}
			}
		}
		return Random.nextInt(10, 50);
	}

	@Override
	public void onRepaint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(7, 344, 208, 44);
		g.setColor(Color.white);

		g.drawString("Time Running "+ timer.toElapsedString(), 10, 357);	 		
		g.drawString("# Trips = "+ ToBank.tripCnt +"   Trips/hour = "+((ToBank.tripCnt*3600000)/timer.getElapsed()), 10, 372);			
		g.drawString("# of Unid Liquid = "+FillVials.unIdVialCount+ "   #/hour = "+((FillVials.unIdVialCount*3600000)/timer.getElapsed()), 10, 387);

		g.setColor(Color.black);
		g.fillRect(7, 400, 300, 44);
		g.setColor(Color.white);
		g.drawString("Equipment Needed: Dung Ring Equipped ", 10, 415);	 		
		g.drawString("            Dig Site Necklaces & Unfilled vials in Bank ", 10, 427);	 		
		g.drawString("Start either in Dig Site or Daemonheim Bank ", 10, 439);	
	}

}