/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package instances.JiniaGuildHideout2;

import l2r.gameserver.instancemanager.InstanceManager;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.L2Npc;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.instancezone.InstanceWorld;
import l2r.gameserver.model.quest.Quest;
import l2r.gameserver.model.quest.QuestState;
import l2r.gameserver.network.SystemMessageId;
import quests.Q10285_MeetingSirra.Q10285_MeetingSirra;

/**
 * Jinia Guild Hideout instance zone.
 * @author Adry_85
 */
public final class JiniaGuildHideout2 extends Quest
{
	protected class JGH2World extends InstanceWorld
	{
		long storeTime = 0;
	}
	
	private static final int TEMPLATE_ID = 141;
	// NPC
	private static final int RAFFORTY = 32020;
	// Location
	private static final Location START_LOC = new Location(-23530, -8963, -5413, 0, 0);
	
	public JiniaGuildHideout2()
	{
		super(-1, JiniaGuildHideout2.class.getSimpleName(), "instances");
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final QuestState qs = talker.getQuestState(Q10285_MeetingSirra.class.getSimpleName());
		if ((qs != null) && qs.isMemoState(1))
		{
			enterInstance(talker, "JiniaGuildHideout2.xml", START_LOC);
			qs.setCond(2, true);
		}
		return super.onTalk(npc, talker);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof JGH2World))
			{
				player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId(), false);
			return 0;
		}
		// New instance
		world = new JGH2World();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(TEMPLATE_ID);
		world.setStatus(0);
		((JGH2World) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Jinia Guild Hideout started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId(), false);
		world.addAllowed(player.getObjectId());
		return world.getInstanceId();
	}
}