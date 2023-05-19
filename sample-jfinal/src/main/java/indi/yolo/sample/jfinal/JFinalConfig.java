package indi.yolo.sample.jfinal;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yoloz
 */
@Configuration
public class JFinalConfig {
    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.username}")
    private String name;
    @Value(value = "${spring.datasource.password}")
    private String pwd;
    @Value(value = "${spring.datasource.showSql}")
    private boolean showSql;

    @Value(value = "${spring.datasource.driverClassName}")
    private String driverClassName;

    @Bean
    public ActiveRecordPlugin activeRecord() {
        DruidPlugin druidPlugin;
        if (driverClassName == null || driverClassName.isEmpty()) {
            druidPlugin = new DruidPlugin(url, name, pwd);
        } else {
            druidPlugin = new DruidPlugin(url, name, pwd, driverClassName);
        }
        WallFilter wallFilter = new WallFilter();
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(100);
//        statFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        druidPlugin.start();

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.setShowSql(showSql);
        arp.setDialect(new MysqlDialect());
        arp.start();
        return arp;
    }
}
