package ai.zone.LairOfAntharas;

import l2r.gameserver.model.actor.L2Npc;
import ai.npc.AbstractNpcAI;

/**
 * @author L2jSunrise Team
 * @Website www.l2jsunrise.com
 */
public class BridgeRunners extends AbstractNpcAI
{
	private static final int RUNNERS[] =
	{
		22848,
		22849,
		22850,
		22851,
		22857
	};
	
	public BridgeRunners()
	{
		super(BridgeRunners.class.getSimpleName(), "ai/zone/LairOfAntharas");
		addSpawnId(RUNNERS);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setIsRunner(true);
		return super.onSpawn(npc);
	}
}