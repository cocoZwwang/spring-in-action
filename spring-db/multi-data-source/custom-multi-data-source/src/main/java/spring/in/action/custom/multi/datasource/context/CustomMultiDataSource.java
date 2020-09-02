package spring.in.action.custom.multi.datasource.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源
 * spring boot 的多数据源最主要是通过AbstractRoutingDataSource来实现的
 */
public class CustomMultiDataSource extends AbstractRoutingDataSource{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMultiDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = MultiDataSourceHolder.getDataSourceRouterKey();
        LOGGER.info("当前数据源是：{}",dataSourceName);
        return dataSourceName;
    }
}
