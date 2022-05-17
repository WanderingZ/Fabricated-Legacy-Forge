package fr.catcore.fabricatedforge.mixin.forgefml.server.dedicated.gui;

import fr.catcore.fabricatedforge.mixininterface.IMinecraftServer;
import net.minecraft.network.OutboundConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.gui.PlayerStatsGui;
import net.minecraft.server.world.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.swing.*;
import java.text.DecimalFormat;

@Mixin(PlayerStatsGui.class)
public abstract class PlayerStatsGuiMixin extends JComponent {

    @Shadow private String[] lines;

    @Shadow @Final private static DecimalFormat AVG_TICK_FORMAT;

    @Shadow protected abstract double average(long[] ls);

    @Shadow @Final private MinecraftServer server;

    @Shadow private int[] memoryUsePercentage;

    @Shadow private int field_2763;

    /**
     * @author Minecraft Forge
     * @reason none
     */
    @Overwrite
    private void method_2086() {
        this.lines = new String[5 + DimensionManager.getIDs().length];
        long var1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        this.lines[0] = "Memory use: " + var1 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.lines[1] = "Threads: " + OutboundConnection.READ_THREAD_COUNT.get() + " + " + OutboundConnection.WRITE_THREAD_COUNT.get();
        this.lines[2] = "Avg tick: " + AVG_TICK_FORMAT.format(this.average(this.server.lastTickLengths) * 1.0E-6) + " ms";
        this.lines[3] = "Avg sent: " + (int)this.average(this.server.field_3853) + ", Avg size: " + (int)this.average(this.server.field_3854);
        this.lines[4] = "Avg rec: " + (int)this.average(this.server.field_3855) + ", Avg size: " + (int)this.average(this.server.field_3856);
        if (this.server.worlds != null) {
            int x = 0;
            Integer[] arr$ = DimensionManager.getIDs();
            int len$ = arr$.length;

            for (Integer id : arr$) {
                this.lines[5 + x] = "Lvl " + id + " tick: " + AVG_TICK_FORMAT.format(this.average((long[]) ((IMinecraftServer)this.server).getWorldTickTimes().get(id)) * 1.0E-6) + " ms";
                ServerWorld world = DimensionManager.getWorld(id);
                if (world != null && world.chunkCache != null) {
                    this.lines[5 + x] = this.lines[5 + x] + ", " + world.chunkCache.getChunkProviderName();
                }

                ++x;
            }
        }

        this.memoryUsePercentage[this.field_2763++ & 255] = (int)(this.average(this.server.field_3854) * 100.0 / 12500.0);
        this.repaint();
    }
}
