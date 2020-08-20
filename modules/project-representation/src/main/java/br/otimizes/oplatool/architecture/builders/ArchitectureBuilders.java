package br.otimizes.oplatool.architecture.builders;

/**
 * Enum of architecture builders
 */
public enum ArchitectureBuilders implements IArchitectureBuilders {
    SMARTY {
        @Override
        public IArchitectureBuilder getBuilder() {
            return new ArchitectureBuilderSMarty();
        }
    },
    PAPYRUS {
        @Override
        public IArchitectureBuilder getBuilder() {
            return new ArchitectureBuilderPapyrus();
        }
    }
}
