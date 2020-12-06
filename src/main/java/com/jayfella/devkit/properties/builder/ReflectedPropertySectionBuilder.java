package com.jayfella.devkit.properties.builder;

import com.jayfella.devkit.properties.PropertySection;
import com.jayfella.devkit.properties.component.enumeration.EnumEditor;
import com.jayfella.devkit.service.inspector.ExactMatchFinder;
import com.jayfella.devkit.service.inspector.InheritedMatchFinder;
import com.jayfella.devkit.service.inspector.PropertySectionListFinder;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectedPropertySectionBuilder extends AbstractPropertySectionBuilder<Object> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ReflectedPropertySectionBuilder.class);

  private static final Field classRefField = FieldUtils
      .getField(PropertyDescriptor.class, "classRef", true);
  private final PropertySectionListFinder propertySectionListFinder;
  private final Map<Class<?>, Set<PropertyDescriptor>> groupedDescriptors;

  public ReflectedPropertySectionBuilder(Object object,
      Field... ignoredFieldsArray) {
    super(object, ignoredFieldsArray);
    groupedDescriptors = getPropertyDescriptorGroupByClass(object);
    propertySectionListFinder = new ExactMatchFinder();
    propertySectionListFinder.chainWith(new InheritedMatchFinder());
  }

  public static Map<Class<?>, Set<PropertyDescriptor>> getPropertyDescriptorGroupByClass(
      Object bean) {
    PropertyDescriptor[] descriptors;
    try {
      descriptors = Introspector.getBeanInfo(bean.getClass())
          .getPropertyDescriptors();
    } catch (IntrospectionException e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }
    Map<Class<?>, Set<PropertyDescriptor>> map = Arrays.stream(descriptors)
        .collect(Collectors.groupingBy(descriptor -> ReflectedPropertySectionBuilder
            .getPropertyDescriptorClassReference(descriptor), Collectors.toSet()));
    return map;
  }

  public static Class<?> getPropertyDescriptorClassReference(
      PropertyDescriptor propertyDescriptor) {
    WeakReference<?> classRef = null;
    try {
      classRef = (WeakReference<?>) classRefField.get(propertyDescriptor);
    } catch (IllegalAccessException e) {
      //should not happen add loger when not in test
      System.out.println("IllegalAccessException");
    }
    Class<?> propertyClassRef = (Class<?>) classRef.get();
    return propertyClassRef;
  }

  @Override
  public List<PropertySection> build() {
    LOGGER.trace(">> build()");
    List<PropertySection> result = new ArrayList<>();
    //One PropertySection by class level
    for (Class classLevel : groupedDescriptors.keySet()) {
      PropertySection propertySection = new PropertySection(classLevel.getSimpleName());
      for (PropertyDescriptor descriptor : groupedDescriptors.get(classLevel)) {
        Class propertyType = descriptor instanceof IndexedPropertyDescriptor
            ? ((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType()
            : descriptor.getPropertyType();

        PropertyEditor editor = propertyType.isEnum() ? new EnumEditor(propertyType)
            : PropertyEditorManager.findEditor(propertyType);
        if (editor == null) {
          LOGGER.debug("-- build() No editor found for class {}, trying another finding strategy",
              descriptor.getPropertyType());
          continue;
        }
        try {
          editor.setValue(descriptor.getReadMethod().invoke(object));
        } catch (Exception e) {
          LOGGER.debug(
              "-- build() An error happened trying to read the field value of type {} and named {}, ignoring the field.",
              descriptor.getPropertyType(), descriptor.getName(), e);
          continue;
        }
        propertySection.addProperty(descriptor.getDisplayName(), editor.getCustomEditor());
      }
      result.add(propertySection);
    }
    return result;
  }

}