package at.fhv.musicshopfx;

public class UserDataStore {

    private static UserDataStore instance;
    private static List<Role>

    public static UserDataStore getInstance() {
        if (null == UserDataStore.instance) {
            new UserDataStore();
        }

        return UserDataStore.instance;
    }

    private UserDataStore() {
        UserDataStore.instance = this;
    }
}
