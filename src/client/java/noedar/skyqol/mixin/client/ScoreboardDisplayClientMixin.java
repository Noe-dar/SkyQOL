package noedar.skyqol.mixin.client;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import noedar.skyqol.mixins.ScoreboardDisplayHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ScoreboardDisplayClientMixin {
    @Shadow private CommandDispatcher<CommandSource> commandDispatcher;

    @Inject(method = "onScoreboardObjectiveUpdate(Lnet/minecraft/network/packet/s2c/play/ScoreboardObjectiveUpdateS2CPacket;)V", at = @At("RETURN"))
    private void onScoreboardDisplay(ScoreboardObjectiveUpdateS2CPacket packet, CallbackInfo ci) {
        if(packet.getDisplayName().getString().equals("SKYBLOCK") && !ScoreboardDisplayHook.INSTANCE.isRegistered()) {
            ScoreboardDisplayHook.INSTANCE.registerCommands(commandDispatcher);
        }
    }
}
