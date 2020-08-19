package ChildParentActorSample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.stream.IntStream;

public class BootStrap {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();

        // this is the root we have created a parent from actorOf method.
        // now hierarchy will root(system.actorOf) ==> parent(getContext().actorOf) ==> Child
        final ActorRef parent = system.actorOf(Parent.props(), "parent");

        IntStream.range(0, 100).forEach(i -> parent.tell(new Child.Command(), ActorRef.noSender()));
    }
}
