package spring.in.action.custom.multi.datasource.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import spring.in.action.custom.multi.datasource.annotation.DataSource;
import spring.in.action.custom.multi.datasource.context.MultiDataSourceHolder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class MultiDataSourceAnnotationInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MultiDataSourceAnnotationInterceptor.class);


    /**
     * 缓存方法注解值
     */
    private static final Map<Method, String> METHOD_CACHE = new HashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {

            String datasource = determineDatasource(invocation);
            if (! MultiDataSourceHolder.hasDataSourceRouterKey(datasource)) {
                logger.info("数据源[{}]不存在，使用默认数据源 >", datasource);
            }
            MultiDataSourceHolder.setDataSourceRouterKey(datasource);
            return invocation.proceed();
        } finally {
            MultiDataSourceHolder.removeDataSourceRouterKey();
        }
    }

    private String determineDatasource(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (METHOD_CACHE.containsKey(method)) {
            return METHOD_CACHE.get(method);
        } else {
            DataSource ds = method.isAnnotationPresent(DataSource.class) ? method.getAnnotation(DataSource.class)
                    : AnnotationUtils.findAnnotation(method.getDeclaringClass(), DataSource.class);
            METHOD_CACHE.put(method, ds.value());
            return ds.value();
        }
    }
}
