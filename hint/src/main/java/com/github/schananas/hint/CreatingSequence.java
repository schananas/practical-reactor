package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "value_I_already_have_mono")
class value_I_already_have_mono
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Creating a New Sequence that emits a T, and you already have:  https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "potentially_null_mono")
class potentially_null_mono
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Creating a New Sequence from a potentially null T: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "optional_value")
class optional_value
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Creating a New Sequence from an Optional<T>: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "callable_counter")
class callable_counter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Creating a New Sequence that emits from various single-valued sources such as task: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "future_counter")
class future_counter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that emits from various single-valued sources such as future: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "runnable_counter")
class runnable_counter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that emits from various single-valued sources such as runnable task: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "acknowledged")
class acknowledged
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that completes empty: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "seen")
class seen
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that never does anything: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "trouble_maker")
class trouble_maker
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that errors immediately: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "from_array")
class from_array
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that iterates over array: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "from_list")
class from_list
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that iterates over iterable or a collection: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "from_stream")
class from_stream
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that iterates over a Stream supplied for each Subscription: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "interval")
class interval
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to repeat an existing sequence but at time intervals: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "range")
class range
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that iterates over a range of integers https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "repeat")
class repeat
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to repeat an existing sequence: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "generate_programmatically")
class generate_programmatically
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Creating a New Sequence that generates events programmatically: https://projectreactor.io/docs/core/release/reference/#which.create. Also you may want to introduce counters");
    }
}

@Mojo(name = "multi_threaded_producer")
class multi_threaded_producer
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                " Did you really answer all the question from previous exercise correctly?");
    }
}