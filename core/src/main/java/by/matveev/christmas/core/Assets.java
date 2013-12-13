package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.*;


public final class Assets implements Disposable, AssetErrorListener {

    private final Logger log = new Logger("Assets", Logger.DEBUG);

    private final AssetManager manager;
    private final ObjectMap<String, Array<Asset>> groups;

    private static Assets instance;

    private Assets() {
        manager = new AssetManager();
        manager.setErrorListener(this);

//        Texture.setAssetManager(manager);

        groups = new ObjectMap<String, Array<Asset>>();
    }

    public static Assets instance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public void load(String path) {
        log.info("Start loading assets meta from " + path);
        final JsonValue groupsArray = new JsonReader().parse(Gdx.files.internal(path));
        for (int ix = 0; ix < groupsArray.size; ix++) {
            final Array<Asset> group = new Array<Asset>();

            final JsonValue groupObj = groupsArray.get(ix);
            groups.put(groupObj.getString("gid"), group);

            final JsonValue assetsArray = groupObj.get("assets");
            for (int aix = 0; aix < assetsArray.size; aix++) {
                final JsonValue assetObj = assetsArray.get(aix);
                final String type = assetObj.getString("type");
                group.add(new Asset(resolve(type), assetObj.getString("path"), params(type)));
            }
        }
    }

    private static AssetLoaderParameters params(String type) {
        if ("texture".equals(type)) {
            final TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
            param.minFilter = Texture.TextureFilter.Linear;
            param.magFilter = Texture.TextureFilter.Linear;
            return param;
        }
        return null;
    }

    private static AssetLoaderParameters params(Class<?> type) {
        if (Texture.class.equals(type)) {
            final TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
            param.minFilter = Texture.TextureFilter.Linear;
            param.magFilter = Texture.TextureFilter.Linear;
            return param;
        }
        return null;
    }


    private static Class<?> resolve(String type) {
        if ("texture".equals(type)) return Texture.class;
        if ("atlas".equals(type)) return TextureAtlas.class;
        if ("font".equals(type)) return BitmapFont.class;
        if ("music".equals(type)) return Music.class;
        if ("sound".equals(type)) return Sound.class;
        return null;
    }

    public void loadGroup(String groupName) {
        log.info("Loading group '" + groupName + "'");

        Array<Asset> assets = groups.get(groupName, null);

        if (assets != null) {
            for (Asset asset : assets) {
                log.info("\t\tLoading asset '" + asset.path + "'");
                manager.load(asset.path, asset.type, asset.params);
            }
        } else {
            log.error("Error loading group " + groupName + ", not found");
        }
    }

    public void unloadGroup(String groupName) {
        log.info("Unloading group '" + groupName + "'");

        Array<Asset> assets = groups.get(groupName, null);

        if (assets != null) {
            for (Asset asset : assets) {
                if (manager.isLoaded(asset.path, asset.type)) {
                    log.info("\t\tUnloading asset '" + asset.path + "'");
                    manager.unload(asset.path);
                }
            }
        } else {
            log.error("Error unloading group " + groupName + ", not found");
        }
    }

    public void loadAsset(String path, Class<?> type) {
        log.info("\tLoading asset '" + path + "'");

        manager.load(path, type, params(type));
    }

    public void unloadAsset(String path) {
        log.info("\tUnloading asset '" + path + "'");
        manager.unload(path);
    }

    public <T> T get(String fileName) {
        if (manager.isLoaded(fileName)) {
            return manager.get(fileName);
        }
        log.error("Asset '" + fileName + "' hasn't been loaded");
        return null;
    }

    public <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }

    public boolean update() {
        return manager.update();
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public float getProgress() {
        return manager.getProgress();
    }

    public void debug() {
        log.info("\tDiagnostics:\n" + manager.getDiagnostics());
    }

    @Override
    public void dispose() {
        log.info("Shutting down...");
        manager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        log.error("Can't load asset: " + asset.toString(), throwable);
    }

    public static final class Asset<T> {
        private final Class<T> type;
        private final String path;
        private final AssetLoaderParameters<T> params;

        public Asset(Class<T> type, String path, AssetLoaderParameters<T> params) {
            this.type = type;
            this.path = path;
            this.params = params;
        }
    }
}