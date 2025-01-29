package eu.ha3.presencefootsteps.events;

import eu.ha3.presencefootsteps.PFOptionsScreen;
import eu.ha3.presencefootsteps.PresenceFootsteps;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.Optional;

@EventBusSubscriber(modid = PresenceFootsteps.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ForgeEventSubscriber {
    private static final PresenceFootsteps presenceFootsteps = PresenceFootsteps.getInstance();

    @SubscribeEvent
    public static void onClientTick(final ClientTickEvent.Post event) {
        final Minecraft client = Minecraft.getInstance();
        Optional.ofNullable(client.player).filter(e -> !e.isRemoved()).ifPresent(cameraEntity -> {
            if (client.screen == null && presenceFootsteps.optionsKeyBinding.get().isDown()) {
                client.setScreen(new PFOptionsScreen(client.screen));
            }
            if (presenceFootsteps.toggleKeyBinding.get().isDown()) {
                if (!presenceFootsteps.toggleTriggered) {
                    presenceFootsteps.toggleTriggered = true;
                    presenceFootsteps.config.toggleDisabled();
                }
            } else {
                presenceFootsteps.toggleTriggered = false;
            }

            presenceFootsteps.engine.onFrame(client, cameraEntity);
        });
    }
}
