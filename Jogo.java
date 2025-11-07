// ============================================================
// RPG CARROS: CORRIDA PELA SOBREVIVÊNCIA
// ============================================================
// Todas as classes em um único arquivo Java
// ============================================================

import javax.swing.*;
import java.util.Random;

// ============================================================
// INTERFACE PERSONAGEM
// ============================================================
interface Personagem {
    void atacar(Personagem inimigo);
    void usarHabilidade(Personagem inimigo);
    void usarItem();
    boolean fugir();
    boolean estaVivo();
    String getNome();
    int getVida();
    void setVida(int novaVida);
}

// ============================================================
// CLASSE BASE: AVENTUREIRO
// ============================================================
abstract class Aventureiro implements Personagem {
    protected String nome;
    protected int vida;
    protected int mana;
    protected int forca;
    protected int agilidade;

    public Aventureiro(String nome, int vida, int mana, int forca, int agilidade) {
        this.nome = nome;
        this.vida = vida;
        this.mana = mana;
        this.forca = forca;
        this.agilidade = agilidade;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public void setVida(int novaVida) { vida = novaVida; }

    public abstract void atacar(Personagem inimigo);
    public abstract void usarHabilidade(Personagem inimigo);

    public void usarItem() {
        vida += 10;
        System.out.println(nome + " usou um item e recuperou 10 de vida!");
    }

    public boolean fugir() {
        int chance = Dados.rolar(1, 100);
        return chance <= agilidade * 5;
    }
}

// ============================================================
// CLASSE: BÁRBARO (Mate)
// ============================================================
class Barbaro extends Aventureiro {
    public Barbaro(String nome) {
        super(nome, Dados.rolar(3, 6) + 20, 5, Dados.rolar(2, 6) + 2, Dados.rolar(1, 6));
    }

    @Override
    public void atacar(Personagem inimigo) {
        int dano = forca + Dados.rolar(1, 6);
        System.out.println(nome + " (Bárbaro) ataca " + inimigo.getNome() + " causando " + dano + " de dano!");
        inimigo.setVida(inimigo.getVida() - dano);
    }

    @Override
    public void usarHabilidade(Personagem inimigo) {
        if (mana >= 1) {
            mana--;
            int dano = (int)((forca + Dados.rolar(1, 6)) * 1.5);
            System.out.println(nome + " usa FÚRIA e causa " + dano + " de dano!");
            inimigo.setVida(inimigo.getVida() - dano);
        } else {
            System.out.println("Mana insuficiente!");
        }
    }
}

// ============================================================
// CLASSE: MAGO (Luigi)
// ============================================================
class Mago extends Aventureiro {
    public Mago(String nome) {
        super(nome, Dados.rolar(2, 6) + 15, Dados.rolar(3, 6) + 3, Dados.rolar(1, 6), Dados.rolar(2, 6));
    }

    @Override
    public void atacar(Personagem inimigo) {
        int dano = forca + Dados.rolar(1, 6);
        System.out.println(nome + " lança uma bola de energia e causa " + dano + " de dano!");
        inimigo.setVida(inimigo.getVida() - dano);
    }

    @Override
    public void usarHabilidade(Personagem inimigo) {
        if (mana >= 2) {
            mana -= 2;
            int dano = (forca * 2) + Dados.rolar(1, 6);
            System.out.println(nome + " usa MAGIA ARCANA e causa " + dano + " de dano mágico!");
            inimigo.setVida(inimigo.getVida() - dano);
        } else {
            System.out.println("Mana insuficiente!");
        }
    }
}

// ============================================================
// CLASSE: ARQUEIRO (Sally)
// ============================================================
class Arqueiro extends Aventureiro {
    public Arqueiro(String nome) {
        super(nome, Dados.rolar(3, 6) + 15, 8, Dados.rolar(1, 6) + 3, Dados.rolar(3, 6) + 3);
    }

    @Override
    public void atacar(Personagem inimigo) {
        int acerto = Dados.rolar(1, 100);
        if (acerto <= 70 + (agilidade * 2)) {
            int dano = forca + Dados.rolar(1, 6);
            System.out.println(nome + " dispara uma flecha e causa " + dano + " de dano!");
            inimigo.setVida(inimigo.getVida() - dano);
        } else {
            System.out.println(nome + " errou o disparo!");
        }
    }

    @Override
    public void usarHabilidade(Personagem inimigo) {
        int acerto = Dados.rolar(1, 100);
        if (acerto <= 95) {
            int dano = forca + Dados.rolar(2, 6);
            System.out.println(nome + " usa TIRO PRECISO e causa " + dano + " de dano crítico!");
            inimigo.setVida(inimigo.getVida() - dano);
        } else {
            System.out.println(nome + " errou o tiro!");
        }
    }
}

// ============================================================
// CLASSE: LADINO (Relâmpago McQueen)
// ============================================================
class Ladino extends Aventureiro {
    public Ladino(String nome) {
        super(nome, Dados.rolar(3, 6) + 15, 8, Dados.rolar(2, 6), Dados.rolar(3, 6) + 4);
    }

