package com.hami.sys.flyway;

import com.hami.sys.config.DBConfigDev;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

/**
 * <pre>
 * <li>Program Name : FlyWayMigrate
 * <li>Description  :
 * <li>History      : 2017. 7. 22.
 * </pre>
 *
 * @author HHG
 */
public class FlyWayMigrate {

    public static void main(String[] args) {

        DBConfigDev dc = new DBConfigDev();

        Flyway flyway = new Flyway();
        flyway.setDataSource(dc.dataSource());
        flyway.clean();
        //flyway.setTarget(MigrationVersion.LATEST);
        flyway.migrate();
    }

}
