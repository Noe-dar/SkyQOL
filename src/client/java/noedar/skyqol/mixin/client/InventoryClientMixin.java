package noedar.skyqol.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import noedar.skyqol.events.ScreenOpenEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class InventoryClientMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onInventory(Lnet/minecraft/network/packet/s2c/play/InventoryS2CPacket;)V", at = @At("RETURN"))
    private void onInventory(InventoryS2CPacket packet, CallbackInfo ci) {
        ScreenOpenEvent.Companion.getEVENT().invoker().onScreenOpen(client.currentScreen.getTitle(), packet.getContents());
    }
}
