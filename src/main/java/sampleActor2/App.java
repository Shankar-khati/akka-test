package sampleActor2;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class App {
    static class AlarmActor extends AbstractLoggingActor {
        static class Activity {
        }

        static class Disable {
            private final String password;

            public Disable(String password) {
                this.password = password;
            }
        }

        static class Enable {
            private final String password;

            public Enable(String password) {
                this.password = password;
            }
        }

        private final String password;
        private final PartialFunction<Object, BoxedUnit> enabled;
        private final PartialFunction<Object, BoxedUnit> disabled;

        AlarmActor(String password) {
            this.password = password;

            enabled = ReceiveBuilder
                    .match(Activity.class, this::onActivity)
                    .match(Disable.class, this::onDisabled)
                    .build();
            disabled = ReceiveBuilder
                    .match(Enable.class, this::onEnabled)
                    .build();
            receive(disabled);
        }

        private void onEnabled(Enable enable) {
            if (password.equals(enable.password)) {
                log().info("Alarm Enabled");
                getContext().become(enabled);
            } else {
                log().warning("Blue team failed to enable alarm!!");
            }
        }

        private void onDisabled(Disable disable) {
            if (password.equals(disable.password)) {
                log().info("Alarm Disabled");
                getContext().become(disabled);
            } else {
                log().warning("Read team tried to disable alarm!!");
            }
        }

        private void onActivity(Activity activity) {
            log().warning("Enemies Ahead!!, mock to location!!");
        }

        public static Props props(String password) {
            return Props.create(AlarmActor.class, password);
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sampleActor2");
        final ActorRef alarmRef = system.actorOf(AlarmActor.props("cats"), "alarmActor");
        alarmRef.tell(new AlarmActor.Enable("dogs"), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Activity(), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Enable("cats"), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Activity(), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Disable("dogs"), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Activity(), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Disable("cats"), ActorRef.noSender());
        alarmRef.tell(new AlarmActor.Activity(), ActorRef.noSender());
    }
}


