package com.jayfella.devkit.properties.component.vector4f;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jayfella.devkit.properties.component.AbstractPropertyEditor;
import com.jayfella.devkit.properties.component.FloatFormatFactory;
import com.jme3.math.Vector4f;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Vector4fEditor extends AbstractPropertyEditor<Vector4f> {

  public static final String LISTENED_PROPERTY_NAME = "value";

  private JPanel contentPanel;

  private JFormattedTextField xTextField;
  private JFormattedTextField yTextField;
  private JFormattedTextField zTextField;
  private JFormattedTextField wTextField;

  public Vector4fEditor() {
    this(new Vector4f());
  }

  public Vector4fEditor(Vector4f vector4f) {
    super(vector4f);
    $$$setupUI$$$();
  }

  @Override
  public void setTypedValue(Vector4f newValue) {
    if (newValue == null) {
      newValue = new Vector4f();
    }
    this.wTextField.setValue(newValue.w);
    this.xTextField.setValue(newValue.x);
    this.yTextField.setValue(newValue.y);
    this.zTextField.setValue(newValue.z);
    super.setTypedValue(newValue);
  }

  protected Vector4f computeValue() {
    float w = ((Number) wTextField.getValue()).floatValue();
    float x = ((Number) xTextField.getValue()).floatValue();
    float y = ((Number) yTextField.getValue()).floatValue();
    float z = ((Number) zTextField.getValue()).floatValue();
    return new Vector4f(x, y, z, w);
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
    contentPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
    final Spacer spacer1 = new Spacer();
    contentPanel.add(spacer1, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final JLabel label1 = new JLabel();
    label1.setHorizontalAlignment(0);
    label1.setText("x");
    contentPanel.add(label1,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label2 = new JLabel();
    label2.setText("y");
    contentPanel.add(label2,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label3 = new JLabel();
    label3.setText("z");
    contentPanel.add(label3,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label4 = new JLabel();
    label4.setText("w");
    contentPanel.add(label4,
        new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    contentPanel.add(xTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    contentPanel.add(yTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    contentPanel.add(zTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    contentPanel.add(wTextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JSeparator separator1 = new JSeparator();
    contentPanel.add(separator1,
        new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
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
    FloatFormatFactory floatFormatFactory = new FloatFormatFactory();
    wTextField = new JFormattedTextField(floatFormatFactory, value.w);
    xTextField = new JFormattedTextField(floatFormatFactory, value.x);
    yTextField = new JFormattedTextField(floatFormatFactory, value.y);
    zTextField = new JFormattedTextField(floatFormatFactory, value.z);
    wTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
    xTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
    yTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
    zTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
  }

  @Override
  public Component getCustomEditor() {
    return contentPanel;
  }

}
