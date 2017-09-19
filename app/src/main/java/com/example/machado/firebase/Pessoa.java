package com.example.machado.firebase;

/**
 * Created by gabri on 30/07/2017.
 */

public class Pessoa {

    private String mNome;
    private String mCpf;
    private String mEmail;
    //Endereço
    private String mBairro;
    private String mRua;
    private String mNumero;
    //Contato
    private String mTelefone;


    public Pessoa(String nome, String cpf, String email) {
        mNome = nome;
        mEmail = email;
        mCpf = cpf;

    }


    public String getmNome() {
        return mNome;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmCpf() {
        return mCpf;
    }

    public String getmBairro() {
        return mBairro;
    }

    public void setmBairro(String mBairro) {
        this.mBairro = mBairro;
    }

    public String getmRua() {
        return mRua;
    }

    public void setmRua(String mRua) {
        this.mRua = mRua;
    }

    public String getmNumero() {
        return mNumero;
    }

    public void setmNumero(String mNumero) {
        this.mNumero = mNumero;
    }

    public String getmTelefone() {
        return mTelefone;
    }

    public void setmTelefone(String mTelefone) {
        this.mTelefone = mTelefone;
    }

    /**
     * Created by gabri on 08/08/2017.
     */
}
