package dragger2.inject;

import com.example.root.build_model.MainActivity;

import dagger.Module;
import dagger.Provides;
import dragger2.Students;

@Module
public class MainModule {
    private MainActivity mainActivity;

    public MainModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    Students privideStudent(){
        return new Students();
    }
}
