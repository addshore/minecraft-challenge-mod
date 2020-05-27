package com.addshore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Test1 extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Test1 has been enabled!");
        getServer().getPluginManager().registerEvents(new Test1Listener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Test1 has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("test1")) {
            getLogger().info("Test1 command run!");
            return true;
        }
        return false;
    }

}
