package top.srcres258.tutorialmod.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import top.srcres258.tutorialmod.entity.GeckoVariant;
import top.srcres258.tutorialmod.entity.ModEntities;
import top.srcres258.tutorialmod.item.ModItems;

public class GeckoEntity extends Animal {
    public static final AnimationState idleAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.INT);

    private int idleAnimationTimeout = 0;

    public GeckoEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));

        goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25,
                stack -> stack.is(ModItems.GOJI_BERRIES), false));

        goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.GOJI_BERRIES);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        var variant = Util.getRandom(GeckoVariant.values(), random);
        var baby = ModEntities.GECKO.get().create(level);
        baby.setVariant(variant);
        return baby;
    }

    private void setupAnimationStates() {
        if (idleAnimationTimeout <= 0) {
            idleAnimationTimeout = 80;
            idleAnimationState.start(tickCount);
        } else {
            idleAnimationTimeout--;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    private int getTypeVariant() {
        return entityData.get(VARIANT);
    }

    public GeckoVariant getVariant() {
        return GeckoVariant.byId(getTypeVariant() & 0xFF);
    }

    private void setVariant(GeckoVariant variant) {
        entityData.set(VARIANT, variant.getId() & 0xFF);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(VARIANT, compound.getInt("Variant"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            MobSpawnType spawnType,
            @Nullable SpawnGroupData spawnGroupData
    ) {
        var variant = Util.getRandom(GeckoVariant.values(), random);
        setVariant(variant);

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}
