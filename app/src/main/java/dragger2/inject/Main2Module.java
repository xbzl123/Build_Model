package dragger2.inject;

import com.example.root.build_model.MainActivity;

import dagger.Module;

@Module
public class Main2Module {
    private MainActivity mainActivity;

    public Main2Module(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
