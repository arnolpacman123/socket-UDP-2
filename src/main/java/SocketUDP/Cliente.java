package SocketUDP;

import Cola.Cola;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    private Cola cola = new Cola();
    private double latitud;
    private double longitud;
    private DecimalFormat decimalFormat;
    private String mensaje;
    private byte[] buffer;
    public static int PUERTO_SERVIDOR = 5000;

    public Cliente() {
        latitud = Math.random() * 90;
        longitud = Math.random() * 180;
        decimalFormat = new DecimalFormat("#.0");
        mensaje = "Latitud = " + decimalFormat.format(latitud)
                + "; Longitud = " + decimalFormat.format(longitud);
        buffer = mensaje.getBytes();
        
        iniciarTasks();
    }

    private void iniciarTasks() {
        try {
            DatagramSocket servidorUDP = new DatagramSocket();
            DatagramPacket respuestaCliente = new DatagramPacket(buffer, buffer.length);
            
            taskEnviar(servidorUDP);
            taskRecibir(servidorUDP, respuestaCliente);
            taskVaciar();

        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void taskEnviar(DatagramSocket servidorUDP) {
        Timer timer1 = new Timer();
        TimerTask taskEnviar = new TimerTask() {
            @Override
            public void run() {
                latitud = Math.random() * 90;
                longitud = Math.random() * 180;
                mensaje = "Latitud = " + decimalFormat.format(latitud)
                        + "; Longitud = " + decimalFormat.format(longitud);
                cola.insertar(mensaje);
                System.out.println(cola.primerElemento());
                enviar(servidorUDP, cola.primerElemento());
            }
        };
        timer1.schedule(taskEnviar, 0, 20000);
    }

    private void taskRecibir(DatagramSocket servidorUDP, DatagramPacket respuestaCliente) {
        Timer timer2 = new Timer();
        TimerTask taskRecibir = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Servidor: su posición GPS es:");
                recibir(servidorUDP, respuestaCliente);
            }
        };
        timer2.schedule(taskRecibir, 0, 20000);
    }

    private void taskVaciar() {
        Timer timer3 = new Timer();
        TimerTask taskVaciarCola = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Vaciando la cola");
                while (!cola.vacia()) {
                    System.out.println("Posición descartada: " + cola.quitar());
                }
            }
        };
        timer3.schedule(taskVaciarCola, 0, 60000);
    }

    private void enviar(DatagramSocket servidorUDP, String mensaje) {
        try {
            InetAddress direccionServidor = InetAddress.getByName("192.168.0.34");
            buffer = mensaje.getBytes();
            DatagramPacket preguntaCliente = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
            servidorUDP.send(preguntaCliente);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recibir(DatagramSocket servidorUDP, DatagramPacket respuestaCliente) {
        try {
            servidorUDP.receive(respuestaCliente);
            mensaje = new String(respuestaCliente.getData());
            System.out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
