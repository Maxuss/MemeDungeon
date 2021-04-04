package com.maxus.md;

import com.maxus.md.PlayerSaver.__PLAYERDATA__;
import org.json.simple.JSONObject;

import java.util.Base64;

public class Player extends __PLAYERDATA__ {
    public static void main(String[] args) {
        Player pl = new Player("test");
    }

    public Player(String playerName){
        super(PlayerSaver.getPlayerData(playerName));
        JSONObject data = dat;
        String ec = hashSave(data,playerName);
        String jstr = decodeHash(ec);
        System.out.println(jstr);
    }

    public static String hashSave(JSONObject dat, String filename){
        String coding = dat.toJSONString();
        return PlayerSaver.hashData(coding, filename);
    }

    public static String decodeHash(String encoded){
        byte[] bytes = Base64.getDecoder().decode(encoded);
        return new String(bytes);
    }
}
