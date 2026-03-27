import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego extends JFrame {
    private JButton botonReiniciar;
    private boolean juegoTerminado = false;
    private JPanel panelTablero;
    private JButton[][] botones;
    private boolean turnoX = true;
    private JLabel etiquetaTurno;

    public VentanaJuego() {
        setTitle("Totito 10x10");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        etiquetaTurno = new JLabel("Turno: X", SwingConstants.CENTER);
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 20));

        botonReiniciar = new JButton("Nuevo Juego");
        botonReiniciar.setFont(new Font("Arial", Font.BOLD, 14));
        botonReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });

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
                                etiquetaTurno.setText(turnoX ? "Turno: X" : "Turno: O");
                            }
                        }
                    }
                });

                botones[fila][columna] = boton;
                panelTablero.add(boton);
            }
        }

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(etiquetaTurno, BorderLayout.CENTER);
        panelSuperior.add(botonReiniciar, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);

        setVisible(true);
    }

    private void reiniciarJuego() {
        System.out.println("Se presionó Nuevo Juego");

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botones[fila][columna].setText("");
            }
        }

        turnoX = true;
        juegoTerminado = false;
        etiquetaTurno.setText("Turno: X");
    }

    private boolean verificarGanador(String simbolo) {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila][columna + 1].getText().equals(simbolo) &&
                        botones[fila][columna + 2].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila + 1][columna].getText().equals(simbolo) &&
                        botones[fila + 2][columna].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (botones[fila][columna].getText().equals(simbolo) &&
                        botones[fila + 1][columna + 1].getText().equals(simbolo) &&
                        botones[fila + 2][columna + 2].getText().equals(simbolo)) {
                    return true;
                }
            }
        }

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
