package Strategy;
//策略模式
public class Tool{

    TravelTool travelTool;

    public Tool(TravelTool travelTool) {
        this.travelTool = travelTool;
    }

    public void travelBy() {
        this.travelTool.travelStyle();
    }

    public void setTravelTool(TravelTool travelTool) {
        this.travelTool = travelTool;
    }

    public static void main(String[] args){
        Tool test = new Tool(new BusTool());
        test.travelBy();
        test.setTravelTool(new AirTool());
        test.travelBy();
    }
}
