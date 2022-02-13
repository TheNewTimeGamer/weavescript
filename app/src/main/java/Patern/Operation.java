package Patern;

import org.graalvm.polyglot.Value;

public abstract class Operation {

    public abstract Value run(PaternEngine paternEngine, Object... args);

    public abstract Value getSource();

}