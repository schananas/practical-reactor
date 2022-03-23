package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "batch_writer")
class batch_writer
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Based of method signiture decide which batching strategy to use (grouping, windowing, and buffering)... https://projectreactor.io/docs/core/release/reference/#which.window");
    }
}

@Mojo(name = "command_gateway")
class command_gateway
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You should use `aggregateId` is the key mapper for grouped Flux, `flatMap()` to execute aggregates in parallel and `concatMap()` to execute commands from same aggregate sequentially...");
    }
}

@Mojo(name = "sum_over_time")
class sum_over_time
        extends AbstractMojo implements Hint {
    public void execute() {
        printHint(
                "You can achieve this combining few operators, one operator to batch elements emitted during 1s time span. After this you can use a operator like `reduce()` to get the sum of all elements in the window...");
    }
}