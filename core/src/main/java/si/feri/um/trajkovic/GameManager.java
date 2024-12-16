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
    private static final String LEADERBOARD_NAME = "leaderboard";
    private static final String GRID_SIZE_KEY = "grid_size";
    private static final String MODE_KEY = "game_mode";
    private static final String LEADERBOARD_FILE = "assets/leaderboard.json";

    private Preferences prefs;
    private Preferences leaderboard;
    private Map<String, Integer> leaderboardData;

    public GameManager() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
        leaderboard = Gdx.app.getPreferences(LEADERBOARD_NAME);
        leaderboardData = new HashMap<>();
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
        leaderboard.putString(playerName, String.valueOf(score));
        leaderboard.flush();
        leaderboardData.put(playerName, score);
    }

    public Map<String, Integer> loadLeaderboard() {
        Map<String, Integer> leaderboardEntries = new HashMap<>();
        for (String key : leaderboard.get().keySet()) {
            leaderboardEntries.put(key, Integer.parseInt(leaderboard.getString(key)));
        }
        leaderboardData = leaderboardEntries;
        return leaderboardEntries;
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
            leaderboardData = loadLeaderboard();
            saveLeaderboard();
        }
    }

    public Map<String, Integer> getLeaderboard() {
        loadLeaderboardJson();
        return leaderboardData;
    }
}
