package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.config.HibernateConfiguration;
import ar.edu.unlp.info.bd2.model.PersistentObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {HibernateConfiguration.class},
    loader = AnnotationConfigContextLoader.class)
public class HibernateBithubTestCase extends BithubServiceTestCase<Long> {

  @Autowired
  @Qualifier("hibernateService")
  private BithubService service;

  @Override
  protected BithubService getService() {
    return this.service;
  }

  @Override
  protected Long getId(PersistentObject object) {
    return object.getId();
  }
}
