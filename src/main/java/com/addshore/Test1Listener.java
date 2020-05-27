package com.addshore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.Bukkit.getLogger;

/**
 * TODOs
 *  - Configurable every 1, 2, 5 blocks you move?
 *  - Configurable upper height?
 *  - Configurable player area for swap
 */
public class Test1Listener implements Listener {

    Map<String, Location> playerLastLocation = new HashMap<String, Location>();

    List<Material> materialsToIgnore = Arrays.asList(
            Material.OBSIDIAN,
            Material.NETHER_PORTAL,
            Material.END_PORTAL,
            Material.END_PORTAL_FRAME,
            Material.END_CRYSTAL,
            Material.SPAWNER
    );

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        // Get player location
        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();

        // Skip if the player is on the same block as last move event
        if(playerInSameBlockAsLastEvent(player)) {
            return;
        }
        recordNewPlayerLocation( player );

        // Find some blocks
        Block toBlock = getBlockAroundLocation( playerLoc, 10 );
        Block fromBlock = getBlockFromColumnAroundLocation( playerLoc, 10, 0, 150 );

        // Skip some types of blocks
        if(materialsToIgnore.contains(toBlock.getType()) || materialsToIgnore.contains(fromBlock.getType())) {
            getLogger().info("Skipped replacing block, as the type should be ignored..");
            return;
        }

        // Get the current materials
        Material toMaterial = toBlock.getType();
        Material fromMaterial = fromBlock.getType();

        // Do the swap
        // TODO swap more than just type? for chests etc?
        toBlock.setType(fromMaterial);
        fromBlock.setType(toMaterial);
    }

    private boolean playerInSameBlockAsLastEvent( Player player ) {
        String playerId = player.getUniqueId().toString();
        Location playerLoc = player.getLocation();
        return playerLastLocation.containsKey(playerId) &&
                playerLastLocation.get(playerId).getX() == playerLoc.getX() &&
                playerLastLocation.get(playerId).getY() == playerLoc.getY() &&
                playerLastLocation.get(playerId).getZ() == playerLoc.getZ();
    }

    private void recordNewPlayerLocation( Player player ) {
        playerLastLocation.put(player.getUniqueId().toString(), player.getLocation());
    }

    private Block getBlockAroundLocation( Location location, int eitherWayAnyAxis ) {
        return new Location(
                location.getWorld(),
                ThreadLocalRandom.current().nextInt(location.getBlockX() - eitherWayAnyAxis, location.getBlockX() + eitherWayAnyAxis),
                ThreadLocalRandom.current().nextInt(location.getBlockY() - eitherWayAnyAxis, location.getBlockY() + eitherWayAnyAxis),
                ThreadLocalRandom.current().nextInt(location.getBlockZ() - eitherWayAnyAxis, location.getBlockZ() + eitherWayAnyAxis)
        ).getBlock();
    }

    private Block getBlockFromColumnAroundLocation( Location location, int eitherWayXZ, int minY, int maxY ) {
        return new Location(
                location.getWorld(),
                ThreadLocalRandom.current().nextInt(location.getBlockX() - eitherWayXZ, location.getBlockX() + eitherWayXZ),
                ThreadLocalRandom.current().nextInt(minY, maxY),
                ThreadLocalRandom.current().nextInt(location.getBlockZ() - eitherWayXZ, location.getBlockZ() + eitherWayXZ)
        ).getBlock();
    }

}
