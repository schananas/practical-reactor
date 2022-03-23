package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "girls_are_made_of_sugar_and_spice")
class girls_are_made_of_sugar_and_spice
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This is easy! First read: https://projectreactor.io/docs/core/release/reference/#which.filtering");
    }

}

@Mojo(name = "needle_in_a_haystack")
class needle_in_a_haystack
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This is easy! First read: https://projectreactor.io/docs/core/release/reference/#which.filtering");
    }

}

@Mojo(name = "economical")
class economical
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Ignore duplicates! First read: https://projectreactor.io/docs/core/release/reference/#which.filtering");
    }

}

@Mojo(name = "watch_out_for_the_spiders")
class watch_out_for_the_spiders
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to keep only a subset of only the first element. First read: https://projectreactor.io/docs/core/release/reference/#which.filtering");
    }

}

@Mojo(name = "dont_take_more_then_you_need")
class dont_take_more_then_you_need
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to keep only a subset of the sequence by taking N elements at the beginning of the sequence! First read: https://projectreactor.io/docs/core/release/reference/#which.filtering");
    }

}

@Mojo(name = "golden_middle")
class golden_middle
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Combine two operators to get the middle. First read: https://projectreactor.io/docs/core/release/reference/#which.filtering");
    }

}