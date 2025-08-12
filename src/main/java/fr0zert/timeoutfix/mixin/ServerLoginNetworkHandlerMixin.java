package fr0zert.timeoutfix.mixin;

import fr0zert.timeoutfix.ConfigLoader;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerLoginNetworkHandler.class)
public abstract class ServerLoginNetworkHandlerMixin {
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 600))
    private int modifyTimeout(int defaultValue) {
        return Integer.parseInt(ConfigLoader.INSTANCE.config.getProperty("login_time_out", String.valueOf(defaultValue)));
    }
}