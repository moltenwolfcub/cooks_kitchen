package com.moltenwolfcub.crafted_cuisine.particle;

import com.moltenwolfcub.crafted_cuisine.init.AllFluids;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.FluidState;

@Environment(value=EnvType.CLIENT)
public class CaramelDripParticle extends TextureSheetParticle {

    public CaramelDripParticle(ClientLevel clientLevel, double x, double y, double z) {
        super(clientLevel, x, y, z);
        this.setSize(0.01f, 0.01f);
        this.gravity = 0.06f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (this.removed) {
            return;
        }
        this.yd -= (double)this.gravity;
        this.move(this.xd, this.yd, this.zd);
        this.postMoveUpdate();
        if (this.removed) {
            return;
        }
        this.xd *= (double)0.98f;
        this.yd *= (double)0.98f;
        this.zd *= (double)0.98f;
        BlockPos currentPos = BlockPos.containing(this.x, this.y, this.z);
        FluidState fluidAtPos = this.level.getFluidState(currentPos);
        if (fluidAtPos.getType() == AllFluids.CARAMEL_STILL && this.y < (double)((float)currentPos.getY() + fluidAtPos.getHeight(this.level, currentPos))) {
            this.remove();
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
    }


    @Environment(value=EnvType.CLIENT)
    public static class HangProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public HangProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
            CaramelHangParticle dripParticle = new CaramelHangParticle(clientLevel, x, y, z, ParticleTypes.FALLING_WATER);
            dripParticle.setColor(0.412f, 0.224f, 0.027f);
            dripParticle.pickSprite(this.sprites);
            return dripParticle;
        }
        
    }

    @Environment(value=EnvType.CLIENT)
    public static class CaramelHangParticle extends CaramelDripParticle {
        private final ParticleOptions fallingParticle;

        public CaramelHangParticle(ClientLevel clientLevel, double x, double y, double z, ParticleOptions particleOptions) {
            super(clientLevel, x, y, z);
            this.fallingParticle = particleOptions;
            this.gravity *= 0.02f;
            this.lifetime = 40;
        }

        @Override
        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

        @Override
        protected void postMoveUpdate() {
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }
    }
    
}
