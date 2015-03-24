package handlers.effecthandlers;

import l2r.gameserver.enums.CtrlIntention;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.effects.EffectTemplate;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.effects.L2EffectType;
import l2r.gameserver.model.stats.Env;
import l2r.gameserver.network.serverpackets.FlyToLocation;
import l2r.gameserver.network.serverpackets.FlyToLocation.FlyType;
import l2r.gameserver.network.serverpackets.ValidateLocation;

public class TeleportToTarget extends L2Effect
{
	protected Location _flyLoc;
	
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
	public boolean onStart()
	{
		if (getEffected() == null)
		{
			return false;
		}
		
		Location flyLoc = getEffector().getFlyLocation(getEffected(), getSkill());
		if (flyLoc != null)
		{
			_flyLoc = flyLoc;
			getEffector().getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			getEffector().broadcastPacket(new FlyToLocation(getEffector(), flyLoc.getX(), flyLoc.getY(), flyLoc.getZ(), FlyType.DUMMY));
			getEffector().abortAttack();
			getEffector().abortCast();
			getEffector().setXYZ(flyLoc.getX(), flyLoc.getY(), flyLoc.getZ());
			getEffector().broadcastPacket(new ValidateLocation(getEffector()));
			getEffector().setHeading(getEffected().getHeading());
			getEffector().getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(getEffected().getX(), getEffected().getY(), getEffected().getZ(), getEffected().getHeading()));
		}
		return true;
	}
}