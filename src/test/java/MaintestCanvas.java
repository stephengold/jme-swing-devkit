import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.awt.AwtPanelsContext;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MaintestCanvas extends SimpleApplication {

  public static void main(String[] args) {
    MaintestCanvas maintest = new MaintestCanvas();
    maintest.setShowSettings(false);
    AppSettings settings = new AppSettings(true);
    settings.setCustomRenderer(AwtPanelsContext.class);
    maintest.setSettings(settings);
    maintest.start(true);
    while (maintest.getViewPort() == null) {

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    // window visible.
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("myframe");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      Container rootpane = frame.getContentPane();
      // position and size the main window...
      JmeCanvasContext context = (JmeCanvasContext) maintest.getContext();
      Canvas canvas = context.getCanvas();
      JTabbedPane tabbedPane = new JTabbedPane();
      JPanel northArea = new JPanel();
      northArea.setLayout(new BoxLayout(northArea, BoxLayout.X_AXIS));
      northArea.add(new JButton("toolbarButton"));
      northArea.add(new JLabel("NORTH"));
      rootpane.add(northArea, BorderLayout.NORTH);
      rootpane.add(new JLabel("SOUTH"), BorderLayout.SOUTH);
      rootpane.add(new JLabel("EAST"), BorderLayout.EAST);
      rootpane.add(new JLabel("WEST"), BorderLayout.WEST);
      tabbedPane
          .addTab("Tab 1", null, canvas,
              "Does nothing");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
      rootpane.add(tabbedPane, BorderLayout.CENTER);
      frame.pack();
      // show the window.
      frame.setVisible(true);
    });
  }

  @Override
  public void simpleInitApp() {
    flyCam.setDragToRotate(true);
    Box b = new Box(1, 1, 1); // create cube shape
    Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
    Material mat = new Material(assetManager,
        "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
    mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
    geom.setMaterial(mat);                   // set the cube's material
    rootNode.attachChild(geom);
  }

}
