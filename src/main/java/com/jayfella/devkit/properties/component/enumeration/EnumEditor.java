package com.jayfella.devkit.properties.component.enumeration;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jayfella.devkit.properties.component.AbstractPropertyEditor;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EnumSet;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class EnumEditor extends AbstractPropertyEditor<Enum> {

  private JPanel contentPanel;
  private JComboBox<Enum> valueComboBox;
  private final Class<? extends Enum> clazz;

  public EnumEditor(Class<Enum> clazz) {
    super(null);
    this.clazz = clazz;
  }

  public EnumEditor(Enum value) {
    super(value);
    this.clazz = value.getClass();
    $$$setupUI$$$();
  }

  @Override
  public void setTypedValue(Enum newValue) {
    if (newValue == null) {
      newValue = valueComboBox.getItemAt(0);
    }
    valueComboBox.setSelectedItem(value);
    super.setTypedValue(newValue);
  }

  protected Enum<?> computeValue() {
    return (Enum<?>) valueComboBox.getSelectedItem();
  }

  @Override
  public Component getCustomEditor() {
    return contentPanel;
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    contentPanel = new JPanel();
    contentPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
    final Spacer spacer1 = new Spacer();
    contentPanel.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    contentPanel.add(valueComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JSeparator separator1 = new JSeparator();
    contentPanel.add(separator1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
            null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return contentPanel;
  }

  private void createUIComponents() {
    valueComboBox = new JComboBox(EnumSet.allOf(clazz).toArray());
    if (value == null) {
      valueComboBox.setSelectedIndex(0);
    } else {
      valueComboBox.setSelectedItem(value);
    }

    ItemListener itemListener = evt -> {
      if (ItemEvent.DESELECTED == evt.getStateChange()) {
        return;
      }
      Enum oldComponent = value;
      Enum<?> newComponent = computeValue();
      if (!oldComponent.equals(newComponent)) {
        setTypedValue(newComponent);
      }
    };
    valueComboBox.addItemListener(itemListener);
  }

}