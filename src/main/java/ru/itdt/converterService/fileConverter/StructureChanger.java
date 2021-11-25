package ru.itdt.converterService.fileConverter;

public interface StructureChanger<T1, T2> {
    T1 changeStructure(T2 toChange);
}
