package ch.fhnw.roundtable.etopia.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.Map;

public class VideoAssets<T extends Enum<T> & Asset> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoAssets.class);

    private final Map<T, VideoPlayer> players;

    public VideoAssets(Class<T> type) {
        this.players = new EnumMap<>(type);
    }

    public VideoPlayer getPlayer(T asset) {

        var existing = players.get(asset);

        if (existing != null) {
            return existing;
        }

        var player = VideoPlayerCreator.createVideoPlayer();

        try {
            player.load(Gdx.files.internal(asset.getPath()));
            player.setOnCompletionListener(ignored -> player.play());
            player.play();

            players.put(asset, player);

            return player;
        } catch (FileNotFoundException e) {
            LOGGER.error("Failed to load video {}", asset, e);

            player.dispose();

            return null;
        }
    }

    public void unload(T asset) {
        var player = players.remove(asset);

        if (player != null) {
            player.dispose();
        }
    }

    public void dispose() {
        for (VideoPlayer player : players.values()) {
            player.dispose();
        }

        players.clear();
    }
}
