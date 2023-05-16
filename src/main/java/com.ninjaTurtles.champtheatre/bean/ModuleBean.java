package com.ninjaTurtles.champtheatre.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModuleBean {
    private String moduleName;

    public ModuleBean(){
    }

    public ModuleBean(String moduleName) {
        this.moduleName = moduleName;
    }
}
