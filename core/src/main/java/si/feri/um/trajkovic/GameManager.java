package si.feri.um.trajkovic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameManager {
    private static final String PREFS_NAME = "game_settings";
    private static final String GRID_SIZE_KEY = "grid_size";
    private static final String MODE_KEY = "game_mode";
    private static final String LEADERBOARD_FILE = "assets/leaderboard.json";

    private Preferences prefs;
    private Map<String, Integer> leaderboardData;

    public GameManager() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
        leaderboardData = new HashMap<>();
        loadLeaderboardJson();
    }

    public void saveGridSize(int gridSize) {
        prefs.putInteger(GRID_SIZE_KEY, gridSize);
        prefs.flush();
    }

    public int loadGridSize() {
        return prefs.getInteger(GRID_SIZE_KEY, 8);
    }

    public void saveGameMode(String mode) {
        prefs.putString(MODE_KEY, mode);
        prefs.flush();
    }

    public String loadGameMode() {
        return prefs.getString(MODE_KEY, "Singleplayer");
    }

    public void saveLeaderboardEntry(String playerName, int score) {
        //loadLeaderboardJson();
        leaderboardData.put(playerName, score);
        saveLeaderboard();
    }

    public void saveLeaderboard() {
        Json json = new Json();
        String jsonString = json.toJson(leaderboardData);

        FileHandle file = Gdx.files.local(LEADERBOARD_FILE);
        file.writeString(jsonString, false);
    }

    public void loadLeaderboardJson() {
        FileHandle file = Gdx.files.local(LEADERBOARD_FILE);

        if (file.exists()) {
            Json json = new Json();
            String jsonString = file.readString();

            leaderboardData = json.fromJson(LinkedHashMap.class, jsonString);
        } else {
            //leaderboardData = loadLeaderboard();
            //saveLeaderboard();
            System.out.println("JSON doesn't exist");
        }
    }

    public Map<String, Integer> getLeaderboard() {
        //loadLeaderboardJson();
        return leaderboardData;
    }
}
