package sample.app.dao.repo.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import sample.app.dao.repo.GemstoneRepository;

/**
 * The GemstoneRepositoryBeanPostProcessor class is a Spring BeanPostProcessor to log to standard out when the
 * GemfireRepository bean has been configured and initialized.
 *
 * @author John Blum
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class GemstoneRepositoryBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
    if (bean instanceof GemstoneRepository) {
      System.out.printf("%1$s (%2$s) initialized!%n", bean.getClass().getSimpleName(), beanName);
    }

    return bean;
  }

}
