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
package handlers.effecthandlers;

import l2r.gameserver.enums.CtrlEvent;
import l2r.gameserver.model.actor.instance.L2DefenderInstance;
import l2r.gameserver.model.actor.instance.L2FortCommanderInstance;
import l2r.gameserver.model.actor.instance.L2NpcInstance;
import l2r.gameserver.model.actor.instance.L2SiegeFlagInstance;
import l2r.gameserver.model.actor.instance.L2SiegeSummonInstance;
import l2r.gameserver.model.effects.EffectFlag;
import l2r.gameserver.model.effects.EffectTemplate;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.effects.L2EffectType;
import l2r.gameserver.model.stats.Env;

/**
 * Implementation of the Fear Effect
 * @author littlecrow
 */
public class Fear extends L2Effect
{
	public Fear(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public int getEffectFlags()
	{
		return EffectFlag.FEAR.getMask();
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.FEAR;
	}
	
	@Override
	public boolean onActionTime()
	{
		getEffected().getAI().notifyEvent(CtrlEvent.EVT_AFRAID, getEffector(), false);
		return false;
	}
	
	@Override
	public boolean onStart()
	{
		if ((getEffected() instanceof L2NpcInstance) || (getEffected() instanceof L2DefenderInstance) || (getEffected() instanceof L2FortCommanderInstance) || (getEffected() instanceof L2SiegeFlagInstance) || (getEffected() instanceof L2SiegeSummonInstance))
		{
			return false;
		}
		
		getEffected().getAI().notifyEvent(CtrlEvent.EVT_AFRAID, getEffector(), true);
		return false;
	}
}
