package org.capitalbenefits.repair.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch(ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch(ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String name, String def) {
        return getNMSClass(name) != null ? getNMSClass(name) : getNMSClass(def.split("\\.")[0]).getDeclaredClasses()[0];
    }

    public static int getInventorySize(int max) {
        if (max <= 0)
            return 9;
        int quotient = (int) Math.ceil(max / 9.0);
        return quotient > 5 ? 54 : quotient * 9;
    }

    public static ItemStack createHead(final String owner, final String name, final List<String> lore) {
        final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(owner);
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta((ItemMeta) meta);
        return item;
    }

    public static ItemStack createItem(final Material mat, final int amt, final int durability, final String name,
                                       final List<String> lore) {
        final ItemStack item = new ItemStack(mat, amt);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if (durability != 0) {
            item.setDurability((short) durability);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static String time(final long t) {
        final long time = t - System.currentTimeMillis();
        final long days = time / 3600000L / 24L;
        final long hours = time / 3600000L;
        final long minutes = time / 60000L;
        final long seconds = time / 1000L;
        if (time < 0L) {
            return "ERROR";
        }
        if (days >= 1L) {
            return String.format("%d day(s), %d hour(s), %d min, %d sec", days, hours - days * 24L, minutes - hours * 60L, seconds - minutes * 60L);
        }
        if (hours >= 1L) {
            return String.format("%d hour(s), %d min, %d sec", hours, minutes - hours * 60L, seconds - minutes * 60L);
        }
        if (minutes >= 1L) {
            return String.format("%d min, %d sec", minutes, seconds - minutes * 60L);
        }
        return String.format("%d sec", seconds);
    }


    public static ItemStack createItem(final Material mat, final int amt, final String name, final List<String> lore) {
        return createItem(mat, amt, 0, name, lore);
    }

    public static ItemStack createItem(final Material mat, final int amt, final int durability, final String name,
                                       final String... lore) {
        final List<String> l = new ArrayList<String>();
        for (final String s : lore) {
            l.add(s);
        }
        return createItem(mat, amt, durability, name, l);
    }

    public static ItemStack createItem(final Material mat, final int amt, final String name, final String... lore) {
        return createItem(mat, amt, 0, name, lore);
    }

    public static ItemStack createItem(final Material mat, final String name, final String... lore) {
        return createItem(mat, 1, 0, name, lore);
    }

    public static void remove(final Inventory inv, final Material mat, final int amt) {
        int amount = 0;
        ItemStack[] arrayOfItemStack;
        for (int j = (arrayOfItemStack = inv.getContents()).length, i = 0; i < j; ++i) {
            final ItemStack item = arrayOfItemStack[i];
            if (item != null && item.getType() == mat) {
                amount += item.getAmount();
            }
        }
        inv.remove(mat);
        if (amount > amt) {
            inv.addItem(new ItemStack[] { new ItemStack(mat, amount - amt) });
        }
    }

    public static String caps(final String string) {
        final String[] list = string.split("_");
        String s = "";
        String[] arrayOfString1;
        for (int j = (arrayOfString1 = list).length, i = 0; i < j; ++i) {
            final String st = arrayOfString1[i];
            s = String.valueOf(s) + st.substring(0, 1).toUpperCase() + st.substring(1).toLowerCase();
        }
        return s;
    }

    public static boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(final List<String> list) {
        final List<String> colored = new ArrayList<String>();
        for (final String s : list) {
            colored.add(color(s));
        }
        return colored;
    }

    public static String[] color2(final String[] s) {
        final List<String> product = new ArrayList<String>(s.length);
        for (final String string : s) {
            product.add(color(string));
        }
        final String[] output = product.toArray(new String[product.size()]);
        return output;
    }

    @SuppressWarnings("deprecation")
    public static Material getMaterial(final String s) {
        if (Material.getMaterial(s) != null) {
            return Material.getMaterial(s);
        }
        if (isInt(s) && Material.getMaterial(Integer.parseInt(s)) != null) {
            return Material.getMaterial(Integer.parseInt(s));
        }
        if (Material.matchMaterial(s) != null) {
            return Material.matchMaterial(s);
        }
        if (Material.valueOf(s.toUpperCase()) != null) {
            return Material.valueOf(s.toUpperCase());
        }
        return null;
    }

    public static List<Integer> getBorder(final int size) {
        switch (size) {
            case 54: {
                return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51,
                        52, 53);
            }
            case 45: {
                return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
            }
            case 36: {
                return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35);
            }
            case 27: {
                return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
            }
            default: {
                return null;
            }
        }
    }

    public static int getTotalExperience(final Player player) {
        int exp = Math.round(getExpAtLevel(player.getLevel()) * player.getExp());
        for (int currentLevel = player.getLevel(); currentLevel > 0; --currentLevel, exp += getExpAtLevel(
                currentLevel)) {
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int getExpAtLevel(final int level) {
        if (level > 29) {
            return 62 + (level - 30) * 7;
        }
        if (level > 15) {
            return 17 + (level - 15) * 3;
        }
        return 17;
    }

    public static boolean isArmour(final Material m) {
        return Enchantment.PROTECTION_ENVIRONMENTAL.canEnchantItem(new ItemStack(m));
    }

    public static boolean isDiamond(final Material m) {
        return m.toString().contains("DIAMOND");
    }

    public static boolean isGold(final Material m) {
        return m.toString().contains("GOLD");
    }

    public static boolean isIron(final Material m) {
        return m.toString().contains("IRON");
    }

    public static boolean isLeather(final Material m) {
        return m.toString().contains("LEATHER");
    }

    public static boolean isChain(final Material m) {
        return m.toString().contains("CHAIN");
    }

    public static boolean isSword(final Material m) {
        return m.toString().contains("SWORD");
    }

    public static boolean isAxe(final Material m) {
        return m.toString().endsWith("_AXE");
    }

    public static boolean isPickaxe(final Material m) {
        return m.toString().contains("PICKAXE");
    }

    public static boolean isWeapon(final Material m) {
        return Enchantment.DAMAGE_ALL.canEnchantItem(new ItemStack(m));
    }

    public static boolean isTool(final Material m) {
        return Enchantment.DIG_SPEED.canEnchantItem(new ItemStack(m));
    }

    public static String getName(final EntityType e) {
        if (e.equals((Object) EntityType.PIG_ZOMBIE)) {
            return "Zombie Pigman";
        }
        if (!e.toString().contains("_")) {
            return String.valueOf(e.toString().substring(0, 1).toUpperCase()) + e.toString().substring(1).toLowerCase();
        }
        final String[] split = e.toString().split("_");
        String name = "";
        String[] arrayOfString1;
        for (int j = (arrayOfString1 = split).length, i = 0; i < j; ++i) {
            final String s = arrayOfString1[i];
            name = String.valueOf(name) + s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ";
        }
        return name.trim();
    }

    public static EntityType getEntity(String e) {
        if (e.equalsIgnoreCase("Zombie Pigman")) {
            return EntityType.PIG_ZOMBIE;
        }
        e = e.replaceAll(" ", "_");
        if (!e.contains("_")) {
            return EntityType.valueOf(e.toUpperCase());
        }
        final String[] split = e.toString().split(" ");
        String name = "";
        String[] arrayOfString1;
        for (int j = (arrayOfString1 = split).length, i = 0; i < j; ++i) {
            final String s = arrayOfString1[i];
            name = String.valueOf(name) + s.toUpperCase() + "_";
        }
        return EntityType.valueOf(name.substring(0, name.length() - 1));
    }

    public enum Pane {
        WHITE("WHITE", 0, 0), ORANGE("ORANGE", 1, 1), MAGENTA("MAGENTA", 2, 2), LIGHT_BLUE("LIGHT_BLUE", 3, 3), YELLOW(
                "YELLOW", 4, 4), LIME("LIME", 5, 5), PINK("PINK", 6, 6), GRAY("GRAY", 7, 7), LIGHT_GRAY("LIGHT_GRAY", 8,
                8), CYAN("CYAN", 9, 9), PURPLE("PURPLE", 10, 10), BLUE("BLUE", 11, 11), BROWN("BROWN", 12,
                12), GREEN("GREEN", 13, 13), RED("RED", 14, 14), BLACK("BLACK", 15, 15);

        private int value;

        private Pane(final String s, final int n, final int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}