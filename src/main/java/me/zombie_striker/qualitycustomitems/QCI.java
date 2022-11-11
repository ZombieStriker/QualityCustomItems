package me.zombie_striker.qualitycustomitems;

import me.zombie_striker.qualitycustomitems.data.CustomItem;
import me.zombie_striker.qualitycustomitems.resourcepack.ResourcepackDaemon;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class QCI {

    private static List<CustomItem> customItems = new LinkedList<>();
    private static QualityCustomItemsPlugin plugin;
    private static ResourcepackDaemon daemon;

    public static void init(QualityCustomItemsPlugin plugin) throws IOException {
        QCI.plugin = plugin;
        QCI.daemon = new ResourcepackDaemon(plugin.getConfig().getInt("port"),new File(plugin.getDataFolder(),"resourcepack.zip"));
    }
    public static void delayedInit(){
        QCI.daemon.start();
    }

    /**
     * Returns all the custom items
     * @return
     */
    public static List<CustomItem> getCustomItems() {
        return customItems;
    }

    /**
     * Returns the instance of the core plugin
     * @return
     */
    public static QualityCustomItemsPlugin getPlugin() {
        return plugin;
    }

    /**
     * Returns an instanceof of the ResourcepackDaemon
     * @return
     */
    public static ResourcepackDaemon getDaemon() {
        return daemon;
    }

    /**
     * Returns the custom item by its name
     * @param name
     * @return
     */
    public static CustomItem getItemByName(String name) {
        for (CustomItem c : customItems) {
            if (c.getName().equalsIgnoreCase(name))
                return c;
        }
        return null;
    }

    /**
     * Returns the custom item instance for the itemstack.
     * @param itemStack
     * @return
     */
    public static CustomItem getCustomItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasCustomModelData())
            return null;
        for (CustomItem c : customItems) {
            if (c.getMaterial() == itemStack.getType() && c.getModeldata() == itemStack.getItemMeta().getCustomModelData())
                return c;
        }
        return null;
    }

    /**
     * Returns wshether the item is a custom item
     * @param itemStack
     * @return
     */
    public static boolean isCustomItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasCustomModelData())
            return false;
        for (CustomItem c : customItems) {
            if (c.getMaterial() == itemStack.getType() && c.getModeldata() == itemStack.getItemMeta().getCustomModelData())
                return true;
        }
        return false;
    }

    /**
     * Registers the custom item.
     * @param name
     * @param material
     * @param custommodeldata
     * @return
     */
    public static CustomItem registerItem(String name, Material material, int custommodeldata, String modelpath) {
        CustomItem ci = new CustomItem(name, material, custommodeldata, modelpath);
        for (CustomItem c : customItems) {
            if (c.getName().equalsIgnoreCase(name)) {
                return null;
            }
            if (c.getMaterial() == material && c.getModeldata() == custommodeldata)
                return null;
        }
        customItems.add(ci);
        return ci;
    }

    /**
     * Returns the first free space for model data. Use this when first determining what
     * id you should use for registering items.
     * @param base
     * @return
     */
    public static int getFreeSpace(Material base) {
        int i = 0;
        whileloop:
        while (i >= 0) {
            for (CustomItem c : customItems) {
                if (c.getMaterial() == base) {
                    if (c.getModeldata() == i) {
                        i++;
                        continue whileloop;
                    }
                }
            }
            return i;
        }
        return -1;
    }
}
