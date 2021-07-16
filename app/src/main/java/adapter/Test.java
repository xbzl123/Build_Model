package adapter;

public class Test {
    public static void main(String[] args){
//        Target target = new ReadCard(new Adapter());
//        target.readData();
        ReadCard target = new ReadCard();
//        target.setAdapter();
        target.readData();
        ReadCard2 target1 = new ReadCard2();
        target1.setAdapter(new Adapter2(new Mcard()));
        target1.readData();
    }
}
