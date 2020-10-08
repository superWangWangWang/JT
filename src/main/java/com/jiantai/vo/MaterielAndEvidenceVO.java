package com.jiantai.vo;

/**
 * 用于回显物料和佐证的情况的模型
 */
public class MaterielAndEvidenceVO {
    private String time;
    private String materiel;
    private String evidence;

    @Override
    public String toString() {
        return "MaterielAndEvidenceVO{" +
                "time='" + time + '\'' +
                ", materiel='" + materiel + '\'' +
                ", evidence='" + evidence + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMateriel() {
        return materiel;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
