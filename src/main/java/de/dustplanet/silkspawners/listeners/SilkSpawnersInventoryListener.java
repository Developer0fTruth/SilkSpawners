package de.dustplanet.silkspawners.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.dustplanet.silkspawners.SilkSpawners;
import de.dustplanet.util.SilkUtil;

/**
 * To show a chat message that a player clicked on an mob spawner
 * @author (former) mushroomhostage
 * @author xGhOsTkiLLeRx
 */

public class SilkSpawnersInventoryListener implements Listener {
	private SilkSpawners plugin;
	private SilkUtil su;
	
	public SilkSpawnersInventoryListener(SilkSpawners instance, SilkUtil util) {
		plugin = instance;
		su = util;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		// Null checks, somehow errors appeared...
		if (event == null || event.getCurrentItem() == null || event.getWhoClicked() == null) return;
		// If we should notify and the item is a mobspawner and we have a player here who has the permission
		if (plugin.config.getBoolean("notifyOnClick") && event.getCurrentItem().getType().equals(Material.MOB_SPAWNER) && event.getWhoClicked() instanceof Player && plugin.hasPermission((Player) event.getWhoClicked(), "silkspawners.info")) {
			// Get the entity ID
			short entityID = su.getStoredSpawnerItemEntityID(event.getCurrentItem());
			// Pig here again
			if (entityID == 0) entityID = su.defaultEntityID;
			// Get the name of the creature
			String spawnerName = su.getCreatureName(entityID);
			// Player
			Player player = (Player) event.getWhoClicked();
			// If we use Spout & the player Spoutcraft, send a notification (achievement like)
			if (plugin.spoutEnabled && ((SpoutPlayer) player).isSpoutCraftEnabled()) {
				((SpoutPlayer) player).sendNotification("Monster Spawner", spawnerName, Material.MOB_SPAWNER);
			}
			else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('\u0026', plugin.localization.getString("informationOfSpawner1").replaceAll("%creature%", spawnerName).replaceAll("%ID%", Short.toString(entityID))));
				player.sendMessage(ChatColor.translateAlternateColorCodes('\u0026', plugin.localization.getString("informationOfSpawner2").replaceAll("%creature%", spawnerName).replaceAll("%ID%", Short.toString(entityID))));
				player.sendMessage(ChatColor.translateAlternateColorCodes('\u0026', plugin.localization.getString("informationOfSpawner3").replaceAll("%creature%", spawnerName).replaceAll("%ID%", Short.toString(entityID))));
			}
		}

	}
}