package eu.ha3.presencefootsteps;

import eu.ha3.presencefootsteps.sound.SoundEngine;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

@Mod(value = PresenceFootsteps.MOD_ID, dist = Dist.CLIENT)
public class PresenceFootsteps {
    public static final Logger logger = LogManager.getLogger("PFSolver");
    public static final String MOD_ID = "presencefootsteps";

    public static final Component MOD_NAME = Component.translatable("mod.presencefootsteps.name");

    public static ResourceLocation id(String  name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    private static PresenceFootsteps instance;

    public static PresenceFootsteps getInstance() {
        return instance;
    }

    public SoundEngine engine;

    public PFConfig config;

    public PFDebugHud debugHud;

    @Nullable
    public Lazy<KeyMapping> optionsKeyBinding = null;
    @Nullable
    public Lazy<KeyMapping> toggleKeyBinding = null;
    public boolean toggleTriggered;

    public PresenceFootsteps(ModContainer modContainer) {
        instance = this;

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, screen) -> new PFOptionsScreen(screen));
    }

    void onEnabledStateChange(boolean enabled) {
        engine.reload();
        showSystemToast(
                MOD_NAME,
                Component.translatable("key.presencefootsteps.toggle." + (enabled ? "enabled" : "disabled")).withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.GRAY)
        );
    }

    public void showSystemToast(Component title, Component body) {
        Minecraft client = Minecraft.getInstance();
        client.getToasts().addToast(SystemToast.multiline(client, SystemToast.SystemToastId.PACK_LOAD_FAILURE, title, body));
    }
}
