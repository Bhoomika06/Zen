package zenproject.meditation.android.sketch.performers.flowers;

import com.juankysoriano.rainbow.core.drawing.RainbowDrawer;
import com.juankysoriano.rainbow.core.graphics.RainbowImage;
import com.juankysoriano.rainbow.utils.RainbowMath;

import java.util.ArrayList;
import java.util.List;

import zenproject.meditation.android.R;

public class MeconopsisDrawer extends FlowerDrawer {
    private static final int NUM_FLOWERS = 5;

    public static FlowerDrawer newInstance(RainbowDrawer rainbowDrawer) {
        MeconopsisDrawer meconopsisDrawer = new MeconopsisDrawer(new ArrayList<RainbowImage>(NUM_FLOWERS), rainbowDrawer);
        rainbowDrawer.loadImage(R.drawable.meconopsis_1, RainbowImage.LOAD_ORIGINAL_SIZE, meconopsisDrawer);
        rainbowDrawer.loadImage(R.drawable.meconopsis_2, RainbowImage.LOAD_ORIGINAL_SIZE, meconopsisDrawer);
        rainbowDrawer.loadImage(R.drawable.meconopsis_3, RainbowImage.LOAD_ORIGINAL_SIZE, meconopsisDrawer);
        rainbowDrawer.loadImage(R.drawable.meconopsis_4, RainbowImage.LOAD_ORIGINAL_SIZE, meconopsisDrawer);
        rainbowDrawer.loadImage(R.drawable.meconopsis_5, RainbowImage.LOAD_ORIGINAL_SIZE, meconopsisDrawer);

        return meconopsisDrawer;
    }

    private MeconopsisDrawer(List<RainbowImage> flowerImages, RainbowDrawer rainbowDrawer) {
        super(flowerImages, rainbowDrawer);
    }

    @Override
    protected float getFlowerSize() {
        return RainbowMath.random(MIN_FLOWER_SIZE / 5, MAX_FLOWER_SIZE / 5);
    }
}
