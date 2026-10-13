package com.wayysss.noplacedelay.mixin;

import com.wayysss.noplacedelay.cfg;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class mcclmixin {

    @Shadow
    private int itemUseCooldown;

    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    public ClientWorld world;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tck(CallbackInfo c) {
        if (!cfg.on) return;
        if (this.player != null && this.world != null) {
            Item m = this.player.getMainHandStack().getItem();
            if (m != null && (m.getClass().getSimpleName().contains("ArmorItem") || m.getClass().getSimpleName().contains("ElytraItem"))) return;
            Item o = this.player.getOffHandStack().getItem();
            if (o != null && (o.getClass().getSimpleName().contains("ArmorItem") || o.getClass().getSimpleName().contains("ElytraItem"))) return;

            if (this.itemUseCooldown > 0) {
                int t = com.wayysss.noplacedelay.cfg.min + (com.wayysss.noplacedelay.cfg.max > com.wayysss.noplacedelay.cfg.min ? (int)(Math.random() * ((com.wayysss.noplacedelay.cfg.max - com.wayysss.noplacedelay.cfg.min) + 1)) : 0);
                if (this.itemUseCooldown > t) {
                    this.itemUseCooldown = t;
                }
            }
        }
    }
}


