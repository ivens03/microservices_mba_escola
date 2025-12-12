package br.com.mba.spring.colegio.disciplinas.enums;

public enum MateriaObrigatoriaBrasil {

    // ------ JARDIM DE INFÂNCIA (EDUCAÇÃO INFANTIL) ------
    // A Educação Infantil é organizada em Campos de Experiência, e não em disciplinas tradicionais[citation:2][citation:5].
    JI_EU_OUTRO_NOS("O eu, o outro e o nós"),
    JI_CORPO_GESTOS_MOVIMENTOS("Corpo, gestos e movimentos"),
    JI_TRACOS_SONS_CORES_FORMAS("Traços, sons, cores e formas"),
    JI_ESCUTA_FALA_PENSAMENTO_IMAGINACAO("Escuta, fala, pensamento e imaginação"),
    JI_ESPACOS_TEMPOS_QUANTIDADES_RELACOES("Espaços, tempos, quantidades, relações e transformações"),

    // ------ ENSINO FUNDAMENTAL ------
    // Lista consolidada de disciplinas obrigatórias ao longo do Ensino Fundamental (1º ao 9º ano).
    // Esta lista sintetiza as matérias dos anos iniciais e finais.
    EF_PORTUGUES("Língua Portuguesa"),
    EF_MATEMATICA("Matemática"),
    EF_HISTORIA("História"),
    EF_GEOGRAFIA("Geografia"),
    EF_CIENCIAS("Ciências"),
    EF_EDUCACAO_FISICA("Educação Física"),
    EF_ARTES("Artes"),
    EF_INGLES("Língua Inglesa"),

    // ------ ENSINO MÉDIO (FORMAÇÃO GERAL BÁSICA) ------
    // Disciplinas da Formação Geral Básica (FGB), obrigatórias em todos os anos a partir da nova lei[citation:1].
    EM_LINGUA_PORTUGUESA("Língua Portuguesa"),
    EM_MATEMATICA("Matemática"),
    EM_INGLES("Língua Inglesa"),
    EM_ARTES("Artes"),
    EM_EDUCACAO_FISICA("Educação Física"),
    EM_BIOLOGIA("Biologia"),
    EM_FISICA("Física"),
    EM_QUIMICA("Química"),
    EM_FILOSOFIA("Filosofia"),
    EM_SOCIOLOGIA("Sociologia"),
    EM_GEOGRAFIA("Geografia"),
    EM_HISTORIA("História");

    private final String nomeFormatado;

    MateriaObrigatoriaBrasil(String nomeFormatado) {
        this.nomeFormatado = nomeFormatado;
    }

    public String getNomeFormatado() {
        return this.nomeFormatado;
    }

    /**
     * Retorna o nível de ensino ao qual esta matéria/campo de experiência pertence.
     */
    public NivelEnsino getNivelEnsino() {
        if (this.name().startsWith("JI_")) {
            return NivelEnsino.EDUCACAO_INFANTIL;
        } else if (this.name().startsWith("EF_")) {
            return NivelEnsino.ENSINO_FUNDAMENTAL;
        } else {
            return NivelEnsino.ENSINO_MEDIO;
        }
    }

    /**
     * Verifica se esta matéria faz parte do currículo do Ensino Médio Técnico.
     * Na Formação Técnica e Profissional, a carga horária da FGB é de 2.100 horas,
     * podendo integrar até 300 horas de conteúdos da BNCC relacionados ao curso[citation:1].
     */
    public boolean isParteFormacaoTecnica() {
        // Lógica de exemplo: disciplinas como Matemática, Física e Química são
        // frequentemente integradas a cursos técnicos (ex: Mecatrônica)[citation:1].
        return this.getNivelEnsino() == NivelEnsino.ENSINO_MEDIO &&
                (this == EM_MATEMATICA || this == EM_FISICA || this == EM_QUIMICA ||
                        this == EM_LINGUA_PORTUGUESA || this == EM_BIOLOGIA);
    }

    public enum NivelEnsino {
        EDUCACAO_INFANTIL,
        ENSINO_FUNDAMENTAL,
        ENSINO_MEDIO
    }

}
