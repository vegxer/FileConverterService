package FileConverter;

public interface StructureChanger<T1, T2> {
    T1 changeStructure(T2 toChange);
}
