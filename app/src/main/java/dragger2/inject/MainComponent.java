package dragger2.inject;

import com.example.root.build_model.MainActivity;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);

}
