package fr0zert.timeoutfix.mixin;

import com.mojang.logging.LogUtils;
import fr0zert.timeoutfix.ConfigLoader;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLoginNetworkHandler.class)
public abstract class ServerLoginNetworkHandlerMixin {
    @Shadow private int loginTicks;
    @Shadow static final Logger LOGGER = LogUtils.getLogger();

    @Shadow public abstract void disconnect(Text reason);

    // to be sure
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 600))
    private int modifyTimeout(int defaultValue) {
        Long loginTimeout = ConfigLoader.INSTANCE.config.getLong("LoginTimeOut");
        return loginTimeout != null ? Math.toIntExact(loginTimeout) : defaultValue;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        Long loginTimeout = ConfigLoader.INSTANCE.config.getLong("LoginTimeOut");
        int loginTimeOutInt = loginTimeout != null ? Math.toIntExact(loginTimeout) : 600;
        if (loginTicks >= loginTimeOutInt) {
            this.disconnect(Text.translatable("multiplayer.disconnect.slow_login"));
            ci.cancel();
        }
        LOGGER.debug("Login ticks: {}", this.loginTicks);
    }

}