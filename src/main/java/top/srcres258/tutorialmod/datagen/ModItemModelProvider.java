package top.srcres258.tutorialmod.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TutorialMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.BISMUTH.get());
        basicItem(ModItems.RAW_BISMUTH.get());

        basicItem(ModItems.CHISEL.get());

        basicItem(ModItems.RADISH.get());

        basicItem(ModItems.STARLIGHT_ASHES.get());

        basicItem(ModItems.FROSTFIRE_ICE.get());
    }
}
