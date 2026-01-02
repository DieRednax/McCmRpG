package com.redfox.mccmrpg;

import com.redfox.mccmrpg.types.CustomModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class McCmRpG {
    public static void main(String[] args) {
        try {

            new Generator(
                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests"),
                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\textures\\diamond_coin.png"),
                    "NewTestPack", "This is a new test pack",
                    "test_pack", 70, List.of(
                            new CustomModel(
                                    CustomModel.ModelType.BLOCK, "carved_pumpkin",
                                    CustomModel.ModelType.BLOCK, "christmas_hat",
                                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\models\\christmas_hat.json"),
                                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\textures\\christmas_hat.png")
                            ),
                            new CustomModel(
                                    CustomModel.ModelType.ITEM, "diamond",
                                    CustomModel.ModelType.ITEM, "diamond_biotic",
                                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\textures\\diamond_coin.png")
                            )
                    )
            ).generate();
/*
            Modifier modifier = new Modifier(
                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests"),
                    "TestPack", "test_pack",
                    new CustomModel(
                            CustomModel.ModelType.BLOCK, "carved_pumpkin",
                            CustomModel.ModelType.BLOCK, "christmas_hat",
                            Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\models\\christmas_hat.json"),
                            Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\textures\\christmas_hat.png")
                    )
            );
            modifier.remove();
            modifier.add();
*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
