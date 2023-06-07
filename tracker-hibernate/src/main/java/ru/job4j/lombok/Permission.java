package ru.job4j.lombok;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.util.List;
@ToString
@Builder(builderMethodName = "of")
public class Permission {
    private int id;
    private String name;
    @Singular("rules")
    private List<String> rules;
}
