package handlers.effecthandlers;

import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.effects.EffectTemplate;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.effects.L2EffectType;
import l2r.gameserver.model.stats.Env;

public class NevitHourglass extends L2Effect
{
	public NevitHourglass(Env env, EffectTemplate template)
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
		if (!(getEffected() instanceof L2PcInstance))
		{
			return false;
		}
		
		((L2PcInstance) getEffected()).startHourglassEffect();
		return true;
	}
	
	@Override
	public void onExit()
	{
		((L2PcInstance) getEffected()).stopHourglassEffect();
	}
}