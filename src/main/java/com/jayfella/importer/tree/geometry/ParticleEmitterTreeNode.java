package com.jayfella.importer.tree.geometry;

import com.jme3.effect.ParticleEmitter;

public class ParticleEmitterTreeNode extends GeometryTreeNode {

    public ParticleEmitterTreeNode(ParticleEmitter particleEmitter) {
        super(particleEmitter);
    }

    @Override
    public ParticleEmitter getUserObject() {
        return (ParticleEmitter) super.getUserObject();
    }


}
