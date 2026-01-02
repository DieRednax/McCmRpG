package com.redfox.mccmrpg.types;

import java.io.IOException;
import java.lang.Override;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomModel {
    public enum ModelType {
        ITEM("item"),
        BLOCK("block");

        private final String dirName;

        ModelType(String dirName) {
            this.dirName = dirName;
        }

        @Override
        public String toString() {
            return this.dirName;
        }
    }

    private ModelType type;
    private String overrider;
    private ModelType overriderType;
    private String identifier;
    private Path modelPath;
    private Path texturePath;

    public CustomModel(ModelType type, String overrider, ModelType overriderType, String identifier, Path modelPath, Path texturePath) {
        this.type = type;
        this.overrider = overrider;
        this.overriderType = overriderType;
        this.identifier = identifier;
        this.modelPath = modelPath;
        this.texturePath = texturePath;
    }
    public CustomModel(ModelType type, String overrider, ModelType overriderType, String identifier, Path texturePath) throws IOException {
        if (type != ModelType.ITEM) {
            throw new IllegalArgumentException("No model path found.");
        }

        this.type = type;
        this.overrider = overrider;
        this.overriderType = overriderType;
        this.identifier = identifier;
        this.texturePath = texturePath;

        Path modelPath = Files.createTempFile("redfox.mccmrpg.custom_model.model." + identifier, ".json");
        Files.writeString(modelPath, String.format("""
                {
                    "parent": "minecraft:item/generated",
                    "textures": {
                        "layer0": "item/custom/%s"
                    }
                }
                """, identifier));
        modelPath.toFile().deleteOnExit();
        this.modelPath = modelPath;

        System.out.println(modelPath);
    }

    public ModelType getType() { return this.type; }
    public String getOverrider() { return this.overrider; }
    public ModelType getOverriderType() { return this.overriderType; }
    public String getIdentifier() { return this.identifier; }
    public Path getModelPath() { return this.modelPath; }
    public Path getTexturePath() { return this.texturePath; }
}
