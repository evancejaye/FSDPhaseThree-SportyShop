package com.fsd.sportyshoes.helpers;

import org.springframework.ui.Model;

public class alerts {
    private String alert;
    private String alertClass;

    public void setAlert(Model m, String alert, String alertClass){
        m.addAttribute("alert", alert);
        m.addAttribute("alertClass", alertClass);
    }
}
