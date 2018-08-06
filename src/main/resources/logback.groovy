package properties
// always a good idea to add an on console status listener
statusListener(OnConsoleStatusListener)

def appenderList = []//["ROLLING"]
def WEBAPP_DIR = "."
def consoleAppender = true

// does hostname match pixie or orion?
if (hostname =~ /pixie|orion/) {
    WEBAPP_DIR = "/opt/myapp"
    appenderList.add("ROLLING")
    consoleAppender = false
} else {
    appenderList.add("CONSOLE")
}

if (consoleAppender) {
    appender("CONSOLE", ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %-40logger{36} [%-20.-20M %4L, %-4.-9X{username}] - %msg%n"
        }
    }
}

appender("ROLLING", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %-40logger{36} [%-20.-20M %4L, %-4.-9X{username}] - %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${WEBAPP_DIR}/logs/log-%d{yyyy-MM-dd}.zip"
        maxHistory = 12 // Delete files after 12months
    }
}

root(OFF, appenderList)

logger("org.thymeleaf", OFF)
logger("org.springframework.biz.servlet.mvc.method.annotation.RequestMappingHandlerMapping", DEBUG)
logger("org.springframework.jdbc.sys.JdbcTemplate", OFF)
logger("org.springframework.security", OFF)
logger("org.apache.commons.dbcp2", OFF)

logger("jdbc.sqltiming", DEBUG)
logger("jdbc.sqlonly", OFF)
logger("jdbc.resultsettable", DEBUG)
logger("jdbc.sql", OFF)
logger("jdbc.audit", OFF)

logger("com.hami.sys", DEBUG)
logger("com.hami.biz", DEBUG)
logger("com.hami.biz.common.controller.CommonController", DEBUG)
