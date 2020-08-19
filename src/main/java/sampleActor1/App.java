/*
  @author: shankar singh
 * @here: sample code to show how actor executes for messages.
 *
 * System requirements java 8+, since I'm using lambda expression
 * another thing is just actor dependency
 * <dependency>
 *     <groupId>com.typesafe.akka</groupId>
 *     <artifactId>akka-actor_2.11</artifactId>
 *     <version>2.4.9</version>
 * </dependency>
 * */



package sampleActor1;

import akka.actor.AbstractLoggingActor; // if you don't want logger just use AbstractActor
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.stream.IntStream;

public class App {
    static class CounterActor extends AbstractLoggingActor {
        // actors communicates using messages.
        static class Message { }
        private int counter = 0;

        // initialization block
        // similar to static block but non-static
        {
            receive(ReceiveBuilder.match(Message.class, this::onMessage).build());
        }

        // just a method to print message and increase counter
        private void onMessage(Message message) {
            counter++;
            log().info("Increasing counter " + counter);
        }

        //factory method to crate Actor
        public  static  Props props() {
            return Props.create(CounterActor.class);
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sampleActor1");
        final ActorRef counter = system.actorOf(CounterActor.props(), "CounterActor");

        // simulating concurrent messages using  async threads.
        IntStream.range(0, 5).forEach(i -> new Thread(() -> IntStream.range(0, 5).forEach(j -> counter.tell(new CounterActor.Message(), ActorRef.noSender()))).start());
    }
}

/*
Trust me or run it output will be like:
[INFO] [08/19/2020 16:38:34.906] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 1
[INFO] [08/19/2020 16:38:34.906] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 2
[INFO] [08/19/2020 16:38:34.906] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 3
[INFO] [08/19/2020 16:38:34.906] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 4
[INFO] [08/19/2020 16:38:34.906] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 5
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 6
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 7
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 8
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 9
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 10
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 11
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 12
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 13
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 14
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 15
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 16
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 17
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 18
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 19
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 20
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 21
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 22
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 23
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 24
[INFO] [08/19/2020 16:38:34.907] [sample1-akka.actor.default-dispatcher-2] [akka://sample1/user/Counter] Increasing counter 25
* */
