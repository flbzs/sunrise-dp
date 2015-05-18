package handlers.admincommandhandlers;

import l2r.Config;
import l2r.gameserver.GeoData;
import l2r.gameserver.handler.IAdminCommandHandler;
import l2r.gameserver.model.actor.instance.L2PcInstance;

public class AdminGeodata implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_geo_z",
		"admin_geo_type",
		"admin_geo_nswe",
		"admin_geo_los",
		"admin_geo_position"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (!Config.GEODATA)
		{
			activeChar.sendMessage("Geo Engine is Turned Off!");
			return true;
		}
		
		if (command.equals("admin_geo_z"))
		{
			activeChar.sendMessage("GeoEngine: Geo_Z = " + GeoData.getInstance().getHeight(activeChar.getX(), activeChar.getY(), activeChar.getZ()) + " Loc_Z = " + activeChar.getZ());
		}
		else if (command.equals("admin_geo_type"))
		{
			short type = GeoData.getInstance().getType(activeChar.getX(), activeChar.getY());
			activeChar.sendMessage("GeoEngine: Geo_Type = " + type);
			int height = GeoData.getInstance().getHeight(activeChar.getX(), activeChar.getY(), activeChar.getZ());
			activeChar.sendMessage("GeoEngine: height = " + height);
		}
		else if (command.equals("admin_geo_nswe"))
		{
			String result = "";
			short nswe = GeoData.getInstance().getNSWE(activeChar.getX(), activeChar.getY(), activeChar.getZ());
			if ((nswe & 8) == 0)
			{
				result += " N";
			}
			if ((nswe & 4) == 0)
			{
				result += " S";
			}
			if ((nswe & 2) == 0)
			{
				result += " W";
			}
			if ((nswe & 1) == 0)
			{
				result += " E";
			}
			activeChar.sendMessage("GeoEngine: Geo_NSWE -> " + nswe + "->" + result);
		}
		else if (command.equals("admin_geo_los"))
		{
			if (activeChar.getTarget() != null)
			{
				if (GeoData.getInstance().canSeeTarget(activeChar, activeChar.getTarget()))
				{
					activeChar.sendMessage("GeoEngine: Can See Target");
				}
				else
				{
					activeChar.sendMessage("GeoEngine: Can't See Target");
				}
				
			}
			else
			{
				activeChar.sendMessage("None Target!");
			}
		}
		else if (command.equals("admin_geo_position"))
		{
			activeChar.sendMessage("GeoEngine: Your current position: ");
			activeChar.sendMessage(".... world coords: x: " + activeChar.getX() + " y: " + activeChar.getY() + " z: " + activeChar.getZ());
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}