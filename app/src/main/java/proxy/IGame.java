package proxy;

public interface IGame {
    @Before(cls = Advice.class,method = "beforeM")
    @After(cls = Advice.class,method = "afterM")
    void playgame();
}
