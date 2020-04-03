package edu.jxau.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * attendance
 * @author 
 */
public class Attendance implements Serializable {
    private Integer id;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date clickTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date clickDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date firstClickTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date lastClickTime;

    private String workHours;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public Date getFirstClickTime() {
        return firstClickTime;
    }

    public void setFirstClickTime(Date firstClickTime) {
        this.firstClickTime = firstClickTime;
    }

    public Date getLastClickTime() {
        return lastClickTime;
    }

    public void setLastClickTime(Date lastClickTime) {
        this.lastClickTime = lastClickTime;
    }

    public String getWorkHours() {
        double hours = (lastClickTime.getTime() - firstClickTime.getTime()) / 1000 / 60 / 60.0;
        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        return decimalFormat.format(hours);
    }

    public void setWorkHours(String workHours) {
        double hours = (lastClickTime.getTime() - firstClickTime.getTime()) / 1000 / 60 / 60.0;
        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        this.workHours = decimalFormat.format(hours);
    }

    public Date getClickDate() {
        return clickDate;
    }

    public void setClickDate(Date clickDate) {
        this.clickDate = clickDate;
    }

    public Integer getStatus() {
        double workHours = Double.parseDouble(this.getWorkHours());
        if (workHours >= 0 && workHours < 8){
            this.status = 0;
        }else if(workHours >= 8 && workHours <= 9){
            this.status = 1;
        }else {
            this.status = 2;
        }
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}