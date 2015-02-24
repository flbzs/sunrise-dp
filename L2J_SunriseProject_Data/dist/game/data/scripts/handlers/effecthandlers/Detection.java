/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package handlers.effecthandlers;

import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.effects.EffectTemplate;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.effects.L2EffectType;
import l2r.gameserver.model.stats.Env;

/**
 * Detection effect implementation.
 * @author UnAfraid
 */
public final class Detection extends L2Effect
{
	public Detection(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		if (!getEffector().isPlayer() || !getEffected().isPlayer())
		{
			return false;
		}
		
		final L2PcInstance player = getEffector().getActingPlayer();
		final L2PcInstance target = getEffected().getActingPlayer();
		final boolean hasParty = player.isInParty();
		final boolean hasClan = player.getClanId() > 0;
		final boolean hasAlly = player.getAllyId() > 0;
		
		if (target.isInvisible())
		{
			if (hasParty && (target.isInParty()) && (player.getParty().getLeaderObjectId() == target.getParty().getLeaderObjectId()))
			{
				return false;
			}
			else if (hasClan && (player.getClanId() == target.getClanId()))
			{
				return false;
			}
			else if (hasAlly && (player.getAllyId() == target.getAllyId()))
			{
				return false;
			}
			
			// Remove Hide.
			L2Effect eHide = target.getFirstEffect(L2EffectType.HIDE);
			if (eHide != null)
			{
				eHide.exit();
			}
		}
		return true;
	}
}
