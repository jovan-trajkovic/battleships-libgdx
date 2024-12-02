package si.feri.um.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {
    public static final AssetDescriptor<BitmapFont> FONT =
        new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> BACKGROUNDS =
        new AssetDescriptor<TextureAtlas>(AssetPaths.BACKGROUNDS, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> SHIPS =
        new AssetDescriptor<TextureAtlas>(AssetPaths.SHIPS, TextureAtlas.class);
    public static final AssetDescriptor<Sound> SOUND_LASER =
        new AssetDescriptor<Sound>(AssetPaths.SOUND_LASER, Sound.class);
    public static final AssetDescriptor<Music> GAME_MUSIC =
        new AssetDescriptor<Music>(AssetPaths.GAME_MUSIC, Music.class);
    public static final AssetDescriptor<Music> MENU_MUSIC =
        new AssetDescriptor<Music>(AssetPaths.MENU_MUSIC, Music.class);

    private AssetDescriptors() {
    }
}
