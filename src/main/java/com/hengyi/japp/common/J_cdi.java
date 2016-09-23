package com.hengyi.japp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by jzb on 15-12-5.
 */
public class J_cdi {
    private static final Logger log = LoggerFactory.getLogger(J_cdi.class);
    private static BeanManager _manager;

    public static final BeanManager getBeanManager() {
        if (_manager != null)
            return _manager;
        try {
            CDI cdi = CDI.current();
            if (cdi != null) {
                _manager = cdi.getBeanManager();
                if (_manager != null)
                    return _manager;
            }
        } catch (IllegalStateException e) {

        }

        try {
            final InitialContext initialContext = new InitialContext();
            _manager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            log.error("Cannot locate the BeanManager for JavaEE 6.", e);
        }
        return _manager;
    }

    static final <T> T getBean(Class<T> clazz, Annotation... qualifiers) {
        BeanManager beanManager = getBeanManager();
        Set<Bean<?>> set = beanManager.getBeans(clazz, qualifiers);
        for (Bean bean : set) {
            Context context = beanManager.getContext(bean.getScope());
            return (T) context.get(bean, beanManager.createCreationalContext(bean));
        }
        throw new RuntimeException();
    }

    static final <T> void inject(BeanManager beanManager, Object obj) {
        // CDI uses an AnnotatedType object to read the annotations of a class
        AnnotatedType<T> type = (AnnotatedType<T>) beanManager.createAnnotatedType(obj.getClass());

        // The extension uses an InjectionTarget to delegate instantiation,
        // dependency injection
        // and lifecycle callbacks to the CDI container
        InjectionTarget<T> it = beanManager.createInjectionTarget(type);
        // each instance needs its own CDI CreationalContext
        CreationalContext instanceContext = beanManager.createCreationalContext(null);

        it.inject((T) obj, instanceContext); // call initializer methods and
        // perform field injection
        it.postConstruct((T) obj); // call the @PostConstruct method
    }

}
