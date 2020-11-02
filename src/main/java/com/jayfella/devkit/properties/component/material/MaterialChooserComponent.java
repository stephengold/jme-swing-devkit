package com.jayfella.devkit.properties.component.material;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jayfella.devkit.properties.component.AbstractSDKComponent;
import com.jayfella.devkit.service.JmeEngineService;
import com.jayfella.devkit.service.ServiceManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.util.MaterialDebugAppState;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaterialChooserComponent extends AbstractSDKComponent<Material> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MaterialChooserComponent.class);

  private JComboBox<String> materialsComboBox;
  private JPanel contentPanel;
  private JButton reloadMaterialButton;


  public MaterialChooserComponent(Material value) {
    this(value, null);
  }

  public MaterialChooserComponent(Material value, String propertyName) {
    super(value, propertyName);
    $$$setupUI$$$();
    setComponent(value);
    setPropertyName(propertyName);
  }

  @Override
  public void setComponent(Material value) {
    component = value;
    materialsComboBox.setSelectedItem(component.getMaterialDef().getAssetName());
  }

  @Override
  protected Material computeValue() {
    String selectedMaterial = (String) materialsComboBox.getSelectedItem();

    AssetManager assetManager = ServiceManager.getService(JmeEngineService.class)
        .getAssetManager();
    Material material = null;

    if (selectedMaterial != null) {

      if (selectedMaterial.endsWith(".j3md")) {
        material = new Material(assetManager, selectedMaterial);
      } else if (selectedMaterial.endsWith(".j3m")) {
        material = assetManager.loadMaterial(selectedMaterial);
      }

    } else {
      LOGGER.warn("The specified material is NULL. This is probably not intended!");
    }
    return material;
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    contentPanel = new JPanel();
    contentPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    final JLabel label1 = new JLabel();
    label1.setText("Material");
    contentPanel.add(label1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final Spacer spacer1 = new Spacer();
    contentPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer2 = new Spacer();
    contentPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    materialsComboBox = new JComboBox();
    contentPanel.add(materialsComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    reloadMaterialButton = new JButton();
    reloadMaterialButton.setText("Reload Material");
    contentPanel.add(reloadMaterialButton,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return contentPanel;
  }

  private void createUIComponents() {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

    Reflections reflections = new Reflections(null, new ResourcesScanner());
    Set<String> j3mdResourceList = reflections.getResources(Pattern.compile(".*\\.j3md"));
    Set<String> j3mResourceList = reflections.getResources(Pattern.compile(".*\\.j3m"));

    for (String resource : j3mdResourceList) {
      model.addElement(resource);
    }

    for (String resource : j3mResourceList) {
      model.addElement(resource);
    }
    materialsComboBox = new JComboBox();
    materialsComboBox.setModel(model);

    ItemListener itemListener = evt -> {
      if (evt.getStateChange() == ItemEvent.SELECTED) {
        Material oldMaterial = component;
        setComponent(computeValue());
        firePropertyChange(propertyName, oldMaterial, component);
      }
    };
    materialsComboBox.addItemListener(itemListener);

    reloadMaterialButton = new JButton();
    reloadMaterialButton.addActionListener(e -> {

      if (component != null) {
        JmeEngineService engineService = ServiceManager.getService(JmeEngineService.class);
        engineService.enqueue(() -> engineService
            .getStateManager()
            .getState(MaterialDebugAppState.class)
            .reloadMaterial(component));
      }

    });

  }

  @Override
  public JComponent getJComponent() {
    return contentPanel;
  }
}
