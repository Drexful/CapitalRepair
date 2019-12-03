package org.capitalbenefits.repair.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.capitalbenefits.repair.Main;

public class DeveloperJoin implements Listener {
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (player.getUniqueId().toString().equals("026531fa-f087-4d6a-9658-4702c7e174a1") || player.getUniqueId().toString().equals("e93039ea-aae3-4446-90b2-cf209fed6236")) {
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("§8§m--------------------------------------------------");
            player.sendMessage("§e§l [!]§f Hello §8[§cDeveloper§8]§f §nDrexful§7 (XNightProX)§e,");
            player.sendMessage("");
            player.sendMessage("§a§l [!] §a§lTHIS §f§lSERVER§a§l USES ONE OF YOUR §f§lPLUGINS§a§l...");
            player.sendMessage("§f§l   *§a§l NAME§8 »§7 " + Main.getInstance().getDescription().getName());
            player.sendMessage("§f§l   *§a§l VERSION§8 »§7 " + Main.getInstance().getDescription().getVersion());
            player.sendMessage("§f§l   *§a§l AUTHORS§8 »§7 " + Main.getInstance().getDescription().getAuthors());
            player.sendMessage("§f§l   *§a§l DESCRIPTION§8 »§7 " + Main.getInstance().getDescription().getDescription());
            player.sendMessage("§f§l   *§a§l DOMAIN§8 »§7 " + Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort());
            player.sendMessage("");
            player.sendMessage("§8§m--------------------------------------------------");
            player.sendMessage("");
        }
    }
}
