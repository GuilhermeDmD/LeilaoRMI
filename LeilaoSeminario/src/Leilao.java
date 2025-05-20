import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Leilao extends UnicastRemoteObject implements LeilaoInterface {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private Set<String> clientesBloqueados = new HashSet<>();
    private boolean leilaoAtivo = true;
    int contadorDeLances = 0, contador = 0;

    public Leilao() throws RemoteException {
        super();
    }

    public boolean isLeilaoAtivo() {
        return leilaoAtivo;
    }


    // Esta classe serve para cronmetrar o leilão, uma thread e colocada para "dormir" por 3 minutos,
    // Ao acordar ela encerra o leilão
    public void iniciarCronometroLeilao() {
    new Thread(() -> {
        try {
            Thread.sleep(3 * 60 * 1000); // 3 minutos em milissegundos
            encerrarLeilaoPorTempo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }).start();
    }

    public void addCliente(String nome) throws RemoteException {
        clientes.add(new Cliente(nome, 0));
        System.out.println("Cliente adicionado ao leilão: " + nome);
    }
  @Override
    public synchronized void fazerLance(String nome, int lance) throws RemoteException {
    // System.out.println("Tentando fazer lance: " + lance + " para cliente: " + nome);

    if (!this.leilaoAtivo) {
        System.out.println("Leilão já foi encerrado. Nenhum lance será aceito.");
        return;
    }

    if (clientesBloqueados.contains(nome)) {
        System.out.println("Cliente " + nome + " está temporariamente bloqueado.");
        return;
    }

    Cliente cliente = buscarClientePorNome(nome);
    if (cliente == null) {
        System.out.println("Cliente não encontrado para o nome: " + nome);
        return;
    }

    int maiorLanceAtual = obterMaiorLance();
    System.out.println("Maior lance atual no leilão: " + maiorLanceAtual);

    if (lance > maiorLanceAtual) {
        cliente.setLance(lance);
        bloquearCliente(nome); // bloqueia temporariamente o cliente

        System.out.println("Lance de " + nome + " atualizado para: " + lance);

        contadorDeLances++;
        if (contadorDeLances >= 10) {
            this.leilaoAtivo = false;
            System.out.println("Leilão encerrado: 10 lances atingidos.");
        }
    } else {
        System.out.println("O lance deve ser maior que o lance atual (valor: " + maiorLanceAtual + ")");
    }
}


    public boolean encerrarLeilao(int lance) throws RemoteException {
        return lance == 0;
    }

     public void encerrarLeilaoPorTempo() {
        this.leilaoAtivo = false;
        System.out.println("Leilão encerrado por tempo!");
    }

    // Método que bloqueia o cliente por 20seg após ele realizar um lance
    public void bloquearCliente(String nome) {
        clientesBloqueados.add(nome);
        new Thread(() -> {
            try {
                Thread.sleep(20000); // 20 segundos de bloqueio
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientesBloqueados.remove(nome);
            System.out.println("Cliente " + nome + " desbloqueado para novos lances.");
        }).start();
}

    public String declararVencedor() throws RemoteException {
        if (clientes.isEmpty()){
            return "Nenhum cliente.";
        } 

        Cliente cliente = clientes.get(0);
        int maiorLance = cliente.getLance();
        String maiorCliente = cliente.getNome();

        for (Cliente c : clientes) {
            if (c.getLance() > maiorLance) {
                maiorLance = c.getLance();
                maiorCliente = c.getNome();
            }
        }

        return maiorCliente;
    }

    private Cliente buscarClientePorNome(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }

    private int obterMaiorLance() {
    int maior = 0;
    for (Cliente c : clientes) {
        if (c.getLance() > maior) {
            maior = c.getLance();
        }
    }
    return maior;
    }


    // getLances serve para exibir os lances para todos os participantes
    public synchronized String getLances() {
    StringBuilder sb = new StringBuilder();
    for (Cliente c : clientes) {
        System.out.println("Cliente: " + c.getNome() + ", lance: " + c.getLance());
        sb.append(c.getNome())
          .append(" último lance: ")
          .append(c.getLance())
          .append("\n");
    }
    return sb.toString();
}


     
}
