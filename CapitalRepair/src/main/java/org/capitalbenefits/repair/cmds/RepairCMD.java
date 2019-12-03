package org.capitalbenefits.repair.cmds;

import com.google.common.collect.Lists;
import net.milkbowl.vault.economy.EconomyResponse;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.capitalbenefits.repair.Main;
import org.capitalbenefits.repair.utils.Util;

import java.util.Arrays;
import java.util.List;

public class RepairCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] array) {

        if (array.length == 0) {
            Main.getInstance().getMessages().getStringList("help").forEach(a -> commandSender.sendMessage(Util.color(a.replace("{version}", Main.getInstance().getDescription().getVersion()))));
            return true;
        }

        if (array.length == 1) {

            if (array[0].equalsIgnoreCase("hand")) {
                // Checks if the sender is a player.
                if (!(commandSender instanceof Player)) {
                    Main.getInstance().getMessages().getStringList("must-be-player").forEach(a -> commandSender.sendMessage(Util.color(a)));
                    return true;
                }
                Player player = (Player)commandSender;
                // Checks if player has permission to use fix hand.
                if (!player.hasPermission(Main.getInstance().getConfig().getString("permissions.repair-hand"))) {
                    Main.getInstance().getMessages().getStringList("no-permission").forEach(a -> player.sendMessage(Util.color(a)));
                    Main.getInstance().playSoundIf(player, "no-permission");
                    return true;
                }
                // Checks if the item is valid.
                if (!Main.getInstance().itemCheck(player.getItemInHand())) {
                    Main.getInstance().getMessages().getStringList("invalid-item").forEach(a -> player.sendMessage(Util.color(a)));
                    Main.getInstance().playSoundIf(player, "invalid-item");
                    return true;
                }
                // Checks if the economy section of the configuration is enabled.
                if (Main.getInstance().getConfig().getBoolean("economy.enable")) {
                    EconomyResponse economyResponse = Main.getInstance().getEconomy().withdrawPlayer(player, Main.getInstance().getConfig().getInt("economy.repair-hand"));
                    if (!economyResponse.transactionSuccess()) {
                        Main.getInstance().getMessages().getStringList("insufficient-funds").forEach(a -> player.sendMessage(Util.color(a.replace("{item}", WordUtils.capitalize(player.getItemInHand().getType().toString().toLowerCase().replace("_", " ")))).replace("{remaining}", Main.getInstance().numberDecFormatter(Main.getInstance().getConfig().getInt("economy.repair-hand") - Main.getInstance().getEconomy().getBalance(player)))));
                        Main.getInstance().playSoundIf(player, "insufficient-funds");
                        return true;
                    }
                    Main.getInstance().getMessages().getStringList("repair-hand-success").forEach(a -> player.sendMessage(Util.color(a.replace("{item}", WordUtils.capitalize(player.getItemInHand().getType().toString().toLowerCase().replace("_", " ")))).replace("{cost}", Main.getInstance().numberDecFormatter(Main.getInstance().getConfig().getInt("economy.repair-hand")))));
                    Main.getInstance().playSoundIf(player, "repair-success");
                    Main.getInstance().repairHand(player);
                    return true;
                }
                // If economy is not enabled, fix the item.
                Main.getInstance().getMessages().getStringList("repair-hand-success").forEach(a -> player.sendMessage(Util.color(a.replace("{item}", WordUtils.capitalize(player.getItemInHand().getType().toString().toLowerCase().replace("_", " ")))).replace("{cost}", Main.getInstance().numberDecFormatter(Main.getInstance().getConfig().getInt("economy.repair-hand")))));
                Main.getInstance().playSoundIf(player, "repair-success");
                Main.getInstance().repairHand(player);
                return true;
            } else {
                Main.getInstance().getMessages().getStringList("help").forEach(a -> commandSender.sendMessage(Util.color(a.replace("{version}", Main.getInstance().getDescription().getVersion()))));
            }

            if (array[0].equalsIgnoreCase("all")) {
                // Checks if the sender is a player.
                if (!(commandSender instanceof Player)) {
                    Main.getInstance().getMessages().getStringList("must-be-player").forEach(a -> commandSender.sendMessage(Util.color(a)));
                    return true;
                }
                Player player = (Player)commandSender;
                // Checks if player has permission to use fix hand.
                if (!player.hasPermission(Main.getInstance().getConfig().getString("permissions.repair-hand"))) {
                    Main.getInstance().getMessages().getStringList("no-permission").forEach(a -> player.sendMessage(Util.color(a)));
                    Main.getInstance().playSoundIf(player, "no-permission");
                    return true;
                }
                // Checks if the economy section of the configuration is enabled.
                if (Main.getInstance().getConfig().getBoolean("economy.enable")) {
                    EconomyResponse economyResponse = Main.getInstance().getEconomy().withdrawPlayer(player, Main.getInstance().getConfig().getInt("economy.repair-hand"));
                    if (!economyResponse.transactionSuccess()) {
                        Main.getInstance().getMessages().getStringList("insufficient-funds").forEach(a -> player.sendMessage(Util.color(a.replace("{item}", WordUtils.capitalize(player.getItemInHand().getType().toString().toLowerCase().replace("_", " ")))).replace("{remaining}", Main.getInstance().numberDecFormatter(Main.getInstance().getConfig().getInt("economy.repair-all") - Main.getInstance().getEconomy().getBalance(player)))));
                        Main.getInstance().playSoundIf(player, "insufficient-funds");
                        return true;
                    }
                    Main.getInstance().getMessages().getStringList("repair-all-success").forEach(a -> player.sendMessage(Util.color(a.replace("{item}", WordUtils.capitalize(player.getItemInHand().getType().toString().toLowerCase().replace("_", " ")))).replace("{cost}", Main.getInstance().numberDecFormatter(Main.getInstance().getConfig().getInt("economy.repair-all")))));
                    Main.getInstance().playSoundIf(player, "repair-success");
                    Main.getInstance().repair(player);
                    return true;
                }
                // If economy is not enabled, fix the item.
                Main.getInstance().getMessages().getStringList("repair-all-success").forEach(a -> player.sendMessage(Util.color(a.replace("{item}", WordUtils.capitalize(player.getItemInHand().getType().toString().toLowerCase().replace("_", " ")))).replace("{cost}", Main.getInstance().numberDecFormatter(Main.getInstance().getConfig().getInt("economy.repair-all")))));
                Main.getInstance().playSoundIf(player, "repair-success");
                Main.getInstance().repair(player);
                return true;
            } else {
                Main.getInstance().getMessages().getStringList("help").forEach(a -> commandSender.sendMessage(Util.color(a.replace("{version}", Main.getInstance().getDescription().getVersion()))));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args){
        List<String> arguments = Arrays.asList("hand", "all", "reload");
        List<String> fList = Lists.newArrayList();
        if (args.length == 1) {
            for(String s : arguments) {
                if(s.toLowerCase().startsWith(args[0].toLowerCase()))fList.add(s);
            }
            return fList;
        }
        return null;
    }
}
