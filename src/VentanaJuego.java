import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class VentanaJuego extends JFrame {

    private JButton botonReiniciar;
    private JButton botonGuardar;
    private JButton botonCargar;
    private JButton botonNuevoJuego;

    private boolean juegoTerminado = false;
    private JPanel panelTablero;
    private JButton[][] botones;
    private boolean turnoX = true;
    private JLabel etiquetaTurno;
    private JLabel etiquetaInfo;

    private String nombreJugador1;
    private String nombreJugador2;
    private String simboloJugador1;
    private String simboloJugador2;

    public VentanaJuego() {
        setTitle("Totito 10x10");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        pedirDatosJugadores();

        etiquetaTurno = new JLabel(
                "Turno: " + nombreJugador1 + " (" + simboloJugador1 + ")",
                SwingConstants.CENTER
        );
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 20));

        etiquetaInfo = new JLabel("Luis Miguel Barillas Del Cid - carnet: 7690-25-2654", SwingConstants.CENTER);
        etiquetaInfo.setFont(new Font("Arial", Font.PLAIN, 14));

        botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.setFont(new Font("Arial", Font.BOLD, 14));
        botonReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });

        botonGuardar = new JButton("Guardar");
        botonGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPartida();
            }
        });

        botonCargar = new JButton("Cargar");
        botonCargar.setFont(new Font("Arial", Font.BOLD, 14));
        botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPartida();
            }
        });

        botonNuevoJuego = new JButton("Nuevo Juego");
        botonNuevoJuego.setFont(new Font("Arial", Font.BOLD, 14));
        botonNuevoJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoJuego();
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
                        manejarJugada(boton);
                    }
                });

                botones[fila][columna] = boton;
                panelTablero.add(boton);
            }
        }

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCargar);
        panelBotones.add(botonReiniciar);
        panelBotones.add(botonNuevoJuego);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(etiquetaTurno, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
        add(etiquetaInfo, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void pedirDatosJugadores() {
        nombreJugador1 = JOptionPane.showInputDialog(this, "Ingrese el nombre del Jugador 1:");
        nombreJugador2 = JOptionPane.showInputDialog(this, "Ingrese el nombre del Jugador 2:");

        simboloJugador1 = JOptionPane.showInputDialog(this, "Ingrese el símbolo del Jugador 1:");
        simboloJugador2 = JOptionPane.showInputDialog(this, "Ingrese el símbolo del Jugador 2:");

        if (nombreJugador1 == null || nombreJugador1.trim().isEmpty()) {
            nombreJugador1 = "Jugador 1";
        }

        if (nombreJugador2 == null || nombreJugador2.trim().isEmpty()) {
            nombreJugador2 = "Jugador 2";
        }

        if (simboloJugador1 == null || simboloJugador1.trim().isEmpty()) {
            simboloJugador1 = "X";
        }

        if (simboloJugador2 == null || simboloJugador2.trim().isEmpty()) {
            simboloJugador2 = "O";
        }

        if (simboloJugador1.equals(simboloJugador2)) {
            JOptionPane.showMessageDialog(this, "Los símbolos no pueden ser iguales. Se usarán X y O por defecto.");
            simboloJugador1 = "X";
            simboloJugador2 = "O";
        }
    }

    private void manejarJugada(JButton boton) {
        if (!juegoTerminado && boton.getText().equals("")) {
            String simboloActual;

            if (turnoX) {
                simboloActual = simboloJugador1;
                boton.setText(simboloJugador1);
            } else {
                simboloActual = simboloJugador2;
                boton.setText(simboloJugador2);
            }

            if (verificarGanador(simboloActual)) {
                String nombreGanador;

                if (turnoX) {
                    nombreGanador = nombreJugador1;
                } else {
                    nombreGanador = nombreJugador2;
                }

                etiquetaTurno.setText("Ganó: " + nombreGanador + " (" + simboloActual + ")");
                JOptionPane.showMessageDialog(this, "Ganó el jugador: " + nombreGanador + " con el símbolo " + simboloActual);
                juegoTerminado = true;
            } else {
                turnoX = !turnoX;

                if (turnoX) {
                    etiquetaTurno.setText("Turno: " + nombreJugador1 + " (" + simboloJugador1 + ")");
                } else {
                    etiquetaTurno.setText("Turno: " + nombreJugador2 + " (" + simboloJugador2 + ")");
                }
            }
        }
    }

    private void reiniciarJuego() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botones[fila][columna].setText("");
            }
        }

        turnoX = true;
        juegoTerminado = false;
        etiquetaTurno.setText("Turno: " + nombreJugador1 + " (" + simboloJugador1 + ")");
    }

    private void nuevoJuego() {
        reiniciarJuego();
        pedirDatosJugadores();
        etiquetaTurno.setText("Turno: " + nombreJugador1 + " (" + simboloJugador1 + ")");
    }

    private void guardarPartida() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("partida.txt"));

            writer.write(nombreJugador1);
            writer.newLine();
            writer.write(nombreJugador2);
            writer.newLine();
            writer.write(simboloJugador1);
            writer.newLine();
            writer.write(simboloJugador2);
            writer.newLine();
            writer.write(String.valueOf(turnoX));
            writer.newLine();
            writer.write(String.valueOf(juegoTerminado));
            writer.newLine();

            for (int fila = 0; fila < 10; fila++) {
                for (int columna = 0; columna < 10; columna++) {
                    writer.write(botones[fila][columna].getText());
                    if (columna < 9) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "Partida guardada correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la partida.");
        }
    }

    private void cargarPartida() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("partida.txt"));

            nombreJugador1 = reader.readLine();
            nombreJugador2 = reader.readLine();
            simboloJugador1 = reader.readLine();
            simboloJugador2 = reader.readLine();
            turnoX = Boolean.parseBoolean(reader.readLine());
            juegoTerminado = Boolean.parseBoolean(reader.readLine());

            for (int fila = 0; fila < 10; fila++) {
                String linea = reader.readLine();
                String[] valores = linea.split(",", -1);

                for (int columna = 0; columna < 10; columna++) {
                    botones[fila][columna].setText(valores[columna]);
                }
            }

            reader.close();

            if (juegoTerminado) {
                etiquetaTurno.setText("Partida cargada (terminada)");
            } else {
                if (turnoX) {
                    etiquetaTurno.setText("Turno: " + nombreJugador1 + " (" + simboloJugador1 + ")");
                } else {
                    etiquetaTurno.setText("Turno: " + nombreJugador2 + " (" + simboloJugador2 + ")");
                }
            }

            JOptionPane.showMessageDialog(this, "Partida cargada correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la partida.");
        }
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

    public static void main(String[] args) {
        new VentanaJuego();
    }
}

