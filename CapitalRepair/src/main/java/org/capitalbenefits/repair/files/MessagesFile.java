package org.capitalbenefits.repair.files;

import org.bukkit.configuration.file.YamlConfiguration;
import org.capitalbenefits.repair.Main;

import java.io.File;

public class MessagesFile extends YamlConfiguration {

    private static MessagesFile config;
    private Main plugin;
    private File configFile;

    public static MessagesFile getConfig() {
        if (MessagesFile.config == null) {
            MessagesFile.config = new MessagesFile();
        }
        return MessagesFile.config;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MessagesFile() {
        this.plugin = (Main) Main.getPlugin((Class) Main.class);
        this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
        this.saveDefault();
        this.reload();
    }

    public void reload() {
        try {
            super.load(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void save() {
        try {
            super.save(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        this.plugin.saveResource("messages.yml", false);
    }

    @SuppressWarnings("unused")
    public void saveConfig() {
        try {
            super.save(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void reloadConfig() {
        try {
            super.load(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}