package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "houston_we_have_a_problem")
class houston_we_have_a_problem
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("Without modifying the final sequence, you want execute additional behavior on error termination: https://projectreactor.io/docs/core/release/reference/#which.peeking. Also see Example 7: https://projectreactor.io/docs/core/release/reference/#_asynchronicity_to_the_rescue.");
    }
}

@Mojo(name = "potato_potato")
class potato_potato
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want the try/catch equivalent of catching an exception and wrapping and re-throwing: https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "under_the_rug")
class under_the_rug
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to recover from errors by falling back to a Publisher or Mono, possibly a empty one: https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "have_a_backup")
class have_a_backup
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You want to recover from errors by falling back to a Publisher or Mono, possibly different ones depending on the error: https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "error_reporter")
class error_reporter
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "This exercise is combination of several operators from this and previous chapters. Consider using operators learned till now and how you can use operator nesting to combine them. (eg: onErrorResume + then(error))");
    }
}

@Mojo(name = "unit_of_work")
class unit_of_work
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "This exercise is combination of several operators from this and previous chapters. Consider using operators learned till now and how you can use operator nesting to combine them. (eg: flatMap, onErrorResume, then, thenReturn)");
    }
}

@Mojo(name = "billion_dollar_mistake")
class billion_dollar_mistake
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://nurkiewicz.com/2021/08/onerrorcontinue-reactor.html & https://devdojo.com/ketonemaniac/reactor-onerrorcontinue-vs-onerrorresume");
    }
}

@Mojo(name = "resilience")
class resilience
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "https://devdojo.com/ketonemaniac/reactor-onerrorcontinue-vs-onerrorresume");
    }
}

@Mojo(name = "its_hot_in_here")
class its_hot_in_here
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to recover from errors by retrying with a simple policy: https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "back_off")
class back_off
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to recover from errors by retrying using a standard backoff strategy https://projectreactor.io/docs/core/release/reference/#which.errors");
    }
}

@Mojo(name = "good_old_polling")
class good_old_polling
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://bsideup.github.io/posts/daily_reactive/polling/");
    }
}