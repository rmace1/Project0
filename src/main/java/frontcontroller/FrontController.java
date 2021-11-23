package frontcontroller;

import io.javalin.Javalin;

//contains endpoint start locations and middleware
public class FrontController {

    public FrontController(Javalin app){
        app.exception(NumberFormatException.class, (e, context) -> {
            context.result("Invalid input, expecting a number.");
        });

        new Dispatcher(app);
    }


}
