package edu.jxau.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Employeeec {
    private Integer id;

    private Integer eid;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date ecdate;

    private String ecreason;

    private Integer ecpoint;

    private Integer ectype;

    private String remark;

    private Employee employee;

    private String ectypeStr;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getEcdate() {
        return ecdate;
    }

    public void setEcdate(Date ecdate) {
        this.ecdate = ecdate;
    }

    public String getEcreason() {
        return ecreason;
    }

    public void setEcreason(String ecreason) {
        this.ecreason = ecreason == null ? null : ecreason.trim();
    }

    public Integer getEcpoint() {
        return ecpoint;
    }

    public void setEcpoint(Integer ecpoint) {
        this.ecpoint = ecpoint;
    }

    public Integer getEctype() {
        //return "罚"== ectypeStr ?  1 :  0 ;
        return ectype;
    }

    public void setEctype(Integer ectype) {
        this.ectype = ectype;
    }

    public String getEctypeStr() {
        if(ectype == 1){
            this.ectypeStr = "罚";
        }else {
            this.ectypeStr = "奖";
        }
        return ectypeStr;
    }

    public void setEctypeStr(String ectypeStr) {
        this.ectypeStr = ectypeStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Employeeec{" +
                "id=" + id +
                ", eid=" + eid +
                ", ecdate=" + ecdate +
                ", ecreason='" + ecreason + '\'' +
                ", ecpoint=" + ecpoint +
                ", ectype=" + ectype +
                ", remark='" + remark + '\'' +
                ", employee=" + employee +
                '}';
    }
}