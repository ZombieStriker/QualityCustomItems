package me.zombie_striker.qualitycustomitems.data;

import org.bukkit.Material;

public class CustomItem {

    private final String name;
    private final Material material;
    private final int modeldata;
    private final String modelpath;

    public CustomItem(String name,Material material, int modeldata, String modelpath){
        this.name=  name;
        this.modeldata = modeldata;
        this.material = material;
        this.modelpath = modelpath;
    }


    public String getModelpath() {
        return modelpath;
    }

    public int getModeldata() {
        return modeldata;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }
}
