package com.addshore.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ChallengeMod extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Addshore ChallengeMod has been enabled!");
        getServer().getPluginManager().registerEvents(new BlockSwapListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Addshore ChallengeMod has been disabled!");
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
