
/**
 *
 * @author Mateo
 */
import java.awt.Graphics2D;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javax.swing.Icon;

import javax.swing.ImageIcon;

import javax.swing.JFileChooser;

import javax.swing.JFrame;

import javax.swing.JMenu;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private static final int col = 3;
    private static final int fila = 3;
    private static final int colfila = col * fila;
    private JPanel panelCasilleros;
    private BufferedImage imgs[] = new BufferedImage[colfila];
    private JButtonMod botones[] = new JButtonMod[colfila];
    private BufferedImage imagen;
    private JMenuBar menubar;
    private JMenu menu;
    private JMenuItem itemAbrir, itemSalir;
    
    
    
    public VentanaPrincipal() {

        super("Rompecabezas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BufferedImage iconoAplicacion = null;
        try {
            iconoAplicacion = ImageIO.read(getClass().getResource("puzzle.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        setIconImage(iconoAplicacion);
        setSize(200, 200);
        menubar = new JMenuBar();
        menu = new JMenu("Archivo");
        itemAbrir = new JMenuItem("Abrir Imagen");
        itemSalir = new JMenuItem("Salir");
        menu.add(itemAbrir);
        menu.add(itemSalir);
        menubar.add(menu);
        setJMenuBar(menubar);
        panelCasilleros = new JPanel(new GridLayout(3, 3));
        add(panelCasilleros);
        itemAbrir.addActionListener(this);
        itemSalir.addActionListener(this);
        setVisible(true);
    }

    private void cargarImagen() {

        FileInputStream fis = null;
        File file = null;
        try {
            JFileChooser jfc = new JFileChooser();
            int resultado = jfc.showOpenDialog(this);
            
            if (resultado == jfc.APPROVE_OPTION) {
                file = jfc.getSelectedFile();
            }

            fis = new FileInputStream(file);
            imagen = ImageIO.read(fis);
            
            int count = 0;
            int ancho = imagen.getWidth() / col;
            int alto = imagen.getHeight() / fila;
            
            for (int x = 0; x < fila; x++) {
                for (int y = 0; y < col; y++) {

                    imgs[count] = new BufferedImage(ancho, alto, imagen.getType());

                    //dibujo la imagen
                    Graphics2D g2d = imgs[count].createGraphics();
                    g2d.drawImage(imagen, 0, 0, ancho, alto, ancho * y, alto * x, ancho * y + ancho, alto * x + alto, null);
                    g2d.dispose();
                    count++;
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        crearCasilleros();
        mesclarCasilleros();
        setSize(imagen.getWidth() + 20, imagen.getHeight() + 20);

    }

    //Crear casilleros
    public void crearCasilleros() {

        int count = 0;
        for (int x = 0; x < fila; x++) {
            for (int y = 0; y < col; y++) {

                botones[count] = new JButtonMod();
                botones[count].setNumOrden(count);
                botones[count].setIcon(new ImageIcon(imgs[count]));
                panelCasilleros.add(botones[count]);
                botones[count].addActionListener(this);
                count++;

            }

        }

        botones[8].setVisible(false);

    }

    public void compruebaResultados() {

        int totalCorrectos = 0;
        
        for (int i = 0; i < colfila; i++) {
            if (botones[i].getNumOrden() == i) {

                totalCorrectos++;
            }

        }

        if (totalCorrectos == 9) {
            botones[8].setVisible(true);

            for (int j = 0; j < colfila; j++) {
                botones[j].setEnabled(false);

            }

            VentanaImagen vImg = new VentanaImagen(imagen);
        }

    }

    public void cambiarCasilleros(int c1) {

        int c2 = 8;

        for (int i = 0; i < colfila; i++) {
            if (!botones[i].isVisible()) {

                c2 = i;
                break;
            }

        }
        switch (c2) {

            case 0:
                if (c1 == 1 || c1 == 3) {
                    intercambiar(c1, c2);
                }
                break;

            case 1:
                if (c1 == 0 || c1 == 2 || c1 == 4) {
                    intercambiar(c1, c2);
                }
                break;

            case 2:
                if (c1 == 1 || c1 == 5) {
                    intercambiar(c1, c2);
                }
                break;
            case 3:
                if (c1 == 0 || c1 == 4 || c1 == 6) {
                    intercambiar(c1, c2);
                }
                break;
            case 4:
                if (c1 == 1 || c1 == 3 || c1 == 5 || c1 == 7) {
                    intercambiar(c1, c2);
                }
                break;
            case 5:
                if (c1 == 4 || c1 == 2 || c1 == 8) {
                    intercambiar(c1, c2);
                }

                break;
            case 6:
                if (c1 == 3 || c1 == 7) {

                    intercambiar(c1, c2);

                }

                break;

            case 7:

                if (c1 == 8 || c1 == 6 || c1 == 4) {

                    intercambiar(c1, c2);

                }

                break;

            case 8:

                if (c1 == 5 || c1 == 7) {

                    intercambiar(c1, c2);

                }

                break;

        }

        compruebaResultados();

    }

    public void intercambiar(int c1, int c2) {

        Icon icono = botones[c1].getIcon();

        botones[c1].setIcon(botones[c2].getIcon());

        botones[c2].setIcon(icono);

        botones[c1].setVisible(false);

        botones[c2].setVisible(true);

        int numAux = botones[c1].getNumOrden();

        botones[c1].setNumOrden(botones[c2].getNumOrden());

        botones[c2].setNumOrden(numAux);

    }

    public void mesclarCasilleros() {

        long count = 7;

        for (int i = 0; i < 5000; i++) {

            cambiarCasilleros(Integer.parseInt("" + count));

            double num = Math.random() * 8;

            count = Math.round(num);

            if (count < 0) {

                count = 8;

            }

        }

    }

    @Override

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botones[0]) {

            cambiarCasilleros(0);

        } else if (e.getSource() == botones[1]) {

            cambiarCasilleros(1);

        } else if (e.getSource() == botones[2]) {

            cambiarCasilleros(2);

        } else if (e.getSource() == botones[3]) {

            cambiarCasilleros(3);

        } else if (e.getSource() == botones[4]) {

            cambiarCasilleros(4);

        } else if (e.getSource() == botones[5]) {

            cambiarCasilleros(5);

        } else if (e.getSource() == botones[6]) {

            cambiarCasilleros(6);

        } else if (e.getSource() == botones[7]) {

            cambiarCasilleros(7);

        } else if (e.getSource() == botones[8]) {

            cambiarCasilleros(8);

        } else if (e.getSource() == itemAbrir) {

            cargarImagen();

        } else if (e.getSource() == itemSalir) {

            System.exit(0);

        }

    }

}
