package facade;
//看病模块（类）：包含了里面具体的所有流程
public class CureDisease {
    private Registration registration;
    private GetMedicine getMedicine;
    private Treat treat;
    public CureDisease(){
        this.registration = new Registration();
        this.treat = new Treat();
        this.getMedicine = new GetMedicine();
    }
    public void processCure(){
        //挂号
        registration.registor();
        //见诊
        treat.adiviceOfTreat();
        //拿药
        getMedicine.getPill();
    }
}
