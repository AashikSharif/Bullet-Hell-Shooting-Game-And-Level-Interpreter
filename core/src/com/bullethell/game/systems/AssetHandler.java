package com.bullethell.game.systems;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class AssetHandler {
    private AssetManager assetManager = new AssetManager();
    private Map<String, String> map = new HashMap<>();

    public void load(Map<String, String> assets) {
        for (Map.Entry<String, String> asset : assets.entrySet()) {
            System.out.println("Loading Asset -> " + asset.getKey());
            assetManager.load(asset.getValue(), Texture.class);
            map.put(asset.getKey(), asset.getValue());
            assetManager.finishLoading();
        }
    }

    public Texture getAssetTexture(String key) {
        String filePath = map.get(key);
        if (filePath != null && assetManager.isLoaded(filePath)) {
            return assetManager.get(filePath, Texture.class);
        } else {
            System.out.println("[ERROR] Asset not found -> " + key);
            return null;
        }
    }

    public void dispose() {
        System.out.println("Disposing Assets");
        assetManager.dispose();
    }
}
