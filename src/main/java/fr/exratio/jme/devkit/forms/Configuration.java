package fr.exratio.jme.devkit.forms;

import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.HighContrastDarkTheme;
import com.github.weisj.darklaf.theme.HighContrastLightTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import com.github.weisj.darklaf.theme.SolarizedDarkTheme;
import com.github.weisj.darklaf.theme.SolarizedLightTheme;
import com.github.weisj.darklaf.theme.Theme;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.material.Materials;
import com.jme3.math.Vector3f;
import fr.exratio.jme.devkit.config.DevKitConfig;
import fr.exratio.jme.devkit.core.ColorConverter;
import fr.exratio.jme.devkit.jme.DebugGridState;
import fr.exratio.jme.devkit.service.JmeEngineService;
import fr.exratio.jme.devkit.service.ServiceManager;
import fr.exratio.jme.devkit.swing.NumberFormatters;
import fr.exratio.jme.devkit.swing.SwingTheme;
import fr.exratio.jme.devkit.swing.ThemeComboBoxCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Configuration {

  public static final String WINDOW_ID = "Configuration";
  private static final Logger log = Logger.getLogger(Configuration.class.getName());
  private JButton viewPortColorChooseButton;
  private JFormattedTextField fieldOfViewTextField;
  private JFormattedTextField frustumNearTextField;
  private JFormattedTextField frustumFarTextField;
  private JPanel viewPortColorPreviewPanel;
  private JPanel rootPanel;
  private JButton saveChangesButton;
  private JFormattedTextField xGridSizeTextField;
  private JButton gridColorChooseButton;
  private JFormattedTextField xGridLocationTextField;
  private JFormattedTextField yGridLocationTextField;
  private JFormattedTextField zGridLocationTextField;
  private JFormattedTextField yGridSizeTextField;
  private JFormattedTextField gridSpacingTextField;
  private JPanel gridColorPanel;
  private JButton browseAssetRootButton;
  private JTextField assetRootTextField;
  private JComboBox<Class<? extends Theme>> themeComboBox;
  private JComboBox<String> defaultMaterialComboBox;

  public Configuration() {

    $$$setupUI$$$();

    DevKitConfig devKitConfig = DevKitConfig.getInstance();

    //themes
    DefaultComboBoxModel<Class<? extends Theme>> themeModel = new DefaultComboBoxModel<>();
    themeModel.addElement(DarculaTheme.class);
    themeModel.addElement(HighContrastDarkTheme.class);
    themeModel.addElement(HighContrastLightTheme.class);
    themeModel.addElement(IntelliJTheme.class);
    themeModel.addElement(OneDarkTheme.class);
    themeModel.addElement(SolarizedDarkTheme.class);
    themeModel.addElement(SolarizedLightTheme.class);

    themeComboBox.setModel(themeModel);
    themeComboBox.setRenderer(new ThemeComboBoxCellRenderer());

    try {
      Class<?> currentThemeClass = Class.forName(devKitConfig.getTheme());
      themeComboBox.setSelectedItem(currentThemeClass);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    // asset root
    assetRootTextField.setText(devKitConfig.getAssetRootDir()
        .replace(System.getProperty("user.dir"), ""));
    browseAssetRootButton.addActionListener(e -> {

      String projectRoot = System.getProperty("user.dir");

      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File(projectRoot));
      chooser.setDialogTitle("Select Asset Root");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(false);

      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

        String chosenPath = chooser.getSelectedFile().getAbsolutePath();

        assetRootTextField.setText(chosenPath);

      }

    });

    // default material
    DefaultComboBoxModel<String> materials = new DefaultComboBoxModel<>();
    materials.addElement(Materials.UNSHADED);
    materials.addElement(Materials.LIGHTING);
    materials.addElement(Materials.PBR);
    defaultMaterialComboBox.setModel(materials);
    defaultMaterialComboBox
        .setSelectedItem(DevKitConfig.getInstance().getDefaultMaterial());

    // viewport
    viewPortColorPreviewPanel
        .setBackground(ColorConverter.toColor(devKitConfig.getViewportColor()));
    fieldOfViewTextField.setValue(devKitConfig.getFieldOfView());
    frustumNearTextField.setValue(devKitConfig.getFrustumNear());
    frustumFarTextField.setValue(devKitConfig.getFrustumFar());

    viewPortColorChooseButton.addActionListener(e -> {

      Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());

      Color chosenColor = JColorChooser.showDialog(window,
          "ViewPort Color",
          ColorConverter.toColor(devKitConfig.getViewportColor()));

      viewPortColorPreviewPanel.setBackground(chosenColor);

    });

    // grid
    xGridSizeTextField.setValue(devKitConfig.getGridSize().x);
    yGridSizeTextField.setValue(devKitConfig.getGridSize().y);
    gridSpacingTextField.setValue(devKitConfig.getGridSize().z);

    gridColorPanel
        .setBackground(ColorConverter.toColor(devKitConfig.getGridColor()));
    gridColorChooseButton.addActionListener(e -> {

      Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());

      Color chosenColor = JColorChooser.showDialog(window,
          "ViewPort Color",
          ColorConverter.toColor(devKitConfig.getViewportColor()));

      gridColorPanel.setBackground(chosenColor);

    });

    xGridLocationTextField.setValue(devKitConfig.getGridLocation().x);
    yGridLocationTextField.setValue(devKitConfig.getGridLocation().y);
    zGridLocationTextField.setValue(devKitConfig.getGridLocation().z);

    // Save & Apply
    saveChangesButton.addActionListener(e -> {

      // theme
      @SuppressWarnings("unchecked")
      Class<? extends Theme> chosenThemeClass = ((Class<? extends Theme>) themeComboBox
          .getSelectedItem());

      if (chosenThemeClass != null) {
        if (!chosenThemeClass.getName().equals(devKitConfig.getTheme())) {
          devKitConfig.setTheme(chosenThemeClass.getName());
          SwingTheme.setTheme(chosenThemeClass);
        }
      }

      // asset root
      String existingAssetRoot = devKitConfig.getAssetRootDir();
      String newAssetRoot = assetRootTextField.getText();

      if (!existingAssetRoot.equals(newAssetRoot)) {

        ServiceManager.getService(JmeEngineService.class).getAssetManager()
            .unregisterLocator(existingAssetRoot, FileLocator.class);
        log.info("Unregistered Asset Root: " + existingAssetRoot);

        devKitConfig.setAssetRootDir(newAssetRoot);
        ServiceManager.getService(JmeEngineService.class).getAssetManager()
            .registerLocator(newAssetRoot, FileLocator.class);
        log.info("Registering New Asset Root: " + newAssetRoot);
      }

      // default material
      String defaultMaterial = (String) defaultMaterialComboBox.getSelectedItem();
      devKitConfig.setDefaultMaterial(defaultMaterial);

      // viewport
      devKitConfig
          .setViewportColor(ColorConverter.toColorRGBA(viewPortColorPreviewPanel.getBackground()));
      devKitConfig.setFieldOfView(((Number) fieldOfViewTextField.getValue()).floatValue());
      devKitConfig.setFrustumNear(((Number) frustumNearTextField.getValue()).floatValue());
      devKitConfig.setFrustumFar(((Number) frustumFarTextField.getValue()).floatValue());

      // grid
      devKitConfig.setGridColor(ColorConverter.toColorRGBA(gridColorPanel.getBackground()));

      Float gridSizeX = (Float) xGridSizeTextField.getValue();
      Float gridSizeY = (Float) yGridSizeTextField.getValue();
      Float gridSpacing = (Float) gridSpacingTextField.getValue();

      int iGridX = gridSizeX == null ? 200 : gridSizeX.intValue();
      int iGridY = gridSizeY == null ? 200 : gridSizeY.intValue();
      float fGridSpacing = gridSpacing == null ? 1.0f : gridSpacing;

      devKitConfig.setGridSize(new Vector3f(
          ((Number) xGridSizeTextField.getValue()).floatValue(),
          ((Number) yGridSizeTextField.getValue()).floatValue(),
          ((Number) gridSpacingTextField.getValue()).floatValue()));

      devKitConfig.setGridLocation(new Vector3f(
          ((Number) xGridLocationTextField.getValue()).floatValue(),
          ((Number) yGridLocationTextField.getValue()).floatValue(),
          ((Number) zGridLocationTextField.getValue()).floatValue()));

      devKitConfig.save();

      JmeEngineService engineService = ServiceManager.getService(JmeEngineService.class);

      engineService.enqueue(() -> {

        // apply viewport settongs
        engineService.applyCameraFrustumSizes();
        engineService.getViewPort()
            .setBackgroundColor(devKitConfig.getViewportColor());

        // apply grid settings
        DebugGridState debugGridState = engineService.getStateManager()
            .getState(DebugGridState.class);

        if (debugGridState != null) {
          debugGridState.refreshMesh(true, true);
        }

      });

    });
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    rootPanel = new JPanel();
    rootPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
    final JScrollPane scrollPane1 = new JScrollPane();
    rootPanel.add(scrollPane1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null,
            null, null, 0, false));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(20, 2, new Insets(10, 10, 10, 10), -1, -1));
    scrollPane1.setViewportView(panel1);
    final JLabel label1 = new JLabel();
    label1.setText("ViewPort Color");
    panel1.add(label1,
        new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label2 = new JLabel();
    label2.setText("Field of View");
    panel1.add(label2,
        new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label3 = new JLabel();
    label3.setText("Frustum Near");
    panel1.add(label3,
        new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label4 = new JLabel();
    label4.setText("Frustum Far");
    panel1.add(label4,
        new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2,
        new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    viewPortColorPreviewPanel = new JPanel();
    viewPortColorPreviewPanel
        .setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(viewPortColorPreviewPanel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(16, 16), null, 0, false));
    viewPortColorChooseButton = new JButton();
    viewPortColorChooseButton.setText("Choose...");
    panel2.add(viewPortColorChooseButton,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final Spacer spacer1 = new Spacer();
    panel2.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final JLabel label5 = new JLabel();
    label5.setText("ViewPort (3D)");
    panel1.add(label5,
        new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
            false));
    panel1.add(fieldOfViewTextField,
        new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    panel1.add(frustumNearTextField,
        new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    panel1.add(frustumFarTextField,
        new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel3,
        new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(-1, 10), null, 0, false));
    final JLabel label6 = new JLabel();
    label6.setText("Size (x,y)");
    panel1.add(label6,
        new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label7 = new JLabel();
    label7.setText("Color");
    panel1.add(label7,
        new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel4,
        new GridConstraints(16, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    gridColorPanel = new JPanel();
    gridColorPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel4.add(gridColorPanel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(16, 16), null, 0, false));
    final Spacer spacer2 = new Spacer();
    panel4.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    gridColorChooseButton = new JButton();
    gridColorChooseButton.setText("Choose...");
    panel4.add(gridColorChooseButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    panel1.add(zGridLocationTextField,
        new GridConstraints(19, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    panel1.add(yGridLocationTextField,
        new GridConstraints(18, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    panel1.add(xGridLocationTextField,
        new GridConstraints(17, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JLabel label8 = new JLabel();
    label8.setText("Location (x,y,z)");
    panel1.add(label8,
        new GridConstraints(17, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JSeparator separator1 = new JSeparator();
    panel1.add(separator1,
        new GridConstraints(13, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
            null, 0, false));
    final JLabel label9 = new JLabel();
    label9.setText("Grid");
    panel1.add(label9,
        new GridConstraints(12, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
            false));
    final JSeparator separator2 = new JSeparator();
    panel1.add(separator2,
        new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
            null, 0, false));
    panel1.add(gridSpacingTextField,
        new GridConstraints(15, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel5,
        new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel5.add(xGridSizeTextField,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(70, -1), null, 0, false));
    panel5.add(yGridSizeTextField,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(70, -1), null, 0, false));
    final JLabel label10 = new JLabel();
    label10.setText("Spacing");
    panel1.add(label10,
        new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label11 = new JLabel();
    label11.setText("Asset Root");
    panel1.add(label11,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
            false));
    final JSeparator separator3 = new JSeparator();
    panel1.add(separator3,
        new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
            null, 0, false));
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel6,
        new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    assetRootTextField = new JTextField();
    panel6.add(assetRootTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final Spacer spacer3 = new Spacer();
    panel6.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    browseAssetRootButton = new JButton();
    browseAssetRootButton.setText("Browse...");
    panel6.add(browseAssetRootButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label12 = new JLabel();
    label12.setText("Theme");
    panel1.add(label12,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    themeComboBox = new JComboBox();
    panel1.add(themeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label13 = new JLabel();
    label13.setText("Default Material");
    panel1.add(label13,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JSeparator separator4 = new JSeparator();
    panel1.add(separator4,
        new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
            null, 0, false));
    defaultMaterialComboBox = new JComboBox();
    panel1.add(defaultMaterialComboBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
    rootPanel.add(panel7,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    saveChangesButton = new JButton();
    saveChangesButton.setText("Save / Apply");
    panel7.add(saveChangesButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final Spacer spacer4 = new Spacer();
    rootPanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
  }


  public JComponent $$$getRootComponent$$$() {
    return rootPanel;
  }


  private void createUIComponents() {
    // TODO: place custom component creation code here
    fieldOfViewTextField = new JFormattedTextField(
        NumberFormatters.createFloatFormatter(10.00f, 360.00f));
    frustumNearTextField = new JFormattedTextField(
        NumberFormatters.createFloatFormatter(0.01f, 10.00f));
    frustumFarTextField = new JFormattedTextField(
        NumberFormatters.createFloatFormatter(1.00f, 10000.00f));

    xGridSizeTextField = new JFormattedTextField(NumberFormatters.createIntegerFormatter(1, 1000));
    yGridSizeTextField = new JFormattedTextField(NumberFormatters.createIntegerFormatter(1, 1000));
    gridSpacingTextField = new JFormattedTextField(
        NumberFormatters.createFloatFormatter(0.10f, 10.00f));

    xGridLocationTextField = new JFormattedTextField(NumberFormatters
        .createFloatFormatter(Float.parseFloat(Integer.MIN_VALUE + ".00"),
            Float.parseFloat(Integer.MAX_VALUE + ".00")));
    yGridLocationTextField = new JFormattedTextField(NumberFormatters
        .createFloatFormatter(Float.parseFloat(Integer.MIN_VALUE + ".00"),
            Float.parseFloat(Integer.MAX_VALUE + ".00")));
    zGridLocationTextField = new JFormattedTextField(NumberFormatters
        .createFloatFormatter(Float.parseFloat(Integer.MIN_VALUE + ".00"),
            Float.parseFloat(Integer.MAX_VALUE + ".00")));

  }

}
