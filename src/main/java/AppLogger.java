import java.util.logging.*;

public class AppLogger {
    static Formatter simpleFormatter = null;
    static Handler fileHandler  = null;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static AppLogger appLogger;

    public static AppLogger getAppLogger() {
        try {
            if (appLogger == null) {
                appLogger = new AppLogger();
                fileHandler = new FileHandler("/tmp/mongoSeed.log");
                simpleFormatter = new SimpleFormatter();
                fileHandler.setFormatter(simpleFormatter);
                LOGGER.addHandler(fileHandler);

                //Setting levels to handlers and LOGGER
                LOGGER.setLevel(Level.INFO);
                fileHandler.setLevel(Level.ALL);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return appLogger;
    }

    public  Logger get(String name){
        return LOGGER;
    }
}
