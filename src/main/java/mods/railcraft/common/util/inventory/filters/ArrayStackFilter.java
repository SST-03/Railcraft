/*
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info This code is the property of CovertJaguar and may only be used
 * with explicit written permission unless otherwise specified on the license page at
 * http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.common.util.inventory.filters;

import net.minecraft.item.ItemStack;

import mods.railcraft.api.core.items.IStackFilter;
import mods.railcraft.common.util.inventory.InvTools;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class ArrayStackFilter implements IStackFilter {

    private final ItemStack[] stacks;
    private final boolean matchNBT;

    public ArrayStackFilter(ItemStack... stacks) {
        this(false, stacks);
    }

    public ArrayStackFilter(boolean matchNBT, ItemStack... stacks) {
        this.matchNBT = matchNBT;
        this.stacks = stacks;
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stacks.length == 0 || !hasFilter()) {
            return true;
        }

        for (ItemStack filterStack : stacks) {
            if (InvTools.isItemEqual(stack, filterStack, false, matchNBT)) {
                return true;
            }
        }

        return false;
    }

    public ItemStack[] getStacks() {
        return stacks;
    }

    public boolean hasFilter() {
        for (ItemStack filter : stacks) {
            if (filter != null) {
                return true;
            }
        }
        return false;
    }
}
