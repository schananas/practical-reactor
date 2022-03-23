package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "transforming_sequence")
class transforming_sequence
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This is easy! First read: https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}

@Mojo(name = "transforming_sequence_2")
class transforming_sequence_2
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This is easy! First read: https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}

@Mojo(name = "cast")
class cast
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This is easy! First read: https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}

@Mojo(name = "maybe")
class maybe
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This is easy! First read: https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}

@Mojo(name = "sequence_sum")
class sequence_sum
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to aggregate a Flux by applying a function between each element... https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}

@Mojo(name = "sum_each_successive")
class sum_each_successive
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to aggregate a Flux by applying a function between each element but emitting each intermediary value... https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}

@Mojo(name = "sequence_starts_with_zero")
class sequence_starts_with_zero
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You  want to add pre-set elements to an existing sequence: https://projectreactor.io/docs/core/release/reference/#which.values");
    }

}