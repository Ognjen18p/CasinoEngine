package com.basis.game.machine_game.symbols;

public enum SymbolType implements SymbolInfo {
    SHURIKEN("SHURIKEN", "/images/SlotImages/shuriken.png", 30, new int[]{0, 0, 1, 2, 5}),
    NUNCHAKU("NUNCHAKU", "/images/SlotImages/nunchaka.png", 22, new int[]{0, 0, 2, 4, 8}),
    SAI("SAI", "/images/SlotImages/sai.png", 16, new int[]{0, 0, 3, 6, 12}),
    KUNAI("KUNAI", "/images/SlotImages/kunai.png", 12, new int[]{0, 0, 4, 8, 20}),
    KATANA("KATANA", "/images/SlotImages/katana.png", 8, new int[]{0, 0, 5, 10, 30}),
    BO("BO", "/images/SlotImages/bo.png", 6, new int[]{0, 0, 8, 20, 60}),
    KUSARIGAMA("KUSARIGAMA", "/images/SlotImages/kusarigama.png", 4, new int[]{0, 0, 10, 30, 100}),
    SHUKO("SHUKO", "/images/SlotImages/shuko.png", 2, new int[]{0, 0, 20, 80, 200});

    private final String id;
    private final String imagePath;
    private final int probability;
    private final int[] payouts;

    SymbolType(final String id, final String imagePath, int probability, int[] payouts) {
        this.id = id;
        this.imagePath = imagePath;
        this.probability = probability;
        this.payouts = payouts;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int getProbability() {
        return probability;
    }

    @Override
    public int[] getPayouts() {
        return payouts;
    }
}
