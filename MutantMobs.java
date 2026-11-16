package com.example.mutantmobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod("mutantmobs")
public class MutantMobs {
    public MutantMobs() {
    }

    @Mod.EventBusSubscriber(modid = "mutantmobs")
    public static class Events {
        private static final Random RANDOM = new Random();

        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
            Entity entity = event.getEntity();
            if (event.getWorld().isRemote() || !(entity instanceof ZombieEntity)) {
                return;
            }

            ZombieEntity zombie = (ZombieEntity) entity;
            if (zombie.getPersistentData().getBoolean("mutantmobs_changed")) {
                return;
            }
            zombie.getPersistentData().putBoolean("mutantmobs_changed", true);

            int variant = RANDOM.nextInt(3);
            double health;
            double damage;
            double speed;
            String name;

            if (variant == 0) {
                health = 100.0;
                damage = 12.0;
                speed = 0.18;
                name = "Танк-мутант";
            } else if (variant == 1) {
                health = 35.0;
                damage = 6.0;
                speed = 0.38;
                name = "Зомби-бегун";
            } else {
                health = 60.0;
                damage = 9.0;
                speed = 0.25;
                name = "Мутант";
            }

            if (zombie.getAttribute(Attributes.MAX_HEALTH) != null) {
                zombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
                zombie.setHealth((float) health);
            }
            if (zombie.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                zombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
            }
            if (zombie.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
            }

            zombie.setCustomName(new StringTextComponent(name));
            zombie.setCustomNameVisible(true);
        }
    }
}
