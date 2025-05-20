import java.rmi.Naming;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            LeilaoInterface leilao = (LeilaoInterface) Naming.lookup("rmi://localhost/LeilaoService");
            Scanner input = new Scanner(System.in);
            
            leilao.iniciarCronometroLeilao();
            System.out.print("Nome do participante: ");
            String nome = input.nextLine();
            int lance;
            leilao.addCliente(nome);
            System.out.println(leilao.getLances());

            do {
                if (!leilao.isLeilaoAtivo()) {
                    System.out.println("Leilão foi encerrado!");
                    break;
                }

                System.out.print("Digite seu lance (ou 0 para encerrar): ");
                lance = input.nextInt();
                if(lance != 0){
                    leilao.fazerLance(nome, lance);
                    System.out.println(leilao.getLances());
                    leilao.bloquearCliente(nome);
                }else{
                    leilao.encerrarLeilao(0);
                }
            } while (leilao.isLeilaoAtivo() && lance != 0);

            System.out.println("Vencedor: " + leilao.declararVencedor());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
