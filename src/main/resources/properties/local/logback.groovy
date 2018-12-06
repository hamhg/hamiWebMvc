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
            pattern = "[%d{HH:mm:ss.SSS}][%thread][%-4.-9X{username},%4L] %-5level %logger{36}.%M - %msg%n"
        }
    }
}

appender("ROLLING", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "[%d{yyyy.MM.dd HH:mm:ss.SSS}][%thread] %-5level %-40logger{36} [%-20.-20M %4L, %-4.-9X{username}] - %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${WEBAPP_DIR}/logs/log-%d{yyyy-MM-dd}.zip"
        maxHistory = 12 // Delete files after 12months
    }
}

root(DEBUG, appenderList)
 
logger("org.thymeleaf", OFF)
logger("org.springframework.jdbc", OFF)
logger("org.springframework.web", OFF)
logger("org.springframework.core", OFF)
logger("org.springframework.context", OFF)
logger("org.springframework.beans", OFF)
logger("org.springframework.biz", OFF)
logger("org.springframework.jndi", OFF)
logger("org.springframework.security", DEBUG)
logger("org.apache.commons.dbcp2", OFF)

logger("log4jdbc.debug", OFF)
logger("jdbc.sqltiming", DEBUG)
logger("jdbc.resultsettable", DEBUG)
logger("jdbc.connection", OFF)
logger("jdbc.sql", OFF)
logger("jdbc.sqlonly", OFF)
logger("jdbc.resultset", OFF)
logger("jdbc.audit", OFF)

logger("com.hami.sys", DEBUG)
logger("com.hami.biz", DEBUG)
logger("com.hami.biz.common.controller.CommonController", DEBUG)
