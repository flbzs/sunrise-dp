package handlers.effecthandlers;

import l2r.gameserver.GeoData;
import l2r.gameserver.enums.CtrlIntention;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.effects.EffectTemplate;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.effects.L2EffectType;
import l2r.gameserver.model.stats.Env;
import l2r.gameserver.network.serverpackets.FlyToLocation;
import l2r.gameserver.network.serverpackets.FlyToLocation.FlyType;
import l2r.gameserver.network.serverpackets.ValidateLocation;
import l2r.gameserver.util.Util;

public class TeleportToTarget extends L2Effect
{
	public TeleportToTarget(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.TELEPORT_TO_TARGET;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character activeChar = getEffector();
		L2Character target = getEffected();
		if (target == null)
		{
			return false;
		}
		
		int heading = target.getHeading();
		int sign = -1;
		
		double angle = Util.convertHeadingToDegree(-heading);
		double radian = Math.toRadians(angle) - 1.5839;
		
		final Location toLoc = new Location(target.getX() + (int) (Math.sin(radian) * (40 * (-sign))), target.getY() + (int) (Math.cos(radian) * (40 * sign)), target.getZ(), heading);
		final Location loc = GeoData.getInstance().moveCheck(activeChar.getX(), activeChar.getY(), activeChar.getZ(), toLoc.getX(), toLoc.getY(), toLoc.getZ(), activeChar.getInstanceId());
		
		activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		activeChar.broadcastPacket(new FlyToLocation(activeChar, loc.getX(), loc.getY(), loc.getZ(), FlyType.DUMMY));
		activeChar.abortAttack();
		activeChar.abortCast();
		activeChar.setXYZ(loc);
		activeChar.broadcastPacket(new ValidateLocation(activeChar));
		return true;
	}
}