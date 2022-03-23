package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "single_shooter")
class single_shooter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Use a sink that will emit a single element to its subscribers.");
    }
}

@Mojo(name = "single_subscriber")
class single_subscriber
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Use a sink that can emit multiple values to a single subscriber. Buffering of previously emitted elements should be done withing the sink. Use `onBackpressureBuffer` strategy.");
    }
}

@Mojo(name = "it_gets_crowded")
class it_gets_crowded
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Use a sink that can emit same values to several subscribers. Subscribers receive only the signals emitted through the sink after they have subscribed. Use `onBackpressureBuffer` strategy.");
    }
}

@Mojo(name = "open_24_7")
class open_24_7
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Check the documentation to see how to use the autoCancel parameter: https://projectreactor.io/docs/core/release/reference/#_sinks_many_multicast_onbackpressurebufferargs");
    }
}

@Mojo(name = "blue_jeans")
class blue_jeans
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Which sink can replay all elements to new subscribers?");
    }
}

@Mojo(name = "emit_failure")
class emit_failure
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Read documentation carefully. How should you emit elements in optimistically manner? Can you define different emit strategies? https://projectreactor.io/docs/core/release/reference/#sinks");
    }
}