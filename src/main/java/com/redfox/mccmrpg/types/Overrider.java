package com.redfox.mccmrpg.types;

import java.util.List;

public class Overrider {
    private String parent;
    private List<Override> overrides;

    public Overrider(String parent, List<Override> overrides) {
        this.parent = parent;
        this.overrides = overrides;
    }

    public String getParent() { return this.parent; }
    public List<Override> getOverrides() { return this.overrides; }
}
