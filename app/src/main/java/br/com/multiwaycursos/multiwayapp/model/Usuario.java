package br.com.multiwaycursos.multiwayapp.model;

/**
 * Created by rafael on 16/11/2017.
 */

public class Usuario {
    private int matricula;
    private String nome;
    private String responsavel;


    public Usuario() {
        super();
    }

    public Usuario(int matricula, String nome, String responsavel) {
        super();
        this.matricula = matricula;
        this.nome = nome;
        this.responsavel = responsavel;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
