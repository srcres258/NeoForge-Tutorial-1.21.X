package top.srcres258.tutorialmod.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BismuthParticles extends TextureSheetParticle {
    protected BismuthParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet,
                               double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        friction = 0.8F;

        lifetime = 80;
        setSpriteFromAge(spriteSet);

        rCol = 1F;
        gCol = 1F;
        bCol = 1F;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(
                SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                double pX, double pY, double pZ,
                double pXSpeed, double pYSpeed, double pZSpeed
        ) {
            return new BismuthParticles(clientLevel, pX, pY, pZ, spriteSet, pXSpeed, pYSpeed, pZSpeed);
        }
    }
}
