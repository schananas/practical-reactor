package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "sharing_is_caring")
class sharing_is_caring
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://bsideup.github.io/posts/daily_reactive/split/");
    }
}

@Mojo(name = "hot_vs_cold")
class hot_vs_cold
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to connect multiple Subscriber to a Flux and permanently connect the source when enough subscribers have registered. https://projectreactor.io/docs/core/release/reference/#which.multicasting "
                        + "Subscriber 1 completes and then subscriber 2 starts. How would you stop updates stream from completing if there are no subscribers? https://stackoverflow.com/questions/50528668/why-does-share-have-no-effect-on-cold-sources-autoconnect-vs-refcount");
    }
}

@Mojo(name = "history_lesson")
class history_lesson
        extends AbstractMojo implements Hint {
    public void execute() {
        printHint(
                "You want to cache data from a Publisher and replay it to later subscribers... There is multiple ways of solving this one. See: https://projectreactor.io/docs/core/release/reference/#which.multicasting ");
    }
}