import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ventanaJuego extends JFrame {
    private boolean juegoTerminado = false;
    private JPanel panelTablero;
    private JButton[][] botones;
    private boolean turnoX = true;
    private JLabel etiquetaTurno;

    public ventanaJuego() {
        setTitle("Totito 10x10");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        etiquetaTurno = new JLabel("Turno: X", SwingConstants.CENTER);
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 20));

        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(10, 10));

        botones = new JButton[10][10];

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                JButton boton = new JButton("");
                boton.setFont(new Font("Arial", Font.BOLD, 18));

                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!juegoTerminado && boton.getText().equals("")) {
                            String simboloActual;

                            if (turnoX) {
                                simboloActual = "X";
                                boton.setText("X");
                            } else {
                                simboloActual = "O";
                                boton.setText("O");
                            }

                            if (verificarGanador(simboloActual)) {
                                etiquetaTurno.setText("Ganó el jugador: " + simboloActual);
                                JOptionPane.showMessageDialog(null, "Ganó el jugador: " + simboloActual);
                                juegoTerminado = true;
                            } else {
                                turnoX = !turnoX;
                                if (turnoX) {
                                    etiquetaTurno.setText("Turno: X");
                                } else {
                                    etiquetaTurno.setText("Turno: O");
                                }
                            }
                        }
                    }
                });

                botones[fila][columna] = boton;
                panelTablero.add(boton);
            }
        }

        add(etiquetaTurno, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);

        setVisible(true);
    }

    private boolean verificarGanador(String simbolo) {
        // Horizontal
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila][columna + 1].getText().equals(simbolo) &&
                        botones[fila][columna + 2].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

        // Vertical
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila + 1][columna].getText().equals(simbolo) &&
                        botones[fila + 2][columna].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

        // Diagonal principal
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila + 1][columna + 1].getText().equals(simbolo) &&
                        botones[fila + 2][columna + 2].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

        // Diagonal inversa
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 2; columna < 10; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila + 1][columna - 1].getText().equals(simbolo) &&
                        botones[fila + 2][columna - 2].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

        return false;
    }
}


