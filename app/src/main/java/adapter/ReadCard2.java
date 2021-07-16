package adapter;

public class ReadCard2 implements Target{
    Adapter2 adapter;
    public ReadCard2() {
    }

    public ReadCard2(Adapter2 adapter) {
        this.adapter = adapter;
    }
    public void setAdapter(Adapter2 adapter) {
        this.adapter = adapter;
    }

    @Override
    public String readData() {
        if(adapter == null){
            System.out.println("Version informations");
            return "Version informations";
        }else{
        String s = adapter.readData();
        System.out.println(s);
        return s;
        }
    }
}
