package adapter;

public class Adapter2 implements Target{
    Mcard mcard;
    public Adapter2(Mcard mcard){
        this.mcard = mcard;
    }

    @Override
    public String readData() {
        return mcard.getData();
    }
}
