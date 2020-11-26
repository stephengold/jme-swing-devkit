package com.jayfella.devkit.properties.component.string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

class StringComponentTest {

  private StringEditor stringComponent;

  public static final void main(String[] args) throws ParseException {
    JFrame frame = new JFrame("Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new StringEditor().getCustomEditor());
    frame.pack();
    frame.setVisible(true);
  }

  @Test
  void setComponent() {
    this.stringComponent = new StringEditor();
    assertEquals("", stringComponent.getValue());
    stringComponent.setTypedValue("myString");
    assertEquals("myString", stringComponent.getValue());
  }
}