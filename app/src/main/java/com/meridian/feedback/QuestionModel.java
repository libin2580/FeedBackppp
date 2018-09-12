package com.meridian.feedback;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user 1 on 11-10-2016.
 */

public class QuestionModel {
    private String questid;
    private String questname;
    private String department;
    private String user_id;

    public String getPatienttype() {
        return patienttype;
    }

    public void setPatienttype(String patienttype) {
        this.patienttype = patienttype;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    private  String agentid;
    private  String person_name,patienttype,doctor,specialty;

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }

    private  String email;
    private  String phone;
    private  String pinno;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getQuestid() {
        return questid;
    }

    public void setQuestid(String questid) {
        this.questid = questid;
    }

    public String getQuestname() {
        return questname;
    }

    public void setQuestname(String questname) {
        this.questname = questname;
    }

}