    @Override
    public void atacar(Personagem inimigo) {
        int dano = forca + Dados.rolar(1, 6);
        System.out.println(nome + " golpeia rapidamente e causa " + dano + " de dano!");
        inimigo.setVida(inimigo.getVida() - dano);
    }

    @Override
    public void usarHabilidade(Personagem inimigo) {
        int chance = Dados.rolar(1, 100);
        if (chance <= 70) {
            System.out.println(nome + " usa EVASÃO e foge do combate!");
            vida += 5;
        } else {
            int dano = forca + Dados.rolar(1, 6);
            System.out.println(nome + " contra-ataca e causa " + dano + " de dano!");
            inimigo.setVida(inimigo.getVida() - dano);
        }
    }
}

// ============================================================
// CLASSE: INIMIGO
// ============================================================
class Inimigo implements Personagem {
    private String nome;
    private int vida;
    private int forca;
    private int agilidade;

    public Inimigo(String nome, int vida, int forca, int agilidade) {
        this.nome = nome;
        this.vida = vida;
        this.forca = forca;
        this.agilidade = agilidade;
    }

    public boolean estaVivo() { return vida > 0; }
    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public void setVida(int novaVida) { vida = novaVida; }

    @Override
    public void atacar(Personagem inimigo) {
        int dano = forca + Dados.rolar(1, 6);
        System.out.println(nome + " ataca " + inimigo.getNome() + " causando " + dano + " de dano!");
        inimigo.setVida(inimigo.getVida() - dano);
    }

    @Override
    public void usarHabilidade(Personagem inimigo) {
        System.out.println(nome + " usa um ataque especial!");
        int dano = forca + Dados.rolar(2, 6);
        inimigo.setVida(inimigo.getVida() - dano);
    }

    @Override
    public void usarItem() {}
    @Override
    public boolean fugir() { return false; }
}

// ============================================================
// CLASSE: DADOS
// ============================================================
class Dados {
    private static final Random random = new Random();
    public static int rolar(int quantidade, int lados) {
        int total = 0;
        for (int i = 0; i < quantidade; i++) {
            total += random.nextInt(lados) + 1;
        }
        return total;
    }
}

// ============================================================
// CLASSE PRINCIPAL: JOGO
// ============================================================
public class Jogo {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "🏎️ Bem-vindo ao RPG Carros: Corrida pela Sobrevivência!");

        String nome = JOptionPane.showInputDialog("Digite o nome do seu carro-aventureiro:");
        String[] classes = {"Bárbaro (Mate)", "Mago (Luigi)", "Arqueiro (Sally)", "Ladino (Relâmpago McQueen)"};
        String classeEscolhida = (String) JOptionPane.showInputDialog(null, "Escolha sua classe:", "Classe",
                JOptionPane.QUESTION_MESSAGE, null, classes, classes[0]);

        Aventureiro jogador;
        switch (classeEscolhida) {
            case "Mago (Luigi)" -> jogador = new Mago(nome);
            case "Arqueiro (Sally)" -> jogador = new Arqueiro(nome);
            case "Ladino (Relâmpago McQueen)" -> jogador = new Ladino(nome);
            default -> jogador = new Barbaro(nome);
        }

        JOptionPane.showMessageDialog(null, "Você começa sua jornada na Vila Radiator Springs...");
        Inimigo inimigo = new Inimigo("Chick Hicks", 20, 5, 4);

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            String acao = (String) JOptionPane.showInputDialog(null, "Escolha sua ação:",
                    "Combate", JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Atacar", "Habilidade", "Item", "Fugir"}, "Atacar");

            switch (acao) {
                case "Atacar" -> jogador.atacar(inimigo);
                case "Habilidade" -> jogador.usarHabilidade(inimigo);
                case "Item" -> jogador.usarItem();
                case "Fugir" -> {
                    if (jogador.fugir()) {
                        JOptionPane.showMessageDialog(null, "Você escapou com sucesso!");
                        return;
                    } else JOptionPane.showMessageDialog(null, "Falhou em fugir!");
                }
            }

            if (inimigo.estaVivo()) inimigo.atacar(jogador);
            JOptionPane.showMessageDialog(null, jogador.getNome() + ": " + jogador.getVida() +
                    " HP\n" + inimigo.getNome() + ": " + inimigo.getVida() + " HP");
        }

        if (jogador.estaVivo())
            JOptionPane.showMessageDialog(null, "🎉 Vitória! " + jogador.getNome() + " sobreviveu!");
        else
            JOptionPane.showMessageDialog(null, "💀 Derrota! " + jogador.getNome() + " foi destruído na estrada!");
    }
}
