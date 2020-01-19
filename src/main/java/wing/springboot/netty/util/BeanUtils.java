package wing.springboot.netty.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName)throws BeansException{
        if(StringUtils.isEmpty(beanName)
            || Objects.isNull(context)){
            throw new NullPointerException();
        }
        T bean = (T)context.getBean(beanName);
        if(Objects.isNull(bean)){
            throw new NullPointerException();
        }
        return bean;
    }

    public static <T> T getBean(Class<T> tClass){
        if(Objects.isNull(context)){
            throw new NullPointerException();
        }
        return context.getBean(tClass);
    }

    public static ApplicationContext getContext(){
        return context;
    }
}