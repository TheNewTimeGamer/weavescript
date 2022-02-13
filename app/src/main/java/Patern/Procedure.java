package Patern;

import org.graalvm.polyglot.Value;

public class Procedure extends Operation {

    String[] operations;

    public Procedure() {}

    public Procedure(String[] operations) {
        this.operations = operations;
    }

    public Value run(PaternEngine paternEngine, Object... args) {
        for (String operation : this.operations) {
            paternEngine.run(operation);
        }
        return null;
    }

    public Value getSource() {
        return Value.asValue("inline-procedure");
    }

}