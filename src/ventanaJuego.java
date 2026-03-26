import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ventanaJuego extends JFrame {

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
                        if (boton.getText().equals("")) {
                            if (turnoX) {
                                boton.setText("X");
                                etiquetaTurno.setText("Turno: O");
                            } else {
                                boton.setText("O");
                                etiquetaTurno.setText("Turno: X");
                            }
                            turnoX = !turnoX;
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
}
