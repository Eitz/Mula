package externalRunner;

import interpretador.ExecutorCodigoJava;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExternalRunner {

    public Object runScript(String... args) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = args;
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = stdInput.readLine()) != null) {
            sb.append(s).append("\n");
        }
        String texto = sb.toString();
        Pattern pattern = Pattern.compile("MULA_OUT:'([^']*)'");
        Matcher matcher = pattern.matcher(texto);
        Object val = null;
        if (matcher.find()) {
            val = matcher.group(1);
        }

        return val;
    }

    public void run(String language, String code) throws IOException {
        // salva code em arquivo script.txt;
        switch (language) {
            case "java":
                ExecutorCodigoJava executorCodigoJava = new ExecutorCodigoJava();
                executorCodigoJava.executar(code);
                break;
            case "perl":
                Object output = runScript("java", "-jar", "script.txt");
        }
    }

}
