package classes;

import database.JpaUtil;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.EntityTransaction;

@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "id")
public class Funcionario extends Pessoa {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuncionarioCargo cargo;

    @Column(nullable = false)
    private float salario;

    @Column(name = "data_contrato", nullable = false)
    private LocalDate dataContrato;

    public Funcionario() {}

    public Funcionario(String nome, LocalDate dataAniversario, String endereco, FuncionarioCargo cargo, float salario, LocalDate dataContrato, String senha, String email) {
        super(nome, dataAniversario, endereco, email, senha);
        this.cargo = cargo;
        this.salario = salario;
        this.dataContrato = dataContrato;
    }

    public static List<Funcionario> listarTodos() {
        EntityManager em = JpaUtil.getFactory().createEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f", Funcionario.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void salvar() {
        EntityManager em = JpaUtil.getFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (this.getId() == null) {
                em.persist(this);
            } else {
                em.merge(this);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public FuncionarioCargo getCargo() { return cargo; }
    public void setCargo(FuncionarioCargo cargo) { this.cargo = cargo; }
    public float getSalario() { return salario; }
    public void setSalario(float salario) { this.salario = salario; }
    public LocalDate getDataContrato() { return dataContrato; }
    public void setDataContrato(LocalDate dataContrato) { this.dataContrato = dataContrato; }

    public void promocao() {
        float base = this.salario;
        if (this.getCargo() == FuncionarioCargo.GARCOM){
            this.setCargo(FuncionarioCargo.CHEF);
            this.setSalario(base + 200);
        } else if (this.getCargo() == FuncionarioCargo.VENDEDOR){
            this.setCargo(FuncionarioCargo.GERENTE);
            this.setSalario(base + 300);
        } else if (this.getCargo() == FuncionarioCargo.ZELADOR){
            this.setCargo(FuncionarioCargo.SUPERVISOR);
            this.setSalario(base + 150);
        }
    }

    public void demitirFuncionario() {
        this.setCargo(FuncionarioCargo.DEMITIDO);
        this.setSalario(0);
    }

    @Override
    @Transient
    public boolean ehAniversario() {
        LocalDate dataAniversario = super.getDataAniversario();
        LocalDate hoje = LocalDate.now();
        if (dataAniversario == null) {return false;}
        return dataAniversario.getDayOfMonth() == hoje.getDayOfMonth() &&
                dataAniversario.getMonthValue() == hoje.getMonthValue();
    }
}