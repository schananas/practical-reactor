package com.github.schananas.hint;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "slow_down_there_buckaroo")
class slow_down_there_buckaroo
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint("You need to introduce a delay between each notification. (between each onNext signal): https://projectreactor.io/docs/core/release/reference/#which.time");
    }
}

@Mojo(name = "ready_set_go")
class ready_set_go
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You want to introduce a delay before the subscription happens: https://projectreactor.io/docs/core/release/reference/#which.time. Is flatMap() right operator for this case? There are only few `delay` operators, check documentation, which one suite your needs");
    }
}

@Mojo(name = "non_blocking")
class non_blocking
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://projectreactor.io/docs/core/release/reference/#schedulers");
    }
}

@Mojo(name = "blocking")
class blocking
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://projectreactor.io/docs/core/release/reference/#schedulers and https://projectreactor.io/docs/core/release/reference/#faq.wrap-blocking");
    }
}

@Mojo(name = "free_runners")
class free_runners
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "You should have learned by now... Whats the difference between flatMap and concatMap? Which scheduler should you use?");
    }
}

@Mojo(name = "sequential_free_runners")
class sequential_free_runners
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "Same as previous exercise but you need to retaining the original sequence order (this triggers the async processes immediately but reorders the results)");
    }
}

@Mojo(name = "event_processor")
class event_processor
        extends AbstractMojo implements Hint {

    public void execute() {
        printHint(
                "See: https://projectreactor.io/docs/core/release/reference/#advanced-parallelizing-parralelflux Use operators like parallel(), .runOn(), .sequential()...");
    }
}