package com.basis.game.machine_game.symbols;

public enum SymbolType implements SymbolInfo {
    SHURIKEN("SHURIKEN", "/images/SlotImages/shuriken.png", 45, new int[]{0, 0, 1, 3, 6}),
    NUNCHAKU("NUNCHAKU", "/images/SlotImages/nunchaka.png", 25, new int[]{0, 0, 2, 5, 10}),
    SAI("SAI", "/images/SlotImages/sai.png",  15, new int[]{0, 0, 3, 6, 20}),
    KUNAI("KUNAI", "/images/SlotImages/kunai.png", 10, new int[]{0, 0, 4, 8, 40}),
    KATANA("KATANA", "/images/SlotImages/katana.png", 5, new int[]{0, 0, 5, 10, 50});
//    MASK("MASK", "/images/SlotImages/ninja_mask.png", 5),

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
