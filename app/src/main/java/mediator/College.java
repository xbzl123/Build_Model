package mediator;

public abstract class College {
    protected Mediator mediator;
    public College(Mediator mediator){
        this.mediator = mediator;
    }
}
