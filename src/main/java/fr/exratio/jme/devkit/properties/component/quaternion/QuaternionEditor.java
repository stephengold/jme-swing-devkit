package fr.exratio.jme.devkit.properties.component.quaternion;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jme3.math.Quaternion;
import fr.exratio.jme.devkit.properties.component.AbstractPropertyEditor;
import fr.exratio.jme.devkit.properties.component.FloatFormatFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuaternionEditor extends AbstractPropertyEditor<Quaternion> {

  public static final String LISTENED_PROPERTY_NAME = "value";

  private JPanel contentPanel;
  private JFormattedTextField xTextField;
  private JFormattedTextField yTextField;
  private JFormattedTextField zTextField;
  private JFormattedTextField wTextField;

  public QuaternionEditor() {
    this(new Quaternion());
  }

  public QuaternionEditor(Quaternion quaternion) {
    super(quaternion);
    $$$setupUI$$$();
  }

  @Override
  public void setTypedValue(Quaternion newValue) {
    if (newValue == null) {
      newValue = new Quaternion();
    }
    this.xTextField.setValue(newValue.getX());
    this.yTextField.setValue(newValue.getY());
    this.zTextField.setValue(newValue.getZ());
    this.wTextField.setValue(newValue.getW());
    super.setTypedValue(newValue);
  }

  @Override
  protected Quaternion computeValue() {
    float w = ((Number) wTextField.getValue()).floatValue();
    float x = ((Number) xTextField.getValue()).floatValue();
    float y = ((Number) yTextField.getValue()).floatValue();
    float z = ((Number) zTextField.getValue()).floatValue();
    return new Quaternion(x, y, z, w);
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    contentPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    final JLabel label1 = new JLabel();
    label1.setForeground(new Color(-4515818));
    label1.setText("x");
    contentPanel.add(label1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    contentPanel.add(xTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, new Dimension(20, -1), new Dimension(80, -1), null, 0,
        false));
    final JLabel label2 = new JLabel();
    label2.setForeground(new Color(-14697686));
    label2.setText("y");
    contentPanel.add(label2,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    contentPanel.add(yTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, new Dimension(20, -1), new Dimension(80, -1), null, 0,
        false));
    final JLabel label3 = new JLabel();
    label3.setForeground(new Color(-13522757));
    label3.setText("z");
    contentPanel.add(label3,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    contentPanel.add(zTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, new Dimension(20, -1), new Dimension(80, -1), null, 0,
        false));
    final JLabel label4 = new JLabel();
    label4.setText("w");
    contentPanel.add(label4,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    contentPanel.add(wTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, new Dimension(20, -1), new Dimension(80, -1), null, 0,
        false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return contentPanel;
  }


  private void createUIComponents() {
    contentPanel = this;
    FloatFormatFactory floatFormatFactory = new FloatFormatFactory();
    wTextField = new JFormattedTextField(floatFormatFactory, value.getW());
    xTextField = new JFormattedTextField(floatFormatFactory, value.getX());
    yTextField = new JFormattedTextField(floatFormatFactory, value.getY());
    zTextField = new JFormattedTextField(floatFormatFactory, value.getZ());
    wTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
    xTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
    yTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
    zTextField.addPropertyChangeListener(LISTENED_PROPERTY_NAME, propertyChangeListener);
  }

}
