package com.redfox.mccmrpg;

import com.redfox.mccmrpg.types.CustomModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Generator {
    private Path generatePath;
    private Path iconPath = null;
    private String description;
    private String nameSpace;
    private int format;
    List<CustomModel> models;

    public Generator(Path generatePath, String description, String namespace, List<CustomModel> models) {
        this.generatePath = generatePath;
        this.description = description;
        this.nameSpace = namespace;
        this.format = 46;
        this.models = models;
    }
    public Generator(Path generatePath, String description, String namespace, int format, List<CustomModel> models) {
        this.generatePath = generatePath;
        this.description = description;
        this.nameSpace = namespace;
        this.format = format;
        this.models = models;
    }
    public Generator(Path generatePath, String name, String description, String namespace, List<CustomModel> models) {
        this.generatePath = generatePath.resolve(name);
        this.description = description;
        this.nameSpace = namespace;
        this.format = 46;
        this.models = models;
    }
    public Generator(Path generatePath, String name, String description, String namespace, int format, List<CustomModel> models) {
        this.generatePath = generatePath.resolve(name);
        this.description = description;
        this.nameSpace = namespace;
        this.format = format;
        this.models = models;
    }
    public Generator(Path generatePath, Path iconPath, String name, String description, String namespace, List<CustomModel> models) {
        this.generatePath = generatePath.resolve(name);
        this.iconPath = iconPath;
        this.description = description;
        this.nameSpace = namespace;
        this.format = 46;
        this.models = models;
    }
    public Generator(Path generatePath, Path iconPath, String name, String description, String namespace, int format, List<CustomModel> models) {
        this.generatePath = generatePath.resolve(name);
        this.iconPath = iconPath;
        this.description = description;
        this.nameSpace = namespace;
        this.format = format;
        this.models = models;
    }

    public void generate() throws IOException {
        final Path mcmeta = this.generatePath.resolve("pack.mcmeta");
        final Path icon = this.generatePath.resolve("pack.png");
        Files.createDirectories(generatePath);

        Files.writeString(mcmeta, String.format("""
                {
                    "pack": {
                        "pack_format": %d,
                        "description": %s
                    }
                }
                """, format, description));

        if (iconPath != null) {
            Files.copy(iconPath, icon);
        }

        for (CustomModel model : models) {
            new Modifier(generatePath, nameSpace, model).add();
        }
    }
}
