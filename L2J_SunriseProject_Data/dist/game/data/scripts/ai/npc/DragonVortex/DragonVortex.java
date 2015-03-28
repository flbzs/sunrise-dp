package ai.npc.DragonVortex;

import java.util.concurrent.ScheduledFuture;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.L2Npc;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import ai.npc.AbstractNpcAI;

/**
 * @author L2jSunrise Team
 * @Website www.l2jsunrise.com
 */
public class DragonVortex extends AbstractNpcAI
{
	// NPCs
	private static final int VORTEX_1 = 32871;
	private static final int VORTEX_2 = 32892;
	private static final int VORTEX_3 = 32893;
	private static final int VORTEX_4 = 32894;
	
	private static final int[] RAIDS =
	{
		25724, // Muscle Bomber
		25723, // Spike Slasher
		25722, // Shadow Summoner
		25721, // Blackdagger Wing
		25720, // Bleeding Fly
		25719, // Dust Rider
		25718, // Emerald Horn
	};
	
	// ITEMs
	private static final int LARGE_DRAGON_BONE = 17248;
	
	// MISCs
	protected ScheduledFuture<?> _despawnTask1 = null;
	protected ScheduledFuture<?> _despawnTask2 = null;
	protected ScheduledFuture<?> _despawnTask3 = null;
	protected ScheduledFuture<?> _despawnTask4 = null;
	
	protected L2Npc boss1;
	protected L2Npc boss2;
	protected L2Npc boss3;
	protected L2Npc boss4;
	
	protected int boss1ObjId = 0;
	protected int boss2ObjId = 0;
	protected int boss3ObjId = 0;
	protected int boss4ObjId = 0;
	
	private static final int DESPAWN_TIME = 60; // Despawn time in minutes
	private static final int DESPAWN_DELAY = DESPAWN_TIME * 60 * 1000; // DO NOT MODIFY THIS
	
	public DragonVortex()
	{
		super(DragonVortex.class.getSimpleName(), "ai/npc");
		addFirstTalkId(VORTEX_1, VORTEX_2, VORTEX_3, VORTEX_4);
		addStartNpc(VORTEX_1, VORTEX_2, VORTEX_3, VORTEX_4);
		addTalkId(VORTEX_1, VORTEX_2, VORTEX_3, VORTEX_4);
		addKillId(RAIDS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("Spawn"))
		{
			if (!hasQuestItems(player, LARGE_DRAGON_BONE))
			{
				return "32871-02.htm";
			}
			
			final int random = getRandom(1000);
			int raid = 0;
			if (random < 292)
			{
				raid = RAIDS[0]; // Emerald Horn 29.2%
			}
			else if (random < 516)
			{
				raid = RAIDS[1]; // Dust Rider 22.4%
			}
			else if (random < 692)
			{
				raid = RAIDS[2]; // Bleeding Fly 17.6%
			}
			else if (random < 808)
			{
				raid = RAIDS[3]; // Blackdagger Wing 11.6%
			}
			else if (random < 900)
			{
				raid = RAIDS[4]; // Spike Slasher 9.2%
			}
			else if (random < 956)
			{
				raid = RAIDS[5]; // Shadow Summoner 5.6%
			}
			else
			{
				raid = RAIDS[6]; // Muscle Bomber 4.4%
			}
			
			switch (npc.getId())
			{
				case VORTEX_1:
					if (boss1ObjId != 0)
					{
						return "32871-03.htm";
					}
					
					takeItems(player, LARGE_DRAGON_BONE, 1);
					boss1 = addSpawn(raid, new Location(player.getX() - 300, player.getY() - 100, player.getZ() - 2, player.getHeading()), false, 0, true);
					boss1ObjId = boss1.getObjectId();
					_despawnTask1 = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnVortexBoss(VORTEX_1), DESPAWN_DELAY);
					break;
				case VORTEX_2:
					if (boss2ObjId != 0)
					{
						return "32871-03.htm";
					}
					
					takeItems(player, LARGE_DRAGON_BONE, 1);
					boss2 = addSpawn(raid, new Location(player.getX() - 300, player.getY() - 100, player.getZ() - 2, player.getHeading()), false, 0, true);
					boss2ObjId = boss2.getObjectId();
					_despawnTask2 = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnVortexBoss(VORTEX_2), DESPAWN_DELAY);
					break;
				case VORTEX_3:
					if (boss3ObjId != 0)
					{
						return "32871-03.htm";
					}
					
					takeItems(player, LARGE_DRAGON_BONE, 1);
					boss3 = addSpawn(raid, new Location(player.getX() - 300, player.getY() - 100, player.getZ() - 2, player.getHeading()), false, 0, true);
					boss3ObjId = boss3.getObjectId();
					_despawnTask3 = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnVortexBoss(VORTEX_3), DESPAWN_DELAY);
					break;
				case VORTEX_4:
					if (boss4ObjId != 0)
					{
						return "32871-03.htm";
					}
					
					takeItems(player, LARGE_DRAGON_BONE, 1);
					boss4 = addSpawn(raid, new Location(player.getX() - 300, player.getY() - 100, player.getZ() - 2, player.getHeading()), false, 0, true);
					boss4ObjId = boss4.getObjectId();
					_despawnTask4 = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnVortexBoss(VORTEX_4), DESPAWN_DELAY);
					break;
			}
			
			return "32871-01.htm";
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return "32871.htm";
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		int npcObjId = npc.getObjectId();
		
		if (npcObjId == boss1ObjId)
		{
			boss1ObjId = 0;
			if (_despawnTask1 != null)
			{
				_despawnTask1.cancel(true);
				_despawnTask1 = null;
			}
		}
		else if (npcObjId == boss2ObjId)
		{
			boss2ObjId = 0;
			if (_despawnTask2 != null)
			{
				_despawnTask2.cancel(true);
				_despawnTask2 = null;
			}
		}
		else if (npcObjId == boss3ObjId)
		{
			boss3ObjId = 0;
			if (_despawnTask3 != null)
			{
				_despawnTask3.cancel(true);
				_despawnTask3 = null;
			}
		}
		else if (npcObjId == boss4ObjId)
		{
			boss4ObjId = 0;
			if (_despawnTask4 != null)
			{
				_despawnTask4.cancel(true);
				_despawnTask4 = null;
			}
		}
		
		return super.onKill(npc, player, isSummon);
	}
	
	protected class SpawnVortexBoss implements Runnable
	{
		int _vortex;
		
		protected SpawnVortexBoss(int vortex)
		{
			_vortex = vortex;
		}
		
		@Override
		public void run()
		{
			switch (_vortex)
			{
				case VORTEX_1:
					boss1.deleteMe();
					boss1ObjId = 0;
					_despawnTask1 = null;
					break;
				case VORTEX_2:
					boss2.deleteMe();
					boss2ObjId = 0;
					_despawnTask2 = null;
					break;
				case VORTEX_3:
					boss3.deleteMe();
					boss3ObjId = 0;
					_despawnTask3 = null;
					break;
				case VORTEX_4:
					boss4.deleteMe();
					boss4ObjId = 0;
					_despawnTask4 = null;
					break;
			}
		}
	}
}