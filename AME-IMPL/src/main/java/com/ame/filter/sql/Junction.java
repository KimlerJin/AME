package com.ame.filter.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Junction implements Criterion {

	private static final long serialVersionUID = 184478717945040488L;

	private Nature nature;
	private List<Criterion> conditions = new ArrayList<Criterion>();

	public Junction() {
	}

	protected Junction(Nature nature, Criterion... criterion) {
		this.nature = nature;
		if(criterion != null) {
			Collections.addAll(conditions, criterion);
		}
	}

	@Override
	public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		for (int i = 0; i < conditions.size(); i++) {
			stringBuilder.append(conditions.get(i).toSqlString(simpleSqlQuery));
			if (i < conditions.size() - 1) {
				stringBuilder.append(" ").append(nature.name()).append(" ");
			}
		}
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

	@Override
	public List<Object> getParameters() {
		List<Object> objects = new ArrayList<>();
		for (int i = 0; i < conditions.size(); i++) {
			objects.addAll(conditions.get(i).getParameters());
		}
		return objects;
	}

	public static enum Nature {
		AND, OR
	}

}
