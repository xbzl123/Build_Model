package adapter;

public class Adapter extends Mcard implements Target {
    @Override
    public String readData() {
        return getData();
    }
}
