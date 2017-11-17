package br.com.assembleia.entities;


import br.com.assembleia.enums.EnumEstado;
import br.com.assembleia.enums.EnumEstadoCivil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author fernandosaltoleto
 */
@Entity
public class Aluno implements Serializable, Comparable<Aluno> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    private String cpf;
    private String rg;
    private String altura;
    private String peso;
    private String corOlhos;
    private String corCabelo;
    private EnumEstadoCivil estadoCivil;
    private String nomeConjuge;
    @Temporal(TemporalType.DATE)
    private Date dataCasamento;
    private String nomePai;
    private String nomeMae;
    private String nacionalidade;
    private String naturalidade;
    private EnumEstado estado;
    private String cidade;
    private String bairro;
    private String endereco;
    private String cep;
    private String telefone;
    private String celular;
    private String email;
    private String grauInstrucao;
    private String profissao;
    @Temporal(TemporalType.DATE)
    private Date dataBatismo;
    @Temporal(TemporalType.DATE)
    private Date dataBatismoEspiritoSanto;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cargo cargo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Congregacao congregacao;
    @Temporal(TemporalType.DATE)
    private Date dataConsagracao;
    private String igreja;
    private String congregação;
    @Temporal(TemporalType.DATE)
    private Date membroDesde;
    private String pastor;
    private String telefonePastor;
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public EnumEstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EnumEstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNomeConjuge() {
        return nomeConjuge;
    }

    public void setNomeConjuge(String nomeConjuge) {
        this.nomeConjuge = nomeConjuge;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getMembroDesde() {
        return membroDesde;
    }

    public void setMembroDesde(Date membroDesde) {
        this.membroDesde = membroDesde;
    }

    public Date getDataBatismo() {
        return dataBatismo;
    }

    public void setDataBatismo(Date dataBatismo) {
        this.dataBatismo = dataBatismo;
    }

    public Date getDataBatismoEspiritoSanto() {
        return dataBatismoEspiritoSanto;
    }

    public void setDataBatismoEspiritoSanto(Date dataBatismoEspiritoSanto) {
        this.dataBatismoEspiritoSanto = dataBatismoEspiritoSanto;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Date getDataConsagracao() {
        return dataConsagracao;
    }

    public void setDataConsagracao(Date dataConsagracao) {
        this.dataConsagracao = dataConsagracao;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getCorOlhos() {
        return corOlhos;
    }

    public void setCorOlhos(String corOlhos) {
        this.corOlhos = corOlhos;
    }

    public String getCorCabelo() {
        return corCabelo;
    }

    public void setCorCabelo(String corCabelo) {
        this.corCabelo = corCabelo;
    }

    public Date getDataCasamento() {
        return dataCasamento;
    }

    public void setDataCasamento(Date dataCasamento) {
        this.dataCasamento = dataCasamento;
    }

    public String getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(String grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    public String getIgreja() {
        return igreja;
    }

    public void setIgreja(String igreja) {
        this.igreja = igreja;
    }

    public String getCongregação() {
        return congregação;
    }

    public void setCongregação(String congregação) {
        this.congregação = congregação;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getPastor() {
        return pastor;
    }

    public void setPastor(String pastor) {
        this.pastor = pastor;
    }

    public String getTelefonePastor() {
        return telefonePastor;
    }

    public void setTelefonePastor(String telefonePastor) {
        this.telefonePastor = telefonePastor;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int compareTo(Aluno t) {
        return this.nome.compareTo(t.nome);
    }
}
