import java.rmi.Naming;

public class LeilaoServidor {
    public static void main(String[] args) {
        try {
            Leilao leilao = new Leilao();
            Naming.rebind("rmi://localhost/LeilaoService", leilao);
            System.out.println("Servidor do leilão pronto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
