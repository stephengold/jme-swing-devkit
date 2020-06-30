package com.jayfella.importer.properties.component;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jayfella.importer.core.ColorConverter;
import com.jme3.math.ColorRGBA;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ColorRGBAComponent extends SdkComponent {

    private JPanel contentPanel;
    private JLabel propertyNameLabel;
    private JLabel colorValueLabel;
    private JPanel colorPanel;

    public ColorRGBAComponent() {
        super(null, null, null);

        addColorPanelListener();
    }

    public ColorRGBAComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);

        try {
            setValue(getter.invoke(parent));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        addColorPanelListener();
    }

    private void addColorPanelListener() {

        colorPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Color color = JColorChooser.showDialog(null,
                        "Color",
                        colorPanel.getBackground());

                if (color != null) {
                    setValue(ColorConverter.toColorRGBA(color));
                }

                //ColorDialog colorDialog = new ColorDialog(colorPanel.getBackground());

                //if (colorDialog.showAndGet()) {
                //setValue(ColorUtils.toColorRGBA(colorDialog.getColor()));
                //}

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);

        ColorRGBA newVal = (ColorRGBA) value;

        SwingUtilities.invokeLater(() -> {
            colorPanel.setBackground(ColorConverter.toColor(newVal));
            colorValueLabel.setText(newVal.toString());
            bind();
        });

    }

    @Override
    public void bind() {
        super.bind();

        /*
        this.jColorChooser.getSelectionModel().addChangeListener(e -> {
            setValue(toColorRGBA(jColorChooser.getColor()));
        });

         */

    }

    @Override
    public void setPropertyName(String propertyName) {
        super.setPropertyName(propertyName);
        propertyNameLabel.setText("ColorRGBA: " + propertyName);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 20, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        contentPanel.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(colorPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(24, 24), new Dimension(24, 24), new Dimension(24, 24), 0, false));
        colorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        colorValueLabel = new JLabel();
        colorValueLabel.setText("Label");
        contentPanel.add(colorValueLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        propertyNameLabel = new JLabel();
        propertyNameLabel.setText("Label");
        contentPanel.add(propertyNameLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
