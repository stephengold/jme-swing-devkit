package com.jayfella.devkit.properties.component.string;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jayfella.devkit.properties.component.AbstractSDKComponent;
import java.awt.Dimension;
import java.awt.Insets;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class StringComponent extends AbstractSDKComponent<String> {

  private JTextField valueTextField;
  private JLabel propertyNameLabel;
  private JPanel contentPanel;

  public StringComponent(String value) {
    this(value, null);
  }

  public StringComponent(String value, String propertyName) {
    super(value);
    $$$setupUI$$$();
    setComponent(value);
    setPropertyName(propertyName);
  }

  @Override
  public void setComponent(String value) {
    if (value == null) {
      value = "";
    }
    component = value;
    this.valueTextField.setText(component);
  }

  @Override
  public void setPropertyName(String propertyName) {
    super.setPropertyName(propertyName);
    propertyNameLabel.setText("String: " + propertyName);
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
    propertyNameLabel = new JLabel();
    propertyNameLabel.setText("String");
    contentPanel.add(propertyNameLabel,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    contentPanel.add(valueTextField, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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

  @Override
  public JComponent getJComponent() {
    return contentPanel;
  }

  @Override
  protected String computeValue() {
    return valueTextField.getText();
  }

  private void createUIComponents() {
    valueTextField = new JTextField();
    PropertyChangeListener textFieldChangeListener = getPropertyChangeListener();
    valueTextField.addPropertyChangeListener("value", textFieldChangeListener);
  }
}
