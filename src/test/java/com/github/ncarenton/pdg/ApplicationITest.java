package com.github.ncarenton.pdg;

import com.github.ncarenton.pdg.utils.TestAppender;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static com.github.ncarenton.pdg.utils.TestUtils.getResourcePath;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class ApplicationITest {

    @Before
    public void setUp() {
        TestAppender.clear();
    }

    @Test
    public void application_should_work() throws URISyntaxException {

        // Given
        String[] args = {"-l", "1", "-i", getResourcePath("/level1/data.json").toString(), "-o", "/dev/null"};

        // When
        Application.main(args);

        // Then
        assertThat(TestAppender.messages)
                .isNotNull()
                .hasSize(2)
                .extracting("level")
                .doesNotContain("ERROR");
    }

    @Test
    public void application_should_log_error() throws URISyntaxException {

        // Given
        String[] args = {"-l", "1", "-i", "bad_file_path.json", "-o", "/dev/null"};

        // When
        Application.main(args);

        // Then
        assertThat(TestAppender.messages)
                .isNotNull()
                .hasSize(1)
                .extracting("level")
                .contains("ERROR");
    }

}
