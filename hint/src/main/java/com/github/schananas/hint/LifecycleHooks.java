package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "no_subscription_no_gains")
class no_subscription_no_gains
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Without modifying the sequence, you want to execute additional behavior on post-subscription: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "be_there_early")
class be_there_early
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Without modifying the sequence, you want to execute additional behavior on start of the sequence: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "atomic_counter")
class atomic_counter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Without modifying the sequence, you want to execute additional behavior on emissions: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "successfully_executed")
class successfully_executed
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Without modifying the sequence, you want to execute additional behavior on completion: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "need_to_cancel")
class need_to_cancel
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Without modifying the sequence, you want to execute additional behavior on cancellation: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "terminator")
class terminator
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Without modifying the sequence, you want to execute additional behavior https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "one_to_catch_them_all")
class one_to_catch_them_all
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Without modifying the sequence, you want to execute additional behavior on any terminating condition (complete, error, cancel): https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}

@Mojo(name = "ordering_is_important")
class ordering_is_important
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Check the documentation: https://projectreactor.io/docs/core/release/reference/");
    }
}

@Mojo(name = "one_to_rule_them_all")
class one_to_rule_them_all
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Without modifying the sequence, you want to execute additional behavior on any type of signal, represented as a Signal: https://projectreactor.io/docs/core/release/reference/#which.peeking");
    }
}