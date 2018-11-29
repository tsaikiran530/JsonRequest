package app.vir.com.jsonhandler.model.adapter;

import android.support.test.espresso.core.deps.guava.collect.MinMaxPriorityQueue;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import app.vir.com.jsonhandler.R;
import app.vir.com.jsonhandler.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Ramana on 29-11-2018.
 */
public class DisplayListAdapterTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mainActivityRule.getActivity();
    }

    @Test
    public void testLaunch() {

        View view = mActivity.findViewById(R.id.my_recycler_view);
        assertNotNull(view);
    }


    @Test
    public void testShouldLaunchTheMainActivityAndFindItemsInTheList() throws Exception {
        RecyclerView recyclerView = (RecyclerView) mainActivityRule.getActivity().findViewById(R.id.my_recycler_view);

        Thread.sleep(5000);
        assert recyclerView != null;
        assertThat(recyclerView.getAdapter().getItemCount(), is(100));
    }

    @Test
    public void testShouldTestTheItemNameInTheList() throws Exception {
        onView(withText("sunt qui excepturi placeat culpa")).check(matches(isDisplayed()));
    }

    @Test
    public void testShouldTestTheItemNameIfError() throws Exception {
        Thread.sleep(5000);
        TextView textView = (TextView) mainActivityRule.getActivity().findViewById(R.id.empty_view);
        assertThat(textView.getVisibility(), is(0x00000008));
    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

}