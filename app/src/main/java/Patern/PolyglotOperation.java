package Patern;

import java.io.IOException;

import org.graalvm.polyglot.Value;

class PolyglotOperation extends Operation{

    private final String language;
    private final Value source;

    public PolyglotOperation(String language, Value source) throws IOException {
        this.language = language;
        this.source = source;
    }

    public Value run(PaternEngine paternEngine, Object... args){
        return this.source.execute(args);
    }

    public String getLanguage() {
        return this.language;
    }

    public Value getSource() {
        return this.source;
    }

}