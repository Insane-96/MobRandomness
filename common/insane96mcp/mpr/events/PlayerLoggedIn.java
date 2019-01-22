package insane96mcp.mpr.events;

import insane96mcp.mpr.MobsPropertiesRandomness;
import insane96mcp.mpr.json.Group;
import insane96mcp.mpr.json.Mob;
import insane96mcp.mpr.lib.Properties;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber(modid = MobsPropertiesRandomness.MOD_ID)
public class PlayerLoggedIn {

	@SubscribeEvent
	public static void EventPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (Properties.config.debug)
			event.player.sendMessage(new TextComponentTranslation(MobsPropertiesRandomness.RESOURCE_PREFIX + "reload_json"));
		boolean correctlyLoaded = Group.LoadGroups() &&	Mob.LoadJsons();
		
		if (!correctlyLoaded) {
			event.player.sendMessage(new TextComponentTranslation(MobsPropertiesRandomness.RESOURCE_PREFIX + "json_reloaded_error"));
		}
		else if (Properties.config.debug)
		event.player.sendMessage(new TextComponentTranslation(MobsPropertiesRandomness.RESOURCE_PREFIX + "json_reloaded"));
	}
}