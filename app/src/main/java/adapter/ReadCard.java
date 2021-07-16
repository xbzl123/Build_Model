package adapter;

import proxy.Advice;

public class ReadCard implements Target{
    Adapter adapter;
    public ReadCard() {
//        this.adapter = new Adapter();
    }

    public void setAdapter() {
        this.adapter = new Adapter();
    }

    @Override
    public String readData() {
        if(adapter != null){
        String s = adapter.readData();
        System.out.println(s);
        return s;
        }else {
            System.out.println("free sdcard machine");
            return "free sdcard machine";
        }
    }
}
