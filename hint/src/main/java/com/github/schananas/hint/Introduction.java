package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "hello_world")
class hello_world
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "This one is easy! https://projectreactor.io/docs/core/release/reference/#intro-reactive will help you");
    }
}

@Mojo(name = "unresponsive_service")
class unresponsive_service
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Check if operator used in previous exercise supports variant that might suit this purpose");
    }
}

@Mojo(name = "empty_service")
class empty_service
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Check if operator used in previous exercises supports multiple variants that might suit this purpose");
    }
}

@Mojo(name = "multi_result_service")
class multi_result_service
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This one is easy! https://projectreactor.io/docs/core/release/reference/#intro-reactive will help you");
    }
}

@Mojo(name = "fortune_top_five")
class fortune_top_five
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This one is easy! https://projectreactor.io/docs/core/release/reference/#intro-reactive will help you");
    }
}

@Mojo(name = "nothing_happens_until_you_")
class nothing_happens_until_you_
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("This one is easy! https://projectreactor.io/docs/core/release/reference/#reactive.subscribe will help you");
    }
}

@Mojo(name = "leaving_blocking_world_behind")
class leaving_blocking_world_behind
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Check documentation for more variants of method used in previous exercise");
    }
}