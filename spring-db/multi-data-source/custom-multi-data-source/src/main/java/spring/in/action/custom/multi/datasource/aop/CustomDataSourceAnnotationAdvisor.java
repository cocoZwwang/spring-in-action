package spring.in.action.custom.multi.datasource.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;
import spring.in.action.custom.multi.datasource.annotation.DataSource;

@Component
public class CustomDataSourceAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice advice;

    private Pointcut pointcut;

    public CustomDataSourceAnnotationAdvisor(MultiDataSourceAnnotationInterceptor multiDataSourceAnnotationInterceptor) {
        this.advice = multiDataSourceAnnotationInterceptor;
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = (Pointcut) new AnnotationMatchingPointcut(DataSource.class, true);
        // 类注解
        Pointcut clpc = (Pointcut) AnnotationMatchingPointcut.forClassAnnotation(DataSource.class);
        // 方法注解
        Pointcut mpc = (Pointcut) AnnotationMatchingPointcut.forMethodAnnotation(DataSource.class);
        return new ComposablePointcut(cpc).union(clpc).union(mpc);
    }
}
