package fr.catcore.fabricatedforge.mixin.forgefml.client.gui.screen;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow protected abstract void renderBackground(int mouseX, int mouseY, float tickDelta);

    @Shadow private float minceraftRandomNumber;

    @Shadow private String splashText;

    @Inject(method = "init", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private void fmlAddModsButton(CallbackInfo ci) {
        int var4 = this.height / 4 + 48;
        this.buttons.add(new ButtonWidget(6, this.width / 2 + 2, var4 + 48, 98, 20, "Mods"));
    }

    @Inject(method = "buttonClicked", at = @At("RETURN"))
    private void fmlAddModsButton(ButtonWidget par1GuiButton, CallbackInfo ci) {
        if (par1GuiButton.id == 6) {
            this.field_1229.openScreen(new GuiModList(this));
        }
    }

    /**
     * @author Minecraft Forge
     * @reason none
     */
    @Overwrite
    public void render(int par1, int par2, float par3) {
        this.renderBackground(par1, par2, par3);
        Tessellator var4 = Tessellator.INSTANCE;
        short var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        byte var7 = 30;
        this.fillGradient(0, 0, this.width, this.height, -2130706433, 16777215);
        this.fillGradient(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        GL11.glBindTexture(3553, this.field_1229.field_3813.getTextureFromPath("/title/mclogo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if ((double)this.minceraftRandomNumber < 1.0E-4) {
            this.drawTexture(var6, var7, 0, 0, 99, 44);
            this.drawTexture(var6 + 99, var7, 129, 0, 27, 44);
            this.drawTexture(var6 + 99 + 26, var7, 126, 0, 3, 44);
            this.drawTexture(var6 + 99 + 26 + 3, var7, 99, 0, 26, 44);
            this.drawTexture(var6 + 155, var7, 0, 45, 155, 44);
        } else {
            this.drawTexture(var6, var7, 0, 0, 155, 44);
            this.drawTexture(var6 + 155, var7, 0, 45, 155, 44);
        }

        var4.method_1413(16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float var8 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getTime() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F);
        var8 = var8 * 100.0F / (float)(this.textRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef(var8, var8, var8);
        this.drawCenteredString(this.textRenderer, this.splashText, 0, -8, 16776960);
        GL11.glPopMatrix();
        String var9 = "Minecraft 1.3.2";
        if (this.field_1229.isDemo()) {
            var9 = var9 + " Demo";
        }

        List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings());

        for(int i = 0; i < brandings.size(); ++i) {
            String brd = brandings.get(i);
            if (!Strings.isNullOrEmpty(brd)) {
                this.drawWithShadow(this.textRenderer, brd, 2, this.height - (10 + i * (this.textRenderer.fontHeight + 1)), 16777215);
            }
        }

        String var10 = "Copyright Mojang AB. Do not distribute!";
        this.drawWithShadow(this.textRenderer, var10, this.width - this.textRenderer.getStringWidth(var10) - 2, this.height - 10, 16777215);
        super.render(par1, par2, par3);
    }
}
