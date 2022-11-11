package part_generator.app;

import org.apache.velocity.app.Velocity;

public class App {

    public static void main(String[] args) {
        Velocity.init();

        new WndPartGen();
    }

}
