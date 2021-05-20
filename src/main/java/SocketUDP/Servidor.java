package SocketUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static int PUERTO = 5000;
    byte[] buffer;

    public Servidor() {
        buffer = new byte[256];
        iniciarServidor();
    }

    public void iniciarServidor() {
        try {
            System.out.println("------------INICIANDO EL SERVIDOR UDP------------");
            DatagramSocket servidorUDP = new DatagramSocket(PUERTO);
            System.out.println("Servidor en el puerto: " + PUERTO);
            int cliente = 0;
            while (true) {
                DatagramPacket peticionCliente = new DatagramPacket(buffer, buffer.length);
                servidorUDP.receive(peticionCliente);
                System.out.println("Información del cliente recibida");
                String mensaje = new String(peticionCliente.getData());
                System.out.println(mensaje);
                int puertoCliente = peticionCliente.getPort();
                InetAddress direccionCliente = peticionCliente.getAddress();
                mensaje = "Cliente " + cliente + "; " + direccionCliente + "; hola desde el servidor";
                buffer = mensaje.getBytes();
                DatagramPacket respuestaCliente = new DatagramPacket(buffer, buffer.length, direccionCliente, puertoCliente);
                servidorUDP.send(respuestaCliente);
                cliente++;
            }
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
