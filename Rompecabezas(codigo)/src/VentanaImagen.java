import java.awt.BorderLayout;
 
import java.awt.image.BufferedImage;
 
import javax.swing.ImageIcon;
 
import javax.swing.JDialog;
 
import javax.swing.JLabel;
 
  
public class VentanaImagen extends JDialog{ 
     
    BufferedImage imagen;
 
    JLabel labelImagen;
    
 
    public VentanaImagen(BufferedImage imagen){
 
        setTitle("imagen original");
 
        setModal(true);
 
        this.imagen = imagen;
 
        ImageIcon icono = new ImageIcon(imagen);
 
        labelImagen = new JLabel(icono);
 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
 
        setSize(imagen.getWidth()+20, imagen.getHeight());
 
        add(labelImagen,BorderLayout.CENTER);
 
        setVisible(true);
 
    }  
}