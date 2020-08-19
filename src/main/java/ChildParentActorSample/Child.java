package ChildParentActorSample;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class Child extends AbstractLoggingActor {
    public static class Command { }
    private long message = 0L;

    {
        receive(ReceiveBuilder
                .match(Command.class, this::onCommand)
                .build()
        );
    }

    private void onCommand(Command command) {
        message++;
        if(message%4 == 0) {
            throw  new RuntimeException("Oh no!, I got a command which is completely divisible by 4");
        } else {
            log().info("Got a command " + message);
        }
    }


    public static Props props() {
        return Props.create(Child.class);
    }
}
