package frontcontroller;

import io.javalin.Javalin;

//contains endpoint start locations and middleware
public class FrontController {

    public FrontController(Javalin app){
        new Dispatcher(app);
    }
}
