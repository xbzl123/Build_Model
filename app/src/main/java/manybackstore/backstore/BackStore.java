package manybackstore.backstore;

import java.util.HashMap;

public class BackStore {
    private HashMap<String,Object> status;

    public BackStore(HashMap<String, Object> status) {
        this.status = status;
    }

    public HashMap<String, Object> getStatus() {
        return status;
    }

    public void setStatus(HashMap<String, Object> status) {
        this.status = status;
    }
}
