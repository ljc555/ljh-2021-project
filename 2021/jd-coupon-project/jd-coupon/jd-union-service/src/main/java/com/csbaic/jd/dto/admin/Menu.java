package com.csbaic.jd.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu {


    /**
     * path : /
     * component :
     * Routes : ["src/pages/Authorized"]
     * authority : ["admin","user"]
     * routes : []
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonProperty("path")
    private String path;
    @JsonProperty("name")
    private String name;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("children")
    private final List<Menu> children = new ArrayList<>();
    @JsonProperty("meta")
    private Meta meta;


    public Menu(Long id, String path, String name, String icon, Meta meta) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.icon = icon;
        this.meta = meta;
    }

    public Menu(Long id, String path, String name, String icon) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.icon = icon;
    }

    public Menu() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Menu> getChildren() {
        return children;
    }

    @JsonIgnore
    public void addChild(Menu menu){
        children.add(menu);
    }


    public static class Meta {
        private Boolean requiresAuth;

        private List<String> authorities;

        public Boolean getRequiresAuth() {
            return requiresAuth;
        }

        public void setRequiresAuth(Boolean requiresAuth) {
            this.requiresAuth = requiresAuth;
        }

        public List<String> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(List<String> authorities) {
            this.authorities = authorities;
        }
    }
}
