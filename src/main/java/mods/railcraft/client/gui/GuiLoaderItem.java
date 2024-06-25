/*
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info This code is the property of CovertJaguar and may only be used
 * with explicit written permission unless otherwise specified on the license page at
 * http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import mods.railcraft.client.gui.buttons.GuiMultiButton;
import mods.railcraft.common.blocks.machine.gamma.TileLoaderItemBase;
import mods.railcraft.common.blocks.machine.gamma.TileLoaderItemBase.EnumRedstoneMode;
import mods.railcraft.common.blocks.machine.gamma.TileLoaderItemBase.EnumTransferMode;
import mods.railcraft.common.blocks.machine.gamma.TileLoaderItemBase.MatchNBTMode;
import mods.railcraft.common.core.RailcraftConstants;
import mods.railcraft.common.gui.buttons.ButtonTextureSet;
import mods.railcraft.common.gui.buttons.IButtonTextureSet;
import mods.railcraft.common.gui.buttons.MultiButtonController;
import mods.railcraft.common.gui.containers.ContainerItemLoader;
import mods.railcraft.common.plugins.forge.LocalizationPlugin;
import mods.railcraft.common.util.misc.Game;
import mods.railcraft.common.util.network.PacketBuilder;

public class GuiLoaderItem extends TileGui {

    private static final IButtonTextureSet MATCH_NBT = new ButtonTextureSet(32, 0, 32, 32);
    private static final IButtonTextureSet IGNORE_NBT = new ButtonTextureSet(64, 0, 32, 32);

    private static final ResourceLocation WIDGETS = new ResourceLocation(
            RailcraftConstants.GUI_TEXTURE_FOLDER + "new/widgets.png");

    private final String FILTER_LABEL = LocalizationPlugin.translate("railcraft.gui.filters");
    private final String CART_FILTER_LABEL = LocalizationPlugin.translate("railcraft.gui.filters.carts");
    private final String BUFFER_LABEL = LocalizationPlugin.translate("railcraft.gui.item.loader.buffer");
    private GuiMultiButton<EnumTransferMode> transferMode;
    private GuiMultiButton<EnumRedstoneMode> redstoneMode;
    private GuiMultiButton<MatchNBTMode> matchNBTMode;
    private final TileLoaderItemBase tile;

    public GuiLoaderItem(InventoryPlayer inv, TileLoaderItemBase tile) {
        super(
                tile,
                new ContainerItemLoader(inv, tile),
                RailcraftConstants.GUI_TEXTURE_FOLDER + "new/gui_item_loader.png");
        this.tile = tile;

        ySize = 182;
    }

    @Override
    public void initGui() {
        super.initGui();
        if (tile == null) return;
        buttonList.clear();
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        buttonList.add(
                transferMode = new GuiMultiButton<>(0, w + 62, h + 45, 52, tile.getTransferModeController().copy()));
        buttonList.add(
                redstoneMode = new GuiMultiButton<>(0, w + 62, h + 62, 52, tile.getRedstoneModeController().copy()));
        buttonList.add(matchNBTMode = new NBTButton(0, w + 8, h + 81, tile.getMatchNbtController().copy()));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GuiTools.drawCenteredString(fontRendererObj, tile.getName(), 6);
        fontRendererObj.drawString(FILTER_LABEL, 18, 16, 0x404040);
        fontRendererObj.drawString(CART_FILTER_LABEL, 75, 16, 0x404040);
        fontRendererObj.drawString(BUFFER_LABEL, 126, 16, 0x404040);
    }

    @Override
    public void onGuiClosed() {
        if (Game.isNotHost(tile.getWorld())) {
            tile.getTransferModeController().setCurrentState(transferMode.getController().getCurrentState());
            tile.getRedstoneModeController().setCurrentState(redstoneMode.getController().getCurrentState());
            tile.getMatchNbtController().setCurrentState(matchNBTMode.getController().getCurrentState());
            PacketBuilder.instance().sendGuiReturnPacket(tile);
        }
    }

    public static class NBTButton extends GuiMultiButton<MatchNBTMode> {

        public NBTButton(int id, int x, int y, MultiButtonController<? extends MatchNBTMode> control) {
            super(id, x, y, 16, control);
        }

        @Override
        protected void bindButtonTextures(Minecraft minecraft) {
            minecraft.renderEngine.bindTexture(WIDGETS);
        }

        @Override
        public void drawButton(Minecraft minecraft, int x, int y) {
            super.drawButton(minecraft, x, y);

            if (!visible) {
                return;
            }

            bindButtonTextures(minecraft);

            GL11.glPushMatrix();
            float scaleFactor = 0.5F;
            GL11.glTranslatef(xPosition, yPosition, 0);
            GL11.glScalef(scaleFactor, scaleFactor, 1);

            MatchNBTMode state = getController().getButtonState();

            if (state == MatchNBTMode.MATCH_NBT) {
                drawTexturedModalRect(
                        0,
                        0,
                        MATCH_NBT.getX(),
                        MATCH_NBT.getY(),
                        MATCH_NBT.getWidth(),
                        MATCH_NBT.getHeight());
            } else {
                drawTexturedModalRect(
                        0,
                        0,
                        IGNORE_NBT.getX(),
                        IGNORE_NBT.getY(),
                        IGNORE_NBT.getWidth(),
                        IGNORE_NBT.getHeight());
            }

            GL11.glPopMatrix();
        }
    }
}
