package com.ifc.myelinflow.entity;

import java.util.Objects;

public class View {

    private String name;
    private String definition;
    private String schema;

    public View() {
    }

    public View(String name, String definition, String schema) {
        this.name = name.toLowerCase();
        this.definition = definition.toLowerCase();
        this.schema = schema.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition == null ? null : definition.toLowerCase();
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema.toLowerCase();
    }

    @Override
    public String toString() {
        return schema + "." + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return Objects.equals(name, view.name) &&
                Objects.equals(schema, view.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, schema);
    }
}
