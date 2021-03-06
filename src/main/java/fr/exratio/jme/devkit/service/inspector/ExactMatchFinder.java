package fr.exratio.jme.devkit.service.inspector;

import fr.exratio.jme.devkit.properties.PropertySection;
import fr.exratio.jme.devkit.properties.builder.AbstractPropertySectionBuilder;
import fr.exratio.jme.devkit.service.RegistrationService;
import fr.exratio.jme.devkit.service.ServiceManager;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExactMatchFinder extends PropertySectionListFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExactMatchFinder.class);

  @Override
  public List<PropertySection> find(Object object) {
    RegistrationService registrationService = ServiceManager
        .getService(RegistrationService.class);
    AbstractPropertySectionBuilder<?> propertySectionBuilder = registrationService
        .getPropertySectionBuilderInstance(object.getClass(), object);
    if (propertySectionBuilder != null) {
      LOGGER.debug("-- find() Factory {} found for class {}",
          propertySectionBuilder.getClass().getCanonicalName(),
          object.getClass().getCanonicalName());
      return propertySectionBuilder.build();
    }
    return findNext(object);
  }

}
