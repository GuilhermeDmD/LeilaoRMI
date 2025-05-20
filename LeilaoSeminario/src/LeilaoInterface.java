import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LeilaoInterface extends Remote {
    void addCliente(String nome) throws RemoteException;
    void fazerLance(String nome, int lance) throws RemoteException;
    boolean encerrarLeilao(int lance) throws RemoteException;
    void iniciarCronometroLeilao() throws RemoteException;;
    String declararVencedor() throws RemoteException;
    void bloquearCliente(String nome) throws RemoteException;
    String getLances() throws RemoteException;
    boolean isLeilaoAtivo() throws RemoteException;
}
