package eu.kennytv.worldeditsui.listener;

import eu.kennytv.worldeditsui.Settings;
import eu.kennytv.worldeditsui.WorldEditSUIPlugin;
import eu.kennytv.worldeditsui.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class ItemHeldListener implements Listener {

    private final WorldEditSUIPlugin plugin;

    public ItemHeldListener(final WorldEditSUIPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        final User user = plugin.getUserManager().getUser(player);
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        boolean holdingAxe = newItem != null && newItem.getType() == Material.WOODEN_AXE;

        if (holdingAxe) {
            user.setSelectionShown(true);
            user.setClipboardShown(true);
        } else {
            user.setSelectionShown(false);
            user.setClipboardShown(false);
            user.clearCaches();
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Player player = event.getPlayer();
            final User user = plugin.getUserManager().getUser(player);
            ItemStack main = player.getInventory().getItemInMainHand();

            if (main.getType() == Material.WOODEN_AXE) {
                user.setSelectionShown(true);
                user.setClipboardShown(true);
            } else {
                user.setSelectionShown(false);
                user.setClipboardShown(false);
                user.clearCaches();
            }
        });
    }

}
