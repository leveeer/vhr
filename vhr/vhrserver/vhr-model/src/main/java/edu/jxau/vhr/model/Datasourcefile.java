package edu.jxau.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (Datasourcefile)实体类
 *
 * @author makejava
 * @since 2020-03-07 18:20:03
 */
public class Datasourcefile implements Serializable {
    private static final long serialVersionUID = 106880995957351378L;
    
    private Integer id;
    
    private String filename;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date backupdate;
    
    private String backupuser;
    
    private String fileurl;

    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getBackupdate() {
        return backupdate;
    }

    public void setBackupdate(Date backupdate) {
        this.backupdate = backupdate;
    }

    public String getBackupuser() {
        return backupuser;
    }

    public void setBackupuser(String backupuser) {
        this.backupuser = backupuser;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}