package com.ame.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Junction extends Criterion {

    private static final long serialVersionUID = 184478717945040488L;

    private Nature nature;

    private List<Criterion> conditions = new ArrayList<Criterion>();

    public Junction() {}

    public Junction(Nature nature, Criterion... criterion) {
        this.nature = nature;
        Collections.addAll(conditions, criterion);
    }

    @Override
    public List<Object> getParameters() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < conditions.size(); i++) {
            objects.addAll(conditions.get(i).getParameters());
        }
        return objects;
    }

    @Override
    public String toHqlString(EntityQuery query) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < conditions.size(); i++) {
            stringBuilder.append(conditions.get(i).toHqlString(query));
            if (i < conditions.size() - 1) {
                stringBuilder.append(" ").append(nature.name()).append(" ");
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public boolean hasCfField() {
        for (Criterion criterion : conditions) {
            if (criterion.hasCfField()) {
                return true;
            }
        }
        return false;
    }

    public enum Nature {
        AND, OR
    }

    @Override
    public void setEntityQuery(EntityQuery query) {
        super.setEntityQuery(query);
        for (Criterion criterion : conditions) {
            criterion.setEntityQuery(query);
        }
    }
}
