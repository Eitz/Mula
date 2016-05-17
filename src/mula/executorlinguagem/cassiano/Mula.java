package mula.executorlinguagem.cassiano;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Mula {

    private static Map<String, Object> params;

    public static <T> T getParam(String nome) {
        return (T) params.get(nome);
    }

    public static void setParams(String parametros) {
        params = new HashMap<>();
        parametros = parametros.replaceAll("[{} ]", "");
        Collection<String> paramsArray = Arrays.asList(parametros.split(","));
        paramsArray.stream().map((p) -> p.split(":")).forEach((keyVal) -> {
            params.put(keyVal[0], keyVal[1]);
        });
    }

}
