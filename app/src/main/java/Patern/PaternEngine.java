package Patern;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.math.BigInteger;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

public class PaternEngine {

    private final HashMap<String, Operation> operations = new HashMap<String, Operation>();

    public final Engine engine;
    public final Context context;

    public PaternEngine() {
        this.engine = Engine.create();
        this.context = Context.newBuilder().allowAllAccess(true).engine(this.engine).build();
    }

    public void run(String procedure) {
        this.operations.get(procedure).run(this);
    }

    public String registerOperation(File file) throws Exception {
        String source = PaternEngine.getFileDataAsString(file);
        String hash = PaternEngine.generatePatern(source);

        String language = PaternEngine.getLanguage(file);
        PolyglotOperation operation = new PolyglotOperation(language, PaternEngine.parseSource(this, language, source));
        this.operations.put(hash, operation);
        return hash;
    }

    public String registerProcedure(Procedure procedure) throws Exception {
        StringBuilder builder = new StringBuilder();
        for(String operation : procedure.operations) {
            builder.append(operation);
        }
        String hash = PaternEngine.generatePatern(builder.toString());
        this.operations.put(hash, procedure);
        return hash;
    }

    public static String generatePatern(String source) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = hash.digest(source.getBytes());
        return bytesToHex(hashInBytes);
    }

    public static String bytesToHex(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);   
        StringBuilder hexString = new StringBuilder(number.toString(16));   
        while (hexString.length() < 32) { 
            hexString.insert(0, '0'); 
        } 
        return hexString.toString(); 
    }

    public static byte[] getFileData(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        return buffer;
    }

    public static String getFileDataAsString(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        return new String(buffer);
    }

    public static String getLanguage(File file) {
        String extension = file.getName().split("\\.")[1];
        return extension;
    }

    public static Value parseSource(PaternEngine paternEngine, String language, String source) {
        return paternEngine.context.eval(language, source);
    }

}