package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "request_and_demand")
class request_and_demand
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to get notified (add additional behaviour) on request: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "limited_demand")
class limited_demand
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://projectreactor.io/docs/core/release/reference/#_on_backpressure_and_ways_to_reshape_requests");
    }
}
@Mojo(name = "uuid_generator")
class uuid_generator
        extends AbstractMojo implements Hint {
    public void execute() {
        printHint(
                "Read how many UUIDs have been requested and generate them in a loop.");
    }
}

@Mojo(name = "pressure_is_too_much")
class pressure_is_too_much
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to deal with backpressure errors by throwing a special IllegalStateException (OverflowException). See: https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "u_wont_brake_me")
class u_wont_brake_me
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to deal with backpressure errors by buffering excess values (bounded or unbounded). See: https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "subscriber")
class subscriber
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You should interact with subscription object. See: https://projectreactor.io/docs/core/release/reference/#_an_alternative_to_lambdas_basesubscriber");
    }
}