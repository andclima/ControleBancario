import java.math.BigDecimal;

public class ContaBancaria {

    private String numero;
    private String nome;
    private BigDecimal saldo;
    
    public ContaBancaria(String numero, String nome, BigDecimal saldo) {
        this.numero = numero;
        this.nome = nome;
        this.saldo = saldo;
    }

    public ContaBancaria(String numero, String nome) {
        this(numero, nome, BigDecimal.ZERO);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void depositar(BigDecimal valor) throws ValorInvalidoException {
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValorInvalidoException();
        }
        this.saldo = this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) throws SaldoInsuficienteException {
        if (valor.compareTo(this.saldo) > 0) {
            throw new SaldoInsuficienteException();
        }
        this.saldo = this.saldo.subtract(valor);
    }

    @Override
    public String toString() {
        return "ContaBancaria [nome=" + nome + ", numero=" + numero + ", saldo=" + saldo + "]";
    }
 
    
}
