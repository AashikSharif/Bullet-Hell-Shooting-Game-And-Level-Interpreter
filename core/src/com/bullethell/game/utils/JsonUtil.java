package com.bullethell.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

public class JsonUtil {
    public String read(String jsonFile) {
        FileHandle file = Gdx.files.internal(jsonFile);
        return file.readString();
    }

    public Json parse(String jsonStr) {
        Json json = new Json();
        return json.fromJson(null, jsonStr);
    }

    public <T> T deserializeJson (String jsonFile, Class<T> tClass) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(this.read(jsonFile), tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save (Object obj, String jsonFile) {
        Json json = new Json();
        String jsonStr = json.toJson(obj);
        FileHandle file = Gdx.files.local(jsonFile);
        file.writeString(jsonStr, false);
    }
}
