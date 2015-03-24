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

import l2r.gameserver.model.Location;
import l2r.gameserver.model.effects.EffectTemplate;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.effects.L2EffectType;
import l2r.gameserver.model.stats.Env;
import l2r.gameserver.network.serverpackets.FlyToLocation;
import l2r.gameserver.network.serverpackets.FlyToLocation.FlyType;
import l2r.gameserver.network.serverpackets.ValidateLocation;

public class EnemyCharge extends L2Effect
{
	protected Location _flyLoc;
	
	public EnemyCharge(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BUFF;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected().isMovementDisabled())
		{
			return false;
		}
		
		Location flyLoc = getEffector().getFlyLocation(getEffected(), getSkill());
		if (flyLoc != null)
		{
			_flyLoc = flyLoc;
			getEffector().broadcastPacket(new FlyToLocation(getEffector(), flyLoc.getX(), flyLoc.getY(), flyLoc.getZ(), FlyType.CHARGE));
			getEffector().setXYZ(flyLoc.getX(), flyLoc.getY(), flyLoc.getZ());
			getEffector().broadcastPacket(new ValidateLocation(getEffector()));
		}
		return true;
	}
}
