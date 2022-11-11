package me.zombie_striker.qualitycustomitems;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public final class QualityCustomItemsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            QCI.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                QCI.delayedInit();
            }
        }.runTaskLater(this,1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
