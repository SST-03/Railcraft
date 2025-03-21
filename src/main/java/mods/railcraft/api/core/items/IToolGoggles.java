package mods.railcraft.api.core.items;

import mods.railcraft.common.items.ItemGoggles.GoggleAura;
import net.minecraft.item.ItemStack;

public interface IToolGoggles {
    public GoggleAura getCurrentAura(ItemStack goggles);
}
