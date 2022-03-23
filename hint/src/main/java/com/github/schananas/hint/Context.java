package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "message_tracker")
class message_tracker
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://projectreactor.io/docs/core/release/reference/#context.read");
    }
}

@Mojo(name = "execution_counter")
class execution_counter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "It seams there is no counter initialized in context... See: https://projectreactor.io/docs/core/release/reference/#context.write");
    }
}

@Mojo(name = "pagination")
class pagination
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Use all the knowledge gained so far. You will need error handling to skip errors, context counter to counter which page you are on, and powerful `doOnEach(...)` operator to update context after each page is read or the case of error.");
    }
}