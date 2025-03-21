package mods.railcraft.api.core.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mods.railcraft.common.items.ItemGoggles.GoggleAura;
import mods.railcraft.common.plugins.forge.ChatPlugin;
import mods.railcraft.common.util.misc.Game;

public interface IToolGoggles {

    public GoggleAura getCurrentAura(ItemStack goggles);

    public static void displaySwitchMessage(World world, EntityPlayer player, GoggleAura newAura) {
        if (Game.isNotHost(world)) {
            ChatPlugin.sendLocalizedChat(player, "railcraft.gui.goggles.mode", "\u00A75" + newAura);
        }
    }
}
