package tk.shanebee.survival.listeners.item;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Cauldron;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.item.Item;

public class CauldronWaterBottle implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onItemClick(PlayerInteractEvent event) {
		if (event.isCancelled()) return;
		if (event.hasItem()) {
			Player player = event.getPlayer();
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				ItemStack mainItem = player.getInventory().getItemInMainHand();
				if (mainItem.getType() == Material.GLASS_BOTTLE) {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (event.getClickedBlock().getType() == Material.CAULDRON) {
							Levelled cauldron = (Levelled) (event.getClickedBlock().getBlockData());
							if (cauldron.getLevel() > 0) {
								Block fire = event.getClickedBlock().getRelative(BlockFace.DOWN);
								event.setCancelled(true);

								event.getClickedBlock().getState().setData(new Cauldron());
								cauldron.setLevel(cauldron.getLevel() - 1);
								event.getClickedBlock().setBlockData(cauldron);

								ItemStack waterBottle = ItemManager.get(Item.DIRTY_WATER);

								if (fire.getType() == Material.FIRE) {
									waterBottle = ItemManager.get(Item.PURIFIED_WATER);
								}
								player.playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);

								if (mainItem.getAmount() > 1) {
									mainItem.setAmount(mainItem.getAmount() - 1);
									if (player.getInventory().firstEmpty() != -1)
										player.getInventory().addItem(waterBottle);
									else
										player.getWorld().dropItem(player.getLocation(), waterBottle);
								} else {
									player.getInventory().setItemInMainHand(waterBottle);
								}
							}
						}
					}
				}
			}
		}
	}

}