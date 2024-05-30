package org.global.dax.annotation.codegen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class DemoBuilderTest {

    @Test
    void noValues() {

        final var demoBuilder = new DemoBuilder();

        final var demo = demoBuilder.build();

        assertThat(demo).isEqualTo(new Demo(null, null, null));
    }

    @Test
    void oneValue() {

        final var demoBuilder = new DemoBuilder();

        demoBuilder.i(1);

        final var demo = demoBuilder.build();

        assertThat(demo).isEqualTo(new Demo(1, null, null));
    }

    @Test
    void allValues() {

        final var demoBuilder = new DemoBuilder();

        demoBuilder.i(1);
        demoBuilder.s("I hope this demo is going well");
        demoBuilder.l(List.of("For more info, check out this link",
                              "https://hannesdorfmann.com/annotation-processing/annotationprocessing101/"));

        final var demo = demoBuilder.build();

        assertThat(demo).isEqualTo(new Demo(1,
                                            "I hope this demo is going well",
                                            List.of("For more info, check out this link",
                                                    "https://hannesdorfmann.com/annotation-processing/annotationprocessing101/")));
    }
}
