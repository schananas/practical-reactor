package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "behold_flatmap")
class behold_flatmap
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Use flatMap operator, obviously...");
    }
}

@Mojo(name = "task_executor")
class task_executor
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to transform existing data running an asynchronous task for each source item https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "streaming_service")
class streaming_service
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to transform existing data running an asynchronous task for each source item where the async task can return multiple values, from a Mono source: https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "i_am_rubber_you_are_glue")
class i_am_rubber_you_are_glue
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to combine publishers in sequential order: https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "task_executor_again")
class task_executor_again
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Use concatMap(), obviously...");
    }
}

@Mojo(name = "need_for_speed")
class need_for_speed
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "I want to combine publishers selecting the first publisher which produces a value first: https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "plan_b")
class plan_b
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You have an empty sequence but you want another sequence instead: https://projectreactor.io/docs/core/release/reference/#which.create");
    }
}

@Mojo(name = "mail_box_switcher")
class mail_box_switcher
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#switchOnFirst-java.util.function.BiFunction-");
    }
}

@Mojo(name = "instant_search")
class instant_search
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to combine publishers selecting the first publisher which is triggered by the elements in a source sequence. This operator could have nickname 'flatMapLatest' because the way how it works... https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "prettify")
class prettify
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to combine publishers by coordinating their termination... https://projectreactor.io/docs/core/release/reference/#which.values. Use when, and, then, thenReturn...");
    }
}

@Mojo(name = "one_to_n")
class one_to_n
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You have a sequence but you are not interested in values, and you want to switch to a Flux at the end: https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "acid_durability")
class acid_durability
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "This task is combination of previous exercises. The trick is in nesting withing operators...");
    }
}

@Mojo(name = "major_merger")
class major_merger
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to combine publishers in emission order (combined items emitted as they come): https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "car_factory")
class car_factory
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to combine publishers with different types or to pair values: https://projectreactor.io/docs/core/release/reference/#which.values");
    }
}

@Mojo(name = "deterministic")
class deterministic
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You should defer decision until subscription actually happens...");
    }
}

@Mojo(name = "cleanup")
class cleanup
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Reactor has reactive equivalent of “try-with-resource”. You should use methods like Flux.using(...), Flux.usingWhen(...), Mono.using(...)...."
                        + "Its important to read documentation of these methods and to understand them completely for this task. ");
    }
}