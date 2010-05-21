package com.fost.prop.web.controller;

import com.fost.prop.model.Operator;
import com.fost.uum.client.CurrentUser;
import com.opensymphony.xwork2.Preparable;

public class BaseController implements Preparable {
    Operator opuser;

    public Operator getOpuser() {
        return opuser;
    }

    public void setOpuser(Operator opuser) {
        this.opuser = opuser;
    }

    @Override
    public void prepare() throws Exception {
        this.opuser = new Operator();
        opuser.setId(CurrentUser.getId());
        opuser.setName(CurrentUser.getName());
    }
}
