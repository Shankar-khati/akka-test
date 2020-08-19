package ChildParentActorSample;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;
import static akka.actor.SupervisorStrategy.*;

public class Parent extends AbstractLoggingActor {
    // initializer non-static block
    {
        // creating child actor
        final ActorRef child = getContext().actorOf(Child.props(), "Child");

        //adding receiver as it will called on tell method.
        receive(ReceiveBuilder
                .matchAny(any -> child.forward(any, getContext())) // this is how we can forward to child
                .build()
        );
    }

    // creating a strategy to handle error by parent.
    // As we know an exception can occur in any way in actor model
    // we don't catch the error we just send error to supervisor so that it could be handheld properly.
    public static final OneForOneStrategy STRATEGY = new OneForOneStrategy(
            10, // number or reties
            Duration.create("10 seconds"), // note: if it will fail 10 times within 10 seconds, than it will give up and stop the child.
            DeciderBuilder // works similar to receive builder
                .match(RuntimeException.class, ex -> restart())  //here we have access of child life cycle like restart stop.
                .build()
    );

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return STRATEGY; // I have my own strategy to handle things.
    }


    // just a factory method to return instance.
    public  static Props props () {
        return Props.create(Parent.class);
    }
}
