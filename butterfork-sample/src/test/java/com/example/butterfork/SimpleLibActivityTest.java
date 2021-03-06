package com.example.butterfork;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class) //
@Config(manifest = "src/main/AndroidManifest.xml")
public class SimpleLibActivityTest {
  @Test public void verifyContentViewBinding() {
    SimpleLibActivity activity = Robolectric.buildActivity(SimpleLibActivity.class).create().get();

    Unbinder unbinder = ButterKnife.bind(activity);
    verifySimpleActivityBound(activity);
    unbinder.unbind();
    verifySimpleActivityUnbound(activity);
  }

  protected static void verifySimpleActivityBound(SimpleLibActivity activity) {
    assertThat(activity.title.getId()).isEqualTo(R.id.title);
    assertThat(activity.subtitle.getId()).isEqualTo(R.id.subtitle);
    assertThat(activity.hello.getId()).isEqualTo(R.id.hello);
    assertThat(activity.listOfThings.getId()).isEqualTo(R.id.list_of_things);
    assertThat(activity.footer.getId()).isEqualTo(R.id.footer);
  }

  protected static void verifySimpleActivityUnbound(SimpleLibActivity activity) {
    assertThat(activity.title).isNull();
    assertThat(activity.subtitle).isNull();
    assertThat(activity.hello).isNull();
    assertThat(activity.listOfThings).isNull();
    assertThat(activity.footer).isNull();
  }
}
