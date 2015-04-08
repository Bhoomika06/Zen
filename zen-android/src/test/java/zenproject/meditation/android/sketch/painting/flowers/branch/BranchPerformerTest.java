package zenproject.meditation.android.sketch.painting.flowers.branch;

import com.juankysoriano.rainbow.core.drawing.RainbowDrawer;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import zenproject.meditation.android.RobolectricLauncherGradleTestRunner;
import zenproject.meditation.android.ZenTestBase;
import zenproject.meditation.android.persistence.BrushOptionsPreferences;
import zenproject.meditation.android.sketch.painting.PaintStepSkipper;
import zenproject.meditation.android.sketch.painting.flowers.FlowerDrawer;

import static org.mockito.Mockito.*;

@RunWith(RobolectricLauncherGradleTestRunner.class)
public class BranchPerformerTest extends ZenTestBase {

    private static final FlowerDrawer NO_FLOWER = null;

    private BranchesList branchesList;
    @Mock
    private FlowerDrawer flowerDrawer;
    @Mock
    private RainbowDrawer rainbowDrawer;
    @Mock
    private PaintStepSkipper paintStepSkipper;
    @Mock
    private Branch branch;
    @Mock
    private BrushOptionsPreferences brushOptionsPreferences;

    private BranchPerformer branchPerformer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        branchesList = BranchesList.newInstance();
        branchesList.getBranchesList().add(branch);

        branchPerformer = new BranchPerformer(branchesList, flowerDrawer, rainbowDrawer, paintStepSkipper, brushOptionsPreferences);
    }

    @Test
    public void testThatInitConfiguresProperlyRainbowDrawer() {
        branchPerformer.init();

        verify(rainbowDrawer).noStroke();
        verify(rainbowDrawer).smooth();
    }

    @Test
    public void testThatInitDelegatesInFlowerDrawer() {
        branchPerformer.init();

        verify(flowerDrawer).init();
    }

    @Test
    public void testThatOnDoStepIfPerformerIsEnabledStepIsRecordedOnPaintStepSkipper() {
        givenThatPerformerIsEnabled();

        branchPerformer.doStep();

        verify(paintStepSkipper).recordStep();
    }

    @Test
    public void testThatOnDoStepIfPerformerIsDisabledStepIsNotRecordedOnPaintStepSkipper() {
        givenThatPerformerIsDisabled();

        branchPerformer.doStep();

        verify(paintStepSkipper, never()).recordStep();
    }

    @Test
    public void testGivenThatPerformerIsEnabledDoesNotHaveToSkipStepAndHasFlowerThenWhenDoingStepBranchesListIsPrunedIfBranchIsDead() {
        givenThatPerformerIsEnabled();
        givenThatHasNotToSkipStep();
        givenThatHasFlowers();
        givenThatBranchIsDead();

        branchPerformer.doStep();

        Assertions.assertThat(branchesList).isEmpty();

    }

    @Test
    public void testGivenThatPerformerIsEnabledDoesNotHaveToSkipStepAndHasNoFlowerThenWhenDoingStepBranchesListPrunedIfBranchIsDead() {
        givenThatPerformerIsEnabled();
        givenThatHasNotToSkipStep();
        givenThatHasNoFlowers();
        givenThatBranchIsDead();

        branchPerformer.doStep();

        Assertions.assertThat(branchesList).isEmpty();
    }

    @Test
    public void testGivenThatPerformerIsEnabledHaveToSkipStepAndHasFlowerThenWhenDoingStepBranchesListIsNotPrunedIfBranchIsDead() {
        givenThatPerformerIsEnabled();
        givenThatHasToSkipStep();
        givenThatHasFlowers();
        givenThatBranchIsDead();

        branchPerformer.doStep();

        Assertions.assertThat(branchesList).isNotEmpty();
    }

    @Test
    public void testGivenThatPerformerIsEnabledDoesNotHaveToSkipStepThenWhenDoingStepBranchIsPainted() {
        givenThatPerformerIsEnabled();
        givenThatHasNotToSkipStep();
        givenThatBranchIsNotDead();

        branchPerformer.doStep();

        verify(rainbowDrawer).line(anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void testGivenThatPerformerIsEnabledDoesNotHaveToSkipStepAndHasFlowerThenWhenDoingStepBranchIsPainted() {
        givenThatPerformerIsEnabled();
        givenThatHasNotToSkipStep();
        givenThatHasFlowers();
        givenThatBranchIsNotDead();

        branchPerformer.doStep();

        verify(rainbowDrawer).line(anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void testGivenThatPerformerIsEnabledDoesNotHaveToSkipStepAndHasNoFlowerThenWhenDoingStepBranchIsPaintedWithBrushColor() {
        givenThatPerformerIsEnabled();
        givenThatHasNotToSkipStep();
        givenThatHasNoFlowers();
        givenThatBranchIsNotDead();

        branchPerformer.doStep();

        verify(brushOptionsPreferences).getBranchColor();
    }

    @Test
    public void testGivenThatPerformerIsEnabledDoesNotHaveToSkipStepAndHasFlowerThenWhenDoingStepBranchIsPaintedWithBrushColor() {
        givenThatPerformerIsEnabled();
        givenThatHasNotToSkipStep();
        givenThatHasFlowers();
        givenThatBranchIsNotDead();

        branchPerformer.doStep();

        verify(brushOptionsPreferences).getBranchColor();
    }

    @Test
    public void testGivenThatPerformerIsEnabledHaveToSkipStepAndHasFlowerThenWhenDoingStepBranchIsNotPainted() {
        givenThatPerformerIsEnabled();
        givenThatHasToSkipStep();
        givenThatBranchIsNotDead();

        branchPerformer.doStep();

        verify(rainbowDrawer, never()).line(anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void testThatNewInstanceReturnsNotNullBranchPerformer() {
        Assertions.assertThat(BranchPerformer.newInstance(branchesList, rainbowDrawer)).isNotNull();
    }

    @Test
    public void testThatNewInstanceReturnsANewInstance() {
        BranchPerformer firstInstance = BranchPerformer.newInstance(branchesList, rainbowDrawer);
        BranchPerformer secondInstance = BranchPerformer.newInstance(branchesList, rainbowDrawer);

        Assertions.assertThat(firstInstance).isNotEqualTo(secondInstance);
    }

    private void givenThatPerformerIsEnabled() {
        branchPerformer.enable();
    }

    private void givenThatPerformerIsDisabled() {
        branchPerformer.disable();
    }

    private void givenThatHasNoFlowers() {
        flowerDrawer = NO_FLOWER;
    }

    private void givenThatHasFlowers() {
        //no-op, just for legibility
    }

    private void givenThatHasToSkipStep() {
        when(paintStepSkipper.hasToSkipStep()).thenReturn(true);
    }

    private void givenThatHasNotToSkipStep() {
        when(paintStepSkipper.hasToSkipStep()).thenReturn(false);
    }

    private void givenThatBranchIsDead() {
        when(branch.isDead()).thenReturn(true);
    }

    private void givenThatBranchIsNotDead() {
        when(branch.isDead()).thenReturn(false);
    }
}