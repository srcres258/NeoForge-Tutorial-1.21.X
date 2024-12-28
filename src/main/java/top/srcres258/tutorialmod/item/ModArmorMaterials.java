package top.srcres258.tutorialmod.item;

import com.google.common.base.Suppliers;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import top.srcres258.tutorialmod.TutorialMod;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> BISMUTH_ARMOR_MATERIAL = register("bismuth",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attr -> {
                attr.put(ArmorItem.Type.BOOTS, 5);
                attr.put(ArmorItem.Type.LEGGINGS, 7);
                attr.put(ArmorItem.Type.CHESTPLATE, 9);
                attr.put(ArmorItem.Type.HELMET, 5);
                attr.put(ArmorItem.Type.BODY, 11);
            }), 16, 2F, 0.1F, ModItems.BISMUTH);

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> typeProtection,
            int enchantability,
            float toughness,
            float knockbackResistance,
            Supplier<Item> ingredientItem
    ) {
        var location = ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, name);
        var equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
        var ingredient = Suppliers.ofInstance(Ingredient.of(ingredientItem.get()));
        var layers = List.of(new ArmorMaterial.Layer(location));

        var typeMap = new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class);
        for (var type : ArmorItem.Type.values()) {
            typeMap.put(type, typeProtection.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness,
                        knockbackResistance));
    }
}
