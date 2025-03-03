package top.srcres258.tutorialmod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.item.ModItems;
import top.srcres258.tutorialmod.item.custom.HammerItem;
import top.srcres258.tutorialmod.potion.ModPotions;
import top.srcres258.tutorialmod.villager.ModVillagers;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        var player = event.getPlayer();
        var mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof HammerItem hammer
                && player instanceof ServerPlayer serverPlayer) {
            var initialBlockPos = event.getPos();

            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for (var pos : HammerItem.getBlockToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Sheep sheep
                && event.getSource().getDirectEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit a sheep with an END ROD? YOU SICK FRICK!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 6));
                player.getMainHandItem().shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        var trades = event.getTrades();

        if (event.getType() == VillagerProfession.FARMER) {
            // Level 1
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ModItems.GOJI_BERRIES.get(), 18),
                    6, 3, 0.05F
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND, 12),
                    new ItemStack(ModItems.RADISH.get(), 1),
                    6, 3, 0.05F
            ));

            // Level 2
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.ENDER_PEARL, 1),
                    new ItemStack(ModItems.RADISH_SEEDS.get(), 1),
                    2, 8, 0.05F
            ));
        } else if (event.getType() == ModVillagers.KAUPENGER.value()) {
            // Level 1
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ModItems.RAW_BISMUTH.get(), 18),
                    6, 3, 0.05F
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND, 16),
                    new ItemStack(ModItems.RADIATION_STAFF.get(), 1),
                    6, 3, 0.05F
            ));

            // Level 2
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.ENDER_PEARL, 2),
                    new ItemStack(ModItems.BISMUTH_SWORD.get(), 1),
                    2, 8, 0.05F
            ));
        }
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event) {
        var genericTrades = event.getGenericTrades();
        var rareTrades = event.getRareTrades();

        genericTrades.add((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 16),
                new ItemStack(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), 1),
                1, 10, 0.2F
        ));

        rareTrades.add((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.NETHERITE_INGOT, 1),
                new ItemStack(ModItems.BAR_BRAWL_MUSIC_DISC.get(), 1),
                1, 10, 0.2F
        ));
    }
}
