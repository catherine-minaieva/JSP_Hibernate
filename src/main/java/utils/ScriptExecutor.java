package utils;

import config.DbConnection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class ScriptExecutor {
    private final static String PACKAGE_NAME = "scripts";

    public static void main(String[] args) {
        start();
    }

    @SneakyThrows
    public static void start() {
        log.info("Start of migration..." + PACKAGE_NAME);
        Connection connection = DbConnection.getInstance().getConnection();
        List<String> resourceFiles = sortScript(getResourceFiles(PACKAGE_NAME));

        ScriptExecutor.log.info("List of scripts : ");
        ScriptExecutor.log.info(String.valueOf(resourceFiles));

        ScriptRunner scriptRunner = new ScriptRunner(connection);
        for (String script : resourceFiles) {
            ScriptExecutor.log.info("Script execution : " + script);
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(ScriptExecutor.class
                    .getClassLoader().getResourceAsStream(script)))) {
                scriptRunner.runScript(reader);
            }
        }

        connection.close();
    }

    private static Set<String> getResourceFiles(String path) throws IOException {
        final Reflections reflections = new Reflections(path, new ResourcesScanner());
        return reflections.getResources(Pattern.compile(".*\\.sql"));
    }

    private static List<String> sortScript(Iterable<String> scripts) {
        return StreamSupport.stream(scripts.spliterator(), false)
                .sorted((script1, script2) -> {
                    final Integer s1 = parseScriptName(script1);
                    final Integer s2 = parseScriptName(script2);
                    if (s1 == null && s2 == null) return 0;
                    if (s1 == null) return 1;
                    if (s2 == null) return -1;
                    return s1.compareTo(s2);
                })
                .collect(Collectors.toList());
    }

    private static Integer parseScriptName(String scriptName) {
        try {
            final String nameWithoutPackage = scriptName.substring(PACKAGE_NAME.length() + 1, scriptName.length());
            return Integer.valueOf(nameWithoutPackage.split("_")[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
