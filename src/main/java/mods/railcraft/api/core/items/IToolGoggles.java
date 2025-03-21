package mods.railcraft.api.core.items;

import net.minecraft.item.ItemStack;

import mods.railcraft.common.items.ItemGoggles.GoggleAura;

public interface IToolGoggles {

    public GoggleAura getCurrentAura(ItemStack goggles);
}
