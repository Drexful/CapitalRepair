package org.capitalbenefits.repair;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.capitalbenefits.repair.cmds.RepairCMD;
import org.capitalbenefits.repair.files.MessagesFile;
import org.capitalbenefits.repair.listeners.DeveloperJoin;

import java.text.DecimalFormat;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private Economy economy;

    @Override
    public void onEnable() {
        this.register();
        this.setup();
        this.setupEconomy();
    }

    @Override
    public void onDisable() {}

    private void setup() {
        try {
            this.saveDefaultConfig();
            MessagesFile.getConfig().saveDefault();
        } catch (Exception e) {
            e.printStackTrace();
            this.getServer().getLogger().log(Level.SEVERE, "[»] Thank you for using the plugin!");
        }
    }

    private void register() {
        this.getServer().getPluginCommand("repair").setExecutor(new RepairCMD());
        this.getServer().getPluginCommand("repair").setTabCompleter(new RepairCMD());
        this.getServer().getPluginManager().registerEvents(new DeveloperJoin(), this);
    }

    public FileConfiguration getMessages() {
        return MessagesFile.getConfig();
    }

    public Economy getEconomy() {
        return this.economy;
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    public void playSoundIf(Player player, String sound) {
        if (this.getConfig().getBoolean("sounds." + sound + ".enable")) {
            this.playSound(player, "sounds." + sound + ".sound");
        }
    }

    public void playSound(final Player p, final String name) {
        String[] parts = this.getConfig().getString(name).split(";");
        Sound sound = this.getSoundByName(parts[0].toUpperCase());
        if (sound == null) {
            return;
        }
        float volume = (float) Double.parseDouble(parts[1]);
        float pitch = (float) Double.parseDouble(parts[2]);
        p.playSound(p.getLocation(), sound, volume, pitch);
    }

    private Sound getSoundByName(final String name) {
        Sound[] values;
        for (int length = (values = Sound.values()).length, i = 0; i < length; ++i) {
            Sound sound = values[i];
            if (sound.name().equalsIgnoreCase(name)) {
                return sound;
            }
        }
        return null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
                .getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return economy != null;
    }

    public String numberDecFormatter(double number) {
        DecimalFormat mFormatter = new DecimalFormat("###,###,###,###");
        return mFormatter.format(number);
    }

    public boolean itemCheck(ItemStack w) {
        return w.getType().getId() == 256 || w.getType().getId() == 257 || w.getType().getId() == 258 || w.getType().getId() == 259 || w.getType().getId() == 261 || w.getType().getId() == 267 || w.getType().getId() == 268 || w.getType().getId() == 269 || w.getType().getId() == 270 || w.getType().getId() == 271 || w.getType().getId() == 272 || w.getType().getId() == 273 || w.getType().getId() == 274 || w.getType().getId() == 275 || w.getType().getId() == 276 || w.getType().getId() == 277 || w.getType().getId() == 278 || w.getType().getId() == 279 || w.getType().getId() == 283 || w.getType().getId() == 284 || w.getType().getId() == 285 || w.getType().getId() == 286 || w.getType().getId() == 290 || w.getType().getId() == 291 || w.getType().getId() == 292 || w.getType().getId() == 293 || w.getType().getId() == 294 || w.getType().getId() == 298 || w.getType().getId() == 299 || w.getType().getId() == 300 || w.getType().getId() == 301 || w.getType().getId() == 302 || w.getType().getId() == 303 || w.getType().getId() == 304 || w.getType().getId() == 305 || w.getType().getId() == 306 || w.getType().getId() == 307 || w.getType().getId() == 308 || w.getType().getId() == 309 || w.getType().getId() == 310 || w.getType().getId() == 311 || w.getType().getId() == 312 || w.getType().getId() == 313 || w.getType().getId() == 314 || w.getType().getId() == 315 || w.getType().getId() == 316 || w.getType().getId() == 317 || w.getType().getId() == 346 || w.getType().getId() == 359;
    }

    public void repairHand(Player p) {
        ItemStack w = p.getItemInHand();
        if (this.itemCheck(w)) {
            p.getInventory().getItemInHand().setDurability((short)0);
        }
    }

    public void repair(Player p) {
        for (int i = 0; i <= 36; ++i) {
            try {
                ItemStack w = p.getInventory().getItem(i);
                if (!this.itemCheck(w)) continue;
                p.getInventory().getItem(i).setDurability((short)0);
            }
            catch (Exception w) {
                // empty catch block
            }
        }
    }
}
