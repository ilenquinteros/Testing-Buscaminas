/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author ignac
 */
public class Menu extends JFrame {

    private JRadioButton aleatorio = new JRadioButton("ALEATORIO");
    private JRadioButton personal = new JRadioButton("PERSONALIZADO");
    private JLabel bombas = new JLabel("Bombas: ");
    private JTextField cantidadBombas = new JTextField();
    private JComboBox mapas = new JComboBox<>();
    private JButton jugar = new JButton("JUGAR");

    private int ancho = 8 + 2;
    private int alto = 8 + 2;
    public JButton Botones[][] = new JButton[ancho][alto];
    public String[][] elArray = new String[ancho][alto];

    public Menu() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(350, 380));
        this.setTitle("Busca Minas");

        aleatorio.setBounds(new Rectangle(85, 40, 150, 40));
        aleatorio.setMnemonic(KeyEvent.VK_B);
        aleatorio.setActionCommand("aleatorio");
        aleatorio.setFont(new Font("Tahoma", 0, 20));
        aleatorio.setSelected(true);
        aleatorio.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ar) {
                personal.setSelected(false);
                bombas.setEnabled(false);
                cantidadBombas.setEnabled(false);
                mapas.setEnabled(false);
            }
        }
        );
        this.add(aleatorio);

        personal.setBounds(new Rectangle(85, 90, 190, 40));
        personal.setMnemonic(KeyEvent.VK_B);
        personal.setActionCommand("aleatorio");
        personal.setFont(new Font("Tahoma", 0, 20));
        personal.setSelected(false);
        personal.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ar) {
                aleatorio.setSelected(false);
                bombas.setEnabled(true);
                cantidadBombas.setEnabled(true);
                mapas.setEnabled(true);
            }
        }
        );
        this.add(personal);

        bombas.setBounds(new Rectangle(85, 150, 190, 40));
        bombas.setFont(new Font("Tahoma", 0, 15));
        bombas.setEnabled(false);

        this.add(bombas);

        cantidadBombas.setBounds(new Rectangle(160, 150, 85, 40));
        cantidadBombas.setFont(new Font("Tahoma", 0, 15));
        cantidadBombas.setEnabled(false);
        this.add(cantidadBombas);

        mapas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pequeña (8x8)", "Mediana(12x12)", "Grande(20x20)"}));
        mapas.setBounds(new Rectangle(85, 200, 160, 40));
        mapas.setEnabled(false);
        this.add(mapas);

        jugar.setBounds(new Rectangle(45, 270, 240, 50));
        jugar.setFont(new Font("Tahoma", 0, 25));
        jugar.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ar) {
                if (aleatorio.isSelected()) {
                    aleatorio();

                } else if (personal.isSelected()) {
                    personalizado();
                }
            }
        }
        );
        this.add(jugar);
        this.setVisible(true);
    }

    public boolean hayLetra() {
        boolean hayLetra = false;
        String bombas = cantidadBombas.getText();
        System.out.println(bombas.isEmpty());
        if (!bombas.isEmpty()) {
            for (int i = 0; i < bombas.length(); i++) {
                if (Character.isLetter(bombas.charAt(i))) {
                    hayLetra = true;
                    i = bombas.length();
                }
            }
        }else{
           hayLetra = true; 
        }
        return hayLetra;
    }

    public void aleatorio() {
        int numero = (int) (Math.random() * 3) + 1;
        int bombas = 1;
        BuscaMinas Juego;
        switch (numero) {
            case 1:
                bombas = (int) (Math.random() * 25) + 1;
                JOptionPane.showMessageDialog(this, "Mapa Pequeño(8x8), "+bombas+" bombas");
                Juego = new BuscaMinas(10, 10, 236, bombas);
                break;
            case 2:
                bombas = (int) (Math.random() * 50) + 1;
                JOptionPane.showMessageDialog(this, "Mapa Mediano(12x12), "+bombas+" bombas");
                Juego = new BuscaMinas(14, 14, 326, bombas);
                break;
            case 3:
                bombas = (int) (Math.random() * 150) + 1;
                JOptionPane.showMessageDialog(this, "Mapa Grande(20x20), "+bombas+" bombas");
                Juego = new BuscaMinas(22, 22, 506, bombas);
                break;
        }
        this.setVisible(false);
    }

    public void personalizado() {
        if (hayLetra()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar solo numeros");
            cantidadBombas.setText("");
        } else {
            int mapa = mapas.getSelectedIndex();
            int b = Integer.parseInt(cantidadBombas.getText());
            BuscaMinas Juego;
            switch (mapa) {
                case 0:
                    if (b <= 25) {
                        Juego = new BuscaMinas(10, 10, 236, b);
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "La cantidad de bombas maxima para este mapa es 25");
                    }
                    break;
                case 1:
                    if (b <= 50) {
                        Juego = new BuscaMinas(14, 14, 326, b);
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "La cantidad de bombas maxima para este mapa es 50");
                    }
                    break;
                case 2:
                    if (b <= 150) {
                        Juego = new BuscaMinas(22, 22, 506, b);
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "La cantidad de bombas maxima para este mapa es 150");
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {

        Menu MenuBuscaMinas = new Menu();
    }
}