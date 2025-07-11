package classes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Pagamento implements RegraDeCalculo{
    @Column(name = "pagamento_preco")
    private float preco;
    @Column(name = "pagamento_nome")
    private String nome;
    @Column(name = "pagamento_tipo")
    private String tipo;
    @Column(name = "pagamento_parcelas")
    private int parcelas;

    public Pagamento(){}
    public Pagamento(float preco,String nome,String tipo,int parcelas){
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    public float getPreco(){return this.preco;} 
    public String getNome() {return this.nome;}
    public String getTipo(){return this.tipo;}
    public int getParcelas(){return this.parcelas;}

    public void setPreco(float preco){this.preco = preco;}
    public void setNome(String nome){this.nome = nome;}
    public void setTipo(String tipo){this.tipo = tipo;}
    public void setParcelas(int parcelas){this.parcelas = parcelas;}

    @Override
    public void calcular(){
        if(this.tipo.equalsIgnoreCase("Pix")) {
            this.preco *= 0.9f;
        }else if(this.tipo.split(" ")[0].equalsIgnoreCase("Pix")){
            this.preco *= 0.9f;
        };
    }

    public void parcelamento(int num){
        for(int i=1; i<=num; i++){
            this.preco += this.preco*0.01f;
        }
    }
}