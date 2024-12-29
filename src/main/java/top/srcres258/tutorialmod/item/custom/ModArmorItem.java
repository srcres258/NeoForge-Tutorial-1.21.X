package top.srcres258.tutorialmod.item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.srcres258.tutorialmod.item.ModArmorMaterials;

import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            ImmutableMap.<Holder<ArmorMaterial>, List<MobEffectInstance>>builder()
                    .put(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, List.of(
                            new MobEffectInstance(MobEffects.JUMP, 200, 1, false, false),
                            new MobEffectInstance(MobEffects.GLOWING, 200, 1, false, false)
                    ))
                    .build();

    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player
                && !level.isClientSide()
                && hasFullSuitOfArmorOn(player)) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (var entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            var mapArmorMaterial = entry.getKey();
            var mapEffect = entry.getValue();

            if (hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> mapEffect) {
        var hasPlayerEffect = mapEffect.stream()
                .allMatch(effect -> player.hasEffect(effect.getEffect()));

        if (!hasPlayerEffect) {
            for (var effect : mapEffect) {
                player.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier(),
                        effect.isAmbient(), effect.isVisible()));
            }
        }
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> mapArmorMaterial, Player player) {
        for (var armorStack : player.getArmorSlots()) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        var boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        var leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        var chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        var helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return boots.getMaterial() == mapArmorMaterial
                && leggings.getMaterial() == mapArmorMaterial
                && chestplate.getMaterial() == mapArmorMaterial
                && helmet.getMaterial() == mapArmorMaterial;
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        var boots = player.getInventory().getArmor(0);
        var leggings = player.getInventory().getArmor(1);
        var chestplate = player.getInventory().getArmor(2);
        var helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty()
                && !leggings.isEmpty()
                && !chestplate.isEmpty()
                && !helmet.isEmpty();
    }
}
