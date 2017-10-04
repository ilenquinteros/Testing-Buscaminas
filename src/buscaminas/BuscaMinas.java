package buscaminas;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class BuscaMinas extends JFrame {

    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private int ancho = 32;
    private int alto = 32;
    private int bound = 10;
    private int cantidadBombas = 1;
    public JButton[][] Botones = new JButton[ancho][alto];
    public String[][] elArray = new String[ancho][alto];
    private long startTime;
    private long endTime;
    //—- Dependiendo del ancho también asignaré el número de bombas 

    public BuscaMinas(int alto, int ancho, int bound, int cantidadBombas) {
        this.alto = alto;
        this.ancho = ancho;
        this.bound = bound;
        this.cantidadBombas = cantidadBombas;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(bound, bound+40));
        this.setTitle("Busca Minas");
        jPanel1.setBounds(new Rectangle(0, 20, 740 , 740));
        jPanel1.setBackground(new Color(162, 175, 227));
        jPanel1.setLayout(null);
        jButton1.setText("SALIR");
        jButton1.setBounds(new Rectangle(0, 0, 125, 20));
        jButton1.setFont(new Font("Tahoma", 0, 12));
        jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton1.setAlignmentY((float) 0.0);
        jButton1.setMargin(new Insets(2, 14, 2, 12));
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });

        this.getContentPane().add(jButton1, null);
        this.getContentPane().add(jPanel1, null);
        cargarTablero();
        colocarBomba();
        comprueba();

        this.setVisible(true);

        //—- Oculta los botones del contorno que no participaran en el juego.
        ocultaBotones();
        startTime = System.currentTimeMillis();
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        Menu menu = new Menu();
        menu.setVisible(true);
        this.setVisible(false);
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
        }
    }

    //—– Inicializa el tablero a 0
    public void cargarTablero() {
        for (int i = 0; i < ancho; i++) {
            for (int z = 0; z < alto; z++) {
                elArray[i][z] = " ";
                Botones[i][z] = new JButton();
                jPanel1.add(Botones[i][z]);
                Botones[i][z].setBounds(i * 22, z * 22, 22, 22);
                Botones[i][z].setMargin(new Insets(0, 0, 0, 0));
                Botones[i][z].setFont(new Font("Tahoma", 0, 10));
                
                //—– Agrego un ActionListener a cada boton del Array de Botones
                //—– Ahora puede escuchar eventos.
                Botones[i][z].addActionListener(
                        new ActionListener() {
                    public void actionPerformed(ActionEvent ar) {
                        for (int i = 0; i < ancho; i++) {
                            for (int z = 0; z < alto; z++) {
                                if (ar.getSource() == Botones[i][z]) {
                                    showTextTop(i, z);
                                    //—– Cada Evento llama al método pulsarBoton.
                                }
                            }
                        }
                    }
                }
                );

                Botones[i][z].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            for (int i = 0; i < ancho; i++) {
                                for (int z = 0; z < alto; z++) {
                                    if (e.getSource() == Botones[i][z]) {
                                        showFlag(i, z);
                                        //—– Cada Evento llama al método pulsarBoton.
                                    }
                                }
                            }
                        }
                    }
                });

            }
        }
    }
    //—- Coloca el numero de bombas dependiendo el ancho.

    public void colocarBomba() {
        double i = 0;
        double z = 0;
        int condicion = 1;
        do {
            i = Math.random() * (getAncho()-2);
            z = Math.random() * (getAlto()-2);
            i = (int) i;
            z = (int) z;
            if (z != 0 && i != 0 && z != alto && i != ancho) {
                // Botones[(int) i][(int) z].setText("B");
                System.out.println("i: "+i+", z: "+z);
                elArray[(int) i][(int) z] = "B";
                condicion++;
            }
        } while (condicion <= cantidadBombas);
    }

    public void setAncho(int sAncho) {
        ancho = sAncho;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAlto(int sAlto) {
        alto = sAlto;
    }

    public int getAlto() {
        return alto;
    }

    //—- Asigna un número a cada boton dependiendo de las B que tenga al lado.
//--- codigo Busca Minas
    public void comprueba() {
        for (int i = 0; i < ancho; i++) {
            for (int z = 0; z < alto; z++) {
                int numeroComprueba = 0;   //el numero que voy a asignar al boton
                if (elArray[i][z] != ("B")) {
                    if (z != 0 && i != 0 && z != alto - 1 && i != ancho - 1) {
                        //System.out.println(i + " " + z + " " + ancho + " " + alto);

                        if (elArray[i][z - 1] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i - 1][z - 1] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i + 1][z - 1] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i][z + 1] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i + 1][z + 1] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i - 1][z + 1] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i + 1][z] == "B") {
                            numeroComprueba++;
                        }
                        if (elArray[i - 1][z] == "B") {
                            numeroComprueba++;
                        }
                        if (numeroComprueba != 0) {
                            elArray[i][z] = (String.valueOf(numeroComprueba));
                        }
                    }

                }
            }
        }
    }
    //—- Oculta los botones de los laterales.

    public void ocultaBotones() {
        for (int i = 0; i < ancho; i++) {
            for (int z = 0; z < alto; z++) {
                if (z == 0 || i == 0 || z == alto - 1 || i == ancho - 1) {
                    Botones[i][z].setVisible(false);
                }
            }
        }

    }

    //—- Muestra el texto en todos los botones cuando hay bomba.
    public void textoBotones() {
        for (int i = 0; i < ancho; i++) {
            for (int z = 0; z < alto; z++) {
                Botones[i][z].setText(elArray[i][z]);
                Botones[i][z].setEnabled(false);
                //codigo para mostrar el texto encima del boton.
            }
        }
        endTime = System.currentTimeMillis();
        int tiempoTotal = (int) ((endTime - startTime) / 1000);
        JOptionPane.showMessageDialog(this, "Vaya, has perdido.\nTiempo de juego: " + tiempoTotal + " segundos");
    }

    public void showFlag(int i, int z) {
        if (Botones[i][z].isEnabled()) {
            Botones[i][z].setText("X");
            //Botones[i][z].setBackground(Color.orange);
        }
    }

    //—- Muestra el Numero encima del boton.
    //—- Cambia las propiedades del boton mostrado.
    public void showTextTop(int i, int z) {
        Botones[i][z].setText(elArray[i][z]);
        Botones[i][z].setEnabled(false);
        finalGame();

        if (elArray[i][z] == " ") {
            Botones[i][z].setEnabled(false);
            metodoStackOverFlow(i, z);

        } else {
            Botones[i][z].setEnabled(false);
        }
        if (Botones[i][z].getText() == "B") {
            textoBotones();
        }
    }
    //—-  Pone el numero en los botones cercanos.

    private void metodoStackOverFlow(int i, int z) {
        if (z != 0 && i != 0 && z != alto - 1 && i != ancho - 1) {
            Botones[i - 1][z].setEnabled(false);
            Botones[i - 1][z - 1].setEnabled(false);
            Botones[i - 1][z + 1].setEnabled(false);
            Botones[i][z - 1].setEnabled(false);
            Botones[i][z + 1].setEnabled(false);
            Botones[i + 1][z].setEnabled(false);
            Botones[i + 1][z + 1].setEnabled(false);
            Botones[i + 1][z - 1].setEnabled(false);

            Botones[i - 1][z].setText(elArray[i - 1][z]);
            Botones[i - 1][z - 1].setText(elArray[i - 1][z - 1]);
            Botones[i - 1][z + 1].setText(elArray[i - 1][z + 1]);
            Botones[i][z - 1].setText(elArray[i][z - 1]);
            Botones[i][z + 1].setText(elArray[i][z + 1]);
            Botones[i + 1][z].setText(elArray[i + 1][z]);
            Botones[i + 1][z + 1].setText(elArray[i + 1][z + 1]);
            Botones[i + 1][z - 1].setText(elArray[i + 1][z - 1]);
        }
    }

    //—- Este es el metodo que mostrara el final del game.
    //—- 17 columnas por 10 lineas visibles= 170 Botones[][]
    //—- 170 - 19 Bombas = 171 Botones con Texto
//--- codigo Busca Minas
    public void finalGame() {
        int bombas = 0;
        for (int i = 1; i < ancho - 1; i++) {
            for (int z = 1; z < alto - 1; z++) {
                if (elArray[i][z] == "B") {
                    bombas++;
                }
                System.out.print(elArray[z][i]);
            }
            System.out.println("");
        }

        int contadorFinal = 0;
        int restante = 0;
        for (int i = 1; i < ancho - 1; i++) {
            for (int z = 1; z < alto - 1; z++) {

                if (!"B".equals(elArray[i][z]) && !Botones[i][z].isEnabled()) {
                    contadorFinal++;
                    System.out.println("CF: "+contadorFinal);
                    restante = ((ancho - 2) * (alto - 2)) - bombas;
                    System.out.println("R: "+restante);
                    if (contadorFinal == restante) {
                        endTime = System.currentTimeMillis();
                        int tiempoTotal = (int) ((endTime - startTime) / 1000);
                        JOptionPane.showMessageDialog(this, "Felicitaciones, has ganado!!\nTiempo de juego: " + tiempoTotal + " segundos");
                    }
                }
            }
        }
    }

}
