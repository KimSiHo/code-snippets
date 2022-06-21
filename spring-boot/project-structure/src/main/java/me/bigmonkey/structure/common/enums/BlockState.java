package me.bigmonkey.structure.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockState implements CodeDescription {
    BST001("제재"),
    BST002("해지");

    private final String description;
}
