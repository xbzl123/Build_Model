package Factory;

public class CarFactory implements Factory{
    @Override
    public Car createCar(String s) {
        if(s.equals("BWM")){
            return new BWM();
        }else{
            return new Benz();
        }
    }
}
